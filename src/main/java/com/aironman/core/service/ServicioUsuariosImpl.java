/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aironman.core.service;

import com.aironman.core.exceptions.StoreException;
import com.aironman.core.pojos.HistoricoUsuariosItems;
import com.aironman.core.pojos.Usuarios;
import com.aironman.core.utils.CarroDeLaCompra;
import com.aironman.core.utils.Constantes;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Service o @Component ?
 * @author Alonso Isidoro Roman
 */
@Service("servicioUsuarios")
public class ServicioUsuariosImpl implements ServicioUsuarios {

    private static final Log log = LogFactory.getLog(ServicioUsuariosImpl.class);

    //este mapa concurrente se rellenará al completo cuando se inicia el sistema. Lo dejo para la segunda fase, cuando implemente la variedad
    //multithread al estilo psa y sepa que solucion nosql usaré, mongodb? cassandra? alguna otra mas? mas que nada por jugar :)
    //por ahora usare un orm como hibernate para atacar a una tabla de Personas. MYSQL MYISAM, POR AHORA NO NECESITO ALGO TIPO INNODB
    //PROVISIONAL PERO FUNCIONAL
    private static ConcurrentHashMap <String,Usuarios>       hashMapUsuarios        = new ConcurrentHashMap <String,Usuarios> () ;

    private static ConcurrentHashMap <String,CarroDeLaCompra>hashMapCarroUsuarios   = new ConcurrentHashMap <String,CarroDeLaCompra> () ;

    //inyectado por spring, en ppio no tiene sentido que ServicioUsuariosImpl no se instancie sin esto levantado...
    //lo pondre por constructor...
    @Autowired
    //@Qualifier("servicioEncriptacion")
    private ServicioEncriptacion                    servicioEncriptacion;
    @Autowired
    //@Qualifier("servicioPersistenciaUsuarios")
    private ServicioPersistenciaUsuarios            servicioPersistenciaUsuarios;

    public ServicioUsuariosImpl(){
        if (log.isDebugEnabled()){
            log.info("Constructor SIN tipo ServicioUsuariosImpl...");
        }
    }

    @PostConstruct
    public void init() throws StoreException
    {
        if (log.isDebugEnabled()){
            log.info("Init method on ServicioUsuariosImpl. Initializing hashMap...");
        }
        //TODO AQUI habra que hacer una llamada al servicio de persistencia para rellenar el hashMap
        try {

            //TODO AQUI habra que hacer una llamada al servicio de persistencia para rellenar el hashMap
            //ServicioItemsImpl.hashMapItems.putAll(servicioPersistenciaItems.getAllItems());
            List <Usuarios> listaUsuarios = servicioPersistenciaUsuarios.getAllUsuarios();
            for (Usuarios _usuario:listaUsuarios)
            {
                ServicioUsuariosImpl.hashMapUsuarios.putIfAbsent(_usuario.getLegajo(), _usuario);
                //TODO Una mejora puede ser recuperar el carro de la compra, por ahora lo creo
                //initializo el carro de la compra
                crearCarro(_usuario.getLegajo());
                if (log.isDebugEnabled()){
                    log.info("Usuario con legajo: " + _usuario.getLegajo() + " INTRODUCIDO EN LA CACHE...");
                }
            }
        } catch (StoreException ex) {
            log.warn("ATENCION!!. EXCEPCION GRAVE AL RELLENAR EL HASHMAP DE LOS USUARIOS MEDIANTE EL SERVICIO DE PERSISTENCIA", ex);
            //no querremos seguir si esto ocurre. lanzando excepcion.
            throw ex;
        }finally{
                log.info("hashMap INITIALIZED. numUsuarios: "
                         + ServicioUsuariosImpl.hashMapUsuarios.size());
        }
    }

