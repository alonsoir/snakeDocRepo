/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aironman.core.service.callable;

import com.aironman.core.exceptions.StoreException;
import com.aironman.core.pojos.Items;
import com.aironman.core.pojos.Usuarios;
import com.aironman.core.service.ServicioItems;
import com.aironman.core.service.ServicioUsuarios;
import com.aironman.core.utils.CarroDeLaCompra;
import java.util.concurrent.Callable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author alonso
 */
public class OperationDeleteItemFromCartCallable implements Callable<Boolean>{

    private Log log = LogFactory.getLog(OperationDeleteItemFromCartCallable.class);

    private String                      hash;
    private String                      isbn;

    private ServicioUsuarios            servicioUsuarios;
    private ServicioItems               servicioItems;


    public OperationDeleteItemFromCartCallable(String hash,String isbn,ServicioUsuarios servicioUsuarios,ServicioItems servicioItems)
    {
        if (log.isDebugEnabled()){
            StringBuffer sb = new StringBuffer("Constructor TIPADO OperationDeleteItemFromCartCallable.")
                                        .append(" hash: ").append(hash).append(" isbn: ").append(isbn);
            log.info(sb);
        }
        this.hash=hash;
        this.isbn=isbn;
        this.servicioItems=servicioItems;
        this.servicioUsuarios=servicioUsuarios;
    }
    /***
     * la el hilo que realiza la operacion.
     * @return
     * @throws StoreException
     */
    public Boolean call() throws StoreException
    {
        if (log.isDebugEnabled()){

            StringBuffer sbINIT = new StringBuffer("INIT OperationDeleteItemFromCartCallable.call . hash: ")
                                .append(hash).append(" isbn: ").append(isbn);
            log.info(sbINIT);
        }
        boolean retorno =false;
        Items item = this.getServicioItems().getItem(isbn);
        if (item == null)
        {
            if (log.isDebugEnabled()){
                StringBuffer sbItemNull = new StringBuffer("ATENCION. NO SE ENCUENTRA EL ITEM EN EL SISTEMA. ESTO NO DEBERIA OCURRIR NUNCA. ");
                log.warn(sbItemNull);
            }
            throw new StoreException("6");
        }

        CarroDeLaCompra carro = this.getServicioUsuarios().getCarroFromUsuario(hash);
        if (carro == null){
            if (log.isDebugEnabled()){
                log.info("ATENCION!! NO PUEDES DESELECCIONAR EL ITEM DE TU CARRO PORQUE NO TIENES NINGUNO ASIGNADO!! ESTO NO DEBERIA OCURRIR NUNCA!!");
            }
            //igual tienes que lanzar una excepcion
        }else
            retorno = carro.deleteItemFromCarro(item);

        if (log.isDebugEnabled()){

            StringBuffer sbEND = new StringBuffer("END StoreServiceImpl.deleteItemFromCart . hash: ")
                                .append(hash).append(" isbn: ").append(isbn).append(" retorno: ").append(retorno);
            log.info(sbEND);
        }
        
        return retorno;

    }

    /**getters and setters */
    

    public ServicioItems getServicioItems() {
        return servicioItems;
    }

    public ServicioUsuarios getServicioUsuarios() {
        return servicioUsuarios;
    }
}