/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aironman.core.service.callable;

import com.aironman.core.exceptions.StoreException;
import com.aironman.core.pojos.Items;
import com.aironman.core.pojos.Usuarios;
import com.aironman.core.service.ServicioApuntesContables;
import com.aironman.core.service.ServicioItems;
import com.aironman.core.service.ServicioUsuarios;
import com.aironman.core.pojos.ApuntesContables;
import com.aironman.core.pojos.HistoricoItemsApuntes;
import com.aironman.core.pojos.HistoricoItemsApuntesId;
import com.aironman.core.pojos.HistoricoUsuariosItems;
import com.aironman.core.service.ServicioMail;
import com.aironman.core.utils.CarroDeLaCompra;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import java.util.concurrent.Callable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author alonso
 */
public class OperationConfirmCartCallable implements Callable<Boolean>
{
    private Log log = LogFactory.getLog(OperationConfirmCartCallable.class);

    private String                      hash;
    private String                      passConfirm;

    private ServicioUsuarios            servicioUsuarios;

    private ServicioItems               servicioItems;

    private ServicioApuntesContables    servicioApuntesContables;

    //private ServicioMail              servicioMail;

    private ServicioMail                servicioMail;


    public OperationConfirmCartCallable(String hash,String pass
                                        ,ServicioUsuarios servicioUsuarios
                                        ,ServicioItems servicioItems
                                        ,ServicioApuntesContables servicioApuntesContables
                                        ,ServicioMail servicioMail)
    {
        if (log.isDebugEnabled()){

            StringBuffer sb = new StringBuffer("Constructor tipado OperationConfirmCartCallable.")
                                    .append(" hash:").append(hash)
                                    .append(" passConfirm: ").append(pass);
            log.info(sb);
        }
        this.hash=hash;
        this.passConfirm=pass;
        this.servicioApuntesContables=servicioApuntesContables;
        this.servicioItems=servicioItems;
        this.servicioUsuarios=servicioUsuarios;
        this.servicioMail=servicioMail;
    }

    /***
     * ATENCION ESTE METODO es mejorable en cuanto al rendimiento, si le paso el hash que tiene el usuario no necesitaria
     * calcular el usuario para pod
     * Esta metodo lanza un hilo que lanza la operacion ConfirmCart y tmb lanza la creacion y envio de un mail al usuario
     * @return
     * @throws StoreException
     */
    public Boolean call() throws StoreException
    {
        Boolean retorno = false;
        Usuarios usuario = comprobarSiUsuarioPuedeComprar();

        CarroDeLaCompra carro = this.servicioUsuarios.getCarroFromUsuario(hash);
        if (carro == null)
        {
            if (log.isDebugEnabled()){
                StringBuffer sbCarroNulo = new StringBuffer("ATENCION. EL USUARIO NO TIENE UN CARRO ASIGNADO. ESTO NO DEBERIA OCURRIR NUNCA. CANCELANDO OPERACION.");
                log.warn(sbCarroNulo);
            }
            throw new StoreException("34");
        }
        Collection<Items> carroDeLaCompra = carro.getCarroDelaCompra();
        if (carroDeLaCompra!=null && carroDeLaCompra.size() == 0)
        {
            if (log.isDebugEnabled()){
                StringBuffer sbListaItemsVacio = new StringBuffer("Error. LA PERSONA SE ENCUENTRA EN EL SISTEMA PERO NO tiene items en el carro!! . CANCELANDO OPERACION.");
                log.warn(sbListaItemsVacio);
            }
            throw new StoreException("28");
        }

        ApuntesContables apunteContable=null;

        Long pkApunteContable=null;
        boolean retornoUpdateItem = false;
        HistoricoUsuariosItems historicoUsuariosItems=null;
        HistoricoItemsApuntes historicoItemApunte=null;
        HistoricoItemsApuntesId historicoItemsApuntesId =null;
        //quieres contabilizar el precio de cada item que tienes en el carro de la compra para persistir el apunte contable asociado
        //junto con las entradas necesarias en los historicos
        for(Items miItem:carroDeLaCompra)
        {
            if (log.isDebugEnabled())
                log.info("isbn: " + miItem.getIsbn() + " descripcion: " + miItem.getDescripcion()
                        + " precio: " + miItem.getPrecio() + " numTotal: " + miItem.getNumUnidades());
            decrementarUnidadItemActual(miItem);
            pkApunteContable = crearApunteContable(apunteContable, usuario, miItem);
            crearHistoricoUsuarioItem(usuario, miItem, historicoUsuariosItems);
            crearHistoricoItemsApuntes(historicoItemsApuntesId, miItem, historicoItemApunte,pkApunteContable);
        }

        //la persona actualiza la lista de items para otra vez que vaya a hacer otra compra
        carro.deleteAllItemsFromCarro();
        //todo ha ido bien
        retorno = true;
        if (log.isDebugEnabled()){
            List <ApuntesContables> listaApuntesContables = getServicioApuntesContables().getAllApuntes();

            for (ApuntesContables apunte:listaApuntesContables)
            {
                log.info("END confirmCart. apunte actual: " + apunte.toString());

            }
            StringBuffer sbEndConfirmCart = new StringBuffer("END confirmCart hash: ")
                                            .append(hash).append(" pkApunteContable: ")
                                            .append(pkApunteContable).append("retorno: ").append(retorno);
            log.info(sbEndConfirmCart);
        }

        try
        {
            retorno = getServicioMail().sendMessage(usuario.getEmail());
            log.info("El servicio de mailing ha devuelto " + retorno);
            
        }catch(StoreException e){
            if (log.isDebugEnabled())
            {
                log.info("ATENCION!! Ha ocurrido un problema de entregar el mail.",e);
            }
        }
        return retorno;
    }