    /****
     * añado un usuario al sistema. Lo guarda tanto en la cache como en BBDD
     * @param usuario 
     * @return true OK, False KO
     * @throws StoreException en caso de algun problema
     */
    public boolean addUser(Usuarios usuario) throws StoreException
    {
        if (log.isDebugEnabled()){
            StringBuffer sb = new StringBuffer("INIT ServicioUsuariosImpl.addUser. usuario: ")
                                    .append(usuario.toString());
            log.info(sb);
        }
        boolean retorno =false;
        if (usuario==null)
        {
            if (log.isDebugEnabled()){
                log.warn("ATENCION. La persona que quieres guardar no puede ser nulo. ABORTANDO insercion.");
            }
            throw new StoreException("19");
        }
        if (usuario!= null && (usuario.getEmail().equals("")))
        {
            if (log.isDebugEnabled()){
                log.warn("ATENCION. La persona que quieres guardar VIENE CON EL EMAIL VACIO. ABORTANDO insercion.");
            }
            throw new StoreException("20");
        }
        //calculo el hash del usuario a partir del email
        String hashUsuario = getServicioEncriptacion().hash(usuario.getEmail());
        //el hash es la clave en el mapa
        Usuarios _usuario = this.getUser(hashUsuario);
        if (_usuario != null)
        {
            if (log.isDebugEnabled()){
                log.warn("ATENCION. EL usuario ya existe en el sistema. ABORTANDO insercion.");
            }
            //throw new StoreException("21");
        }
        else
        {
            usuario.setLegajo(hashUsuario);
            //lo guardo en la cache
            hashMapUsuarios.putIfAbsent(hashUsuario, usuario);
            //lo persisto
            this.getServicioPersistenciaUsuarios().addUsuario(usuario);
            crearCarro(hashUsuario);
            retorno=true;
        }
        if (log.isDebugEnabled()){
            StringBuffer sb = new StringBuffer("END ServicioUsuariosImpl.addUser. usuario: ")
                                    .append(usuario.toString())
                                    .append(" retorno: ")
                                    .append(retorno);
            log.info(sb);
        }
        return retorno;
    }

    public CarroDeLaCompra crearCarro(String hash)
    {
        //crep el carro de la compra del usuario
        CarroDeLaCompra carro = new CarroDeLaCompra();
        hashMapCarroUsuarios.putIfAbsent(hash, carro);
        return carro;
    }

    /*
    /****
     * actualiza la persona del sistema.
     * @param usuario 
     * @return true OK, false KO
     * @throws StoreException en caso de algun problema, tales como un campo obligatorio que venga vacio o nulo...
     */
    public boolean updateUser(Usuarios usuario) throws StoreException
    {
        if (log.isDebugEnabled()){
            StringBuffer sb = new  StringBuffer("INIT ServicioUsuariosImpl.updateUser. usuario: ").append(usuario);
            log.info(sb);
        }
        boolean retorno =false;
        if (usuario == null || (usuario!=null && usuario.getLegajo().equals("") && usuario.getEmail().equals("")))
        {
            if (log.isDebugEnabled()){
                log.warn("ATENCION!. La persona viene NULA, su legajo viene VACIO o bien su email viene vacio. ABORTANDO actualizacion.");
            }
            throw new StoreException("22");
        }
        //sacamos la persona del hashMap dado el nombre completo
        //que es mejor? usar, el equals o el compareTo
        Usuarios _usuario = this.getUser(usuario.getLegajo());
        if (_usuario == null || (_usuario != null && _usuario.getLegajo().equals("")))
        {
            if (log.isDebugEnabled()){
                log.warn("ATENCION!. La persona que intentas actualizar NO se encuentra en el sistema. ABORTANDO actualizacion.");
            }
            throw new StoreException("23");
        }
        else
        {
            //public boolean replace(K key,V oldValue,V newValue)
            Usuarios oldUsuario=null;
            do
            {
                oldUsuario = ServicioUsuariosImpl.hashMapUsuarios.get(usuario.getLegajo());
            }while(ServicioUsuariosImpl.hashMapUsuarios.replace(usuario.getLegajo(), oldUsuario, usuario) && oldUsuario!=usuario);
            //persisto el cambio
            this.getServicioPersistenciaUsuarios().updateUser(usuario);
            if (log.isDebugEnabled()){
                StringBuilder sbReplace = new StringBuilder("ServicioUsuariosImpl. METHOD: updateUser. oldUsuario: ").append(oldUsuario.toString());
                log.info(sbReplace);
            }
            retorno=true;
        }
        if (log.isDebugEnabled()){
            StringBuilder sbEnd = new StringBuilder("END ServicioUsuariosImpl.updateUser. retorno: ").append(retorno);
            log.info(sbEnd);
        }
        return retorno;
    }

    /**lo mismo que los anteriores, esto en un entorno multihilo tampoco funcionara
     * @param usuario
     * @return 
     * @throws StoreException
     */
    public boolean deleteUser(Usuarios usuario) throws StoreException
    {
        if (log.isDebugEnabled()){
            StringBuilder sbInit = new StringBuilder("INIT ServicioUsuariosImpl.deleteUser...");
            log.info(sbInit);
        }
        boolean retorno =false;
        if (usuario == null || (usuario!=null && usuario.getLegajo().equals("") && usuario.getEmail().equals("")))
        {
            if (log.isDebugEnabled()){
                log.warn("ATENCION. El usuario que intentas borrar viene nulo, con el legajo nulo o el email nulo.Cancelando operacion.");
            }
            throw new StoreException("24");
        }
        //lo buscare en el mapa y actualizare el campo boolean activo a falso
        Usuarios _usuario = this.getUser(usuario.getLegajo());
        //sacamos la persona del hashMap dado el nombre completo
        if (_usuario == null || (_usuario != null && _usuario.getLegajo()!=null && _usuario.getLegajo().equals("")))
        {
            if (log.isDebugEnabled()){
                StringBuilder sbPesonaNull = new StringBuilder("ATENCION. La persona que intentas actualizar NO se encuentra en el sistema. Abortando actualizacion. ");
                log.info(sbPesonaNull);
            }
            throw new StoreException("25");
        }
        else
        {
            //dejamos al usuario como inactivo
            //valores permitidos A: activo I: inactivo
            //TODO para mejorar, esto lo suyo es tener un enum en bbdd para permitir estos dos valores unicamente, pero hoy no me sale :(
            usuario.setStatus(Constantes.estadoUsuarioInactivo);
            //actualizo el mapa
            //public boolean replace(K key,V oldValue,V newValue)
            Usuarios oldUsuario=null;
            do
            {
                oldUsuario = ServicioUsuariosImpl.hashMapUsuarios.get(usuario.getLegajo());
            }while(ServicioUsuariosImpl.hashMapUsuarios.replace(usuario.getLegajo(), oldUsuario, usuario) && oldUsuario!=usuario);
            if (log.isDebugEnabled()){
                StringBuilder sbReplace = new StringBuilder("ServicioUsuariosImpl. METHOD: deleteUser. oldUsuario: ").append(oldUsuario.toString());
                log.info(sbReplace);
            }
            //lo persisto
            this.getServicioPersistenciaUsuarios().updateUser(usuario);
            retorno=true;
        }
        if (log.isDebugEnabled()){
            StringBuilder sbEnd = new StringBuilder("END ServicioUsuariosImpl.deleteUser. retorno: ").append(retorno);
            log.info(sbEnd);
        }
        return retorno;
    }
    public CarroDeLaCompra getCarroFromUsuario(String hash) throws StoreException
    {
        if (log.isDebugEnabled()){
            StringBuilder sbInit = new StringBuilder("Init ServicioUsuariosImpl.getCarroFromUsuario... hash: ").append(hash);
            log.info(sbInit);
        }
        CarroDeLaCompra carro = hashMapCarroUsuarios.get(hash);
        if (log.isDebugEnabled()){
            StringBuilder sbEnd = new StringBuilder("End ServicioUsuariosImpl.getCarroFromUsuario... email: ").append(hash);
            sbEnd.append(" tamaño del carro: ").append(carro!=null?carro.getCarroDelaCompra().size():" carro nulo.");
            log.info(sbEnd);
        }
        return carro;
    }


    /*
     * el legajo esta formado por el hash formado por el nombre del usuario
     **/
    public Usuarios getUser(String hash) throws StoreException
    {
        if (log.isDebugEnabled()){
            StringBuilder sbInit = new StringBuilder("Init ServicioUsuariosImpl.getUser... hash: ").append(hash);
            log.info(sbInit);
        }
        if (hash == null || (hash!=null && hash.equals("")))
        {
            if (log.isDebugEnabled()){
                log.warn("ATENCION!!. El hash es nulo o vacio. EL USUARIO NO SE ENCUENTRA EN EL SISTEMA.");
            }
            //throw new StoreException("30");
            return null;
        }
        //String hashUsuario = getServicioEncriptacion().hash(email);
        Usuarios usuario = hashMapUsuarios.get(hash);
        if (usuario == null)
        {
            if (log.isDebugEnabled()){
                log.warn("ATENCION!!. El usuario no se encuentra en la cache. Buscando en BBDD...");
            }
            usuario = this.getServicioPersistenciaUsuarios().getUsuarioByLegajo(hash);
            if (usuario == null)
            {
                if (log.isDebugEnabled()){
                    log.warn("ATENCION!!. El usuario no se encuentra en la BBDD ni en la cache...");
                }
            }
        }
        if (log.isDebugEnabled()){
            StringBuilder sbEnd = new StringBuilder("End ServicioUsuariosImpl.getUser... hash: ")
                                                .append(hash)
                                                .append(" usuario: ").append(usuario!=null?usuario.toString():" NULO");
            log.info(sbEnd);
        }
        return usuario;

    }