    private void crearHistoricoItemsApuntes(HistoricoItemsApuntesId historicoItemsApuntesId,
            Items miItem,
            HistoricoItemsApuntes historicoItemApunte,Long pkApunteContable) throws StoreException
    {
        //TODO crear el metodo para el historico Item Apunte
        //HistoricoItemsApuntesId addHistoricoItemApunte(HistoricoItemsApuntes historicoItemApunte)   throws StoreException;
        historicoItemsApuntesId = new HistoricoItemsApuntesId();
        historicoItemsApuntesId.setClaveApunte(pkApunteContable);
        historicoItemsApuntesId.setFecha(new Timestamp(new Date().getTime()));
        historicoItemsApuntesId.setIsbn(miItem.getIsbn());
        
        historicoItemApunte = new HistoricoItemsApuntes();
        historicoItemApunte.setId(historicoItemsApuntesId);
        this.servicioApuntesContables.addHistoricoItemApunte(historicoItemApunte);
    }

    private void crearHistoricoUsuarioItem(Usuarios usuario,
            Items miItem,
            HistoricoUsuariosItems historicoUsuariosItems) throws StoreException
    {
        historicoUsuariosItems = new HistoricoUsuariosItems();
        historicoUsuariosItems.setFechaUsuarioItem(new Timestamp(new Date().getTime()));
        historicoUsuariosItems.setIsbn(miItem.getIsbn());
        historicoUsuariosItems.setLegajo(usuario.getLegajo());
        this.servicioUsuarios.addHistoricoUsuarioItem(historicoUsuariosItems);
    }