    /***
     * Busca en la cache el numero de usuarios existentes
     * @return
     */
    public int getCountUsers()
    {
        int count = hashMapUsuarios.size();
        if (log.isDebugEnabled()){
            StringBuilder sb = new StringBuilder("ServicioUsuariosImpl.getCountUsers: ").append(count);
            log.info(sb);
        }
        return count;
    }
    /***
     * Este metodo va a comprobar que el pass de compra introducido es el adecuado
     * @param email
     * @param passConfirm
     * @return true ok, false ko
     * @throws StoreException en caso de algun problema a la hora de obtener el usuario de la capa de persistencia
     */
    public boolean checkConfirmPassword(String hash, String passConfirm) throws StoreException
    {
        if (log.isDebugEnabled()){
            log.info("INIT ServicioUsuariosImpl.checkConfirmPassword.");
        }
        boolean retorno = false;
        if (hash == null || (hash!=null && hash.equals("")))
        {
            if (log.isDebugEnabled()){
                log.warn("ATENCION!!. Email proporcionado es nulo.");
            }
            return retorno;
            //throw new StoreException("30");
        }
        if (passConfirm==null || (passConfirm!=null && passConfirm.equals("")))
        {
            if (log.isDebugEnabled()){
                log.warn("ATENCION!!. passConfirm proporcionado es nulo.");
            }
            return retorno;
            //throw new StoreException("31");
        }
        //buscamos en la cache y en BBDD en caso de fallo
        Usuarios usuario = this.getUser(hash);
        if (usuario == null)
        {
            if (log.isDebugEnabled()){
                log.warn("ATENCION. Persona no encontrada en el sistema.");
            }
            return retorno;
            //throw new StoreException("32");
        }
        String passActual = usuario.getPassConfirm();
        retorno= (passActual.equals(passConfirm));
        if (log.isDebugEnabled()){
            StringBuilder sbEnd = new StringBuilder("END ServicioUsuariosImpl.checkConfirmPassword: retorno: ").append(retorno);
            log.info(sbEnd);
        }
        return retorno;
    }

    /***
     * comprueba que el password es igual al almacenado
     * @param hash
     * @param pass
     * @return
     * @throws StoreException
     */
    public boolean checkPassword(String hash, String pass) throws StoreException
    {
        if (log.isDebugEnabled()){
            StringBuffer sb = new StringBuffer("INIT ServicioUsuariosImpl.checkPassword.")
                                        .append(" hash: ").append(hash)
                                        .append(" pass: ").append(pass);
            log.info(sb);
        }
        
        if (hash == null || (hash!=null && hash.equals("")))
        {
            if (log.isDebugEnabled()){
                log.warn("ATENCION. Email proporcionado es nulo.");
            }
            throw new StoreException("30");
        }
        if (pass==null || (pass!=null && pass.equals("")))
        {
            if (log.isDebugEnabled()){
                log.warn("ATENCION!! pass proporcionado es nulo.");
            }
            throw new StoreException("31");
        }
        //buscamos en la cache y en BBDD en caso de fallo
        Usuarios usuario = this.getUser(hash);
        if (usuario == null)
        {
            if (log.isDebugEnabled()){
                log.warn("Persona no encontrada en el sistema.");
            }
            throw new StoreException("32");
        }
        String passActual = usuario.getPassword();
        boolean retorno= (passActual.equals(pass));
        if (log.isDebugEnabled()){
            StringBuilder sbEnd = new StringBuilder("END ServicioUsuariosImpl.checkPassword: retorno: ").append(retorno);
            log.info(sbEnd);
        }
        return retorno;
    }

    public Long addHistoricoUsuarioItem(HistoricoUsuariosItems historicoUsuariosItems) throws StoreException
    {
        if (log.isDebugEnabled()){
            StringBuffer sbInit = new StringBuffer("Init ServicioUsuariosImpl.addHistoricoUsuarioItem.");
            sbInit.append("historicoUsuariosItems: ").append(historicoUsuariosItems.toString());
        }
        Long generatedKey = this.servicioPersistenciaUsuarios.addHistoricoUsuarioItem(historicoUsuariosItems);
        if (log.isDebugEnabled()){
            StringBuffer sbInit = new StringBuffer("End ServicioUsuariosImpl.addHistoricoUsuarioItem.");
            sbInit.append("generatedKey: ").append(generatedKey!=null?generatedKey.toString(): "KO");
        }
        return generatedKey;
    }

    /**
     * @return the servicioEncriptacion
     */
    public ServicioEncriptacion getServicioEncriptacion() {
        return servicioEncriptacion;
    }

    /**
     * @param servicioEncriptacion the servicioEncriptacion to set
     */
    public void setServicioEncriptacion(ServicioEncriptacion servicioEncriptacion) {
        this.servicioEncriptacion = servicioEncriptacion;
    }

    /**
     * @return the servicioPersistenciaUsuarios
     */
    public ServicioPersistenciaUsuarios getServicioPersistenciaUsuarios() {
        return servicioPersistenciaUsuarios;
    }

    /**
     * @param servicioPersistenciaUsuarios the servicioPersistenciaUsuarios to set
     */
    public void setServicioPersistenciaUsuarios(ServicioPersistenciaUsuarios servicioPersistenciaUsuarios) {
        this.servicioPersistenciaUsuarios = servicioPersistenciaUsuarios;
    }

        
}