    /***
     * Este metodo comprueba si el usuario puede comprar, es decir si el usuario existe en el sistema y su passConfirm es correcto
     * TODO muy mejorable, haces demasiadas consultas a la cache o a la BBDD ARREGLARLO o pensarlo mejor
     * @return
     * @throws StoreException
     */
    private Usuarios comprobarSiUsuarioPuedeComprar() throws StoreException
    {
        //StringBuffer se puede usar en entornos multihilos
        if (hash == null || ( hash != null && hash.equals("") ))
        {
            if (log.isDebugEnabled())
            {
                log.warn("ConfirmCart. Error. EL HASH PROPORCIONADO NO PUEDE SER NULO O VACIO.");
            }
            throw new StoreException("10");
        }
        if (passConfirm == null || ( passConfirm != null && passConfirm.equals("") ))
        {
            if (log.isDebugEnabled())
            {
                log.warn("ConfirmCart. Error. EL PASS PROPORCIONADO NO PUEDE SER NULO O VACIO.");
            }
            throw new StoreException("31");
        }
        if (log.isDebugEnabled())
        {
            StringBuffer sbInit = new StringBuffer("INIT confirmCart hash: ").append(hash).append("passConfirm: ").append(passConfirm);
            log.info(sbInit);
        }
        boolean retorno = this.getServicioUsuarios().checkConfirmPassword(hash,passConfirm);
        if (!retorno)
        {
            if (log.isDebugEnabled())
            {
                StringBuffer sbRetorno = new StringBuffer("PASSWORDS NO COINCIDEN. CANCELANDO OPERACION...");
                log.warn(sbRetorno);
            }
            throw new StoreException("33");
        }
        Usuarios usuario = this.getServicioUsuarios().getUser(hash);
        if (usuario == null)
        {
            if (log.isDebugEnabled())
            {
                StringBuffer sbPersonaNulo = new StringBuffer("Error. NO SE ENCUENTRA LA PERSONA EN EL SISTEMA. CANCELANDO OPERACION...");
                log.warn(sbPersonaNulo);
            }
            throw new StoreException("10");
        }
        if (usuario != null && usuario.getNumeroCC().equals(""))
        {
            if (log.isDebugEnabled())
            {
                StringBuffer sbNumeroCcVacia = new StringBuffer("Error. LA PERSONA SE ENCUENTRA EN EL SISTEMA PERO NO HAY ASIGNADA UN NUMERO DE CUENTA BANCARIA. CANCELANDO OPERACION.");
                log.warn(sbNumeroCcVacia);
            }
            throw new StoreException("11");
        }
        if (usuario != null && usuario.getNombre().equals(""))
        {
            if (log.isDebugEnabled())
            {
                StringBuffer sbPersonaVacia = new StringBuffer("Error. LA PERSONA SE ENCUENTRA EN EL SISTEMA PERO NO tiene ASIGNADO UN nombre!!. CANCELANDO OPERACION.");
                log.warn(sbPersonaVacia);
            }
            throw new StoreException("12");
        }
        if (usuario != null && usuario.getDireccion() != null && usuario.getDireccion().equals(""))
        {
            if (log.isDebugEnabled())
            {
                StringBuffer sbDireccionVacia = new StringBuffer("Error. LA PERSONA SE ENCUENTRA EN EL SISTEMA PERO NO tiene ASIGNADO UNa direccion!!. CANCELANDO OPERACION.");
                log.warn(sbDireccionVacia);
            }
            throw new StoreException("13");
        }
        return usuario;
    }

    private void decrementarUnidadItemActual(Items miItem) throws StoreException
    {
        int numUnidadesActual = miItem.getNumUnidades();
        numUnidadesActual--;
        miItem.setNumUnidades(numUnidadesActual);
        this.servicioItems.updateItem(miItem);
    }

    private Long crearApunteContable(ApuntesContables apunteContable,
            Usuarios usuario,
            Items miItem) throws StoreException
    {
        Long pkApunteContable=null;
        apunteContable = new ApuntesContables();
        apunteContable.setLegajo(usuario.getLegajo());
        apunteContable.setCargo(miItem.getPrecio());
        apunteContable.setFechaConfirmacion(new Timestamp(new Date().getTime()));
        pkApunteContable = this.servicioApuntesContables.addApunteContable(apunteContable);
        return pkApunteContable;
    }
    
    public ServicioApuntesContables getServicioApuntesContables() {
        return servicioApuntesContables;
    }

    public ServicioUsuarios getServicioUsuarios() {
        return servicioUsuarios;
    }

    public ServicioItems getServicioItems() {
        return servicioItems;
    }

    /**
     * @return the servicioMail
     */
    public ServicioMail getServicioMail()
    {
        return servicioMail;
    }

    /**
     * @param servicioMail the servicioMail to set
     */
    public void setServicioMail(ServicioMail servicioMail)
    {
        this.servicioMail = servicioMail;
    }
}
