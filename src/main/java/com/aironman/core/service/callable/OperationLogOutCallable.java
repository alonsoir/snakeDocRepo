/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aironman.core.service.callable;

import com.aironman.core.exceptions.StoreException;
import com.aironman.core.pojos.Usuarios;
import com.aironman.core.service.ServicioUsuarios;
import com.aironman.core.utils.Constantes;
import java.util.concurrent.Callable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author alonso
 */
public class OperationLogOutCallable implements Callable<Boolean>{

    private static final Log            log = LogFactory.getLog(OperationLogOutCallable.class);

    private String                      hash;
    
    private ServicioUsuarios            servicioUsuarios;



    public OperationLogOutCallable(String hash,ServicioUsuarios servicioUsuarios)
    {
        if (log.isDebugEnabled()){
            StringBuffer sb = new StringBuffer("Constructor TIPADO OperationLogOutCallable. hash: ")
                                            .append(hash);
            log.info(sb);
        }
        this.hash=hash;
        this.servicioUsuarios=servicioUsuarios;
    }

    /**
     * LANZA EL HILO para la operacion logOut. Operacion thread safe
     * @return true OK, false KO
     * @throws StoreException en caso de error a la hora de actualizar el usuario
     */
    public Boolean call() throws StoreException
    {
        if (log.isDebugEnabled()){
            StringBuffer sbINIT = new StringBuffer("INIT OperationLogOutCallable.logOut . hash: ")
                                .append(hash);
            log.info(sbINIT);
        }

        boolean retorno=false;
        Usuarios usuario = getServicioUsuarios().getUser(hash);
        if (usuario == null)
        {
            if (log.isDebugEnabled()){
                StringBuffer sbPersonaNull = new StringBuffer("OperationLogOutCallable. logOut persona con hash: ")
                                            .append(hash).append(" NO encontrada.CANCELANDO OPERACION LOGOUT.");
                log.warn(sbPersonaNull);
            }
            return retorno;
            //throw new StoreException("9");
        }
        //A activo, I inactivo. Por defecto inactivo.
        //activo: usuario logado en el sistema. Para que se loguee el cliente debera enviar las coordenadas adecuadas para cada tienda...
        if (usuario.getStatus().equalsIgnoreCase(Constantes.estadoUsuarioActivo))
        {
            if (log.isDebugEnabled()){
                StringBuffer sbIsStatus = new StringBuffer("WARNING. CAMBIANDO EL ESTADO del usuario con hash: ").append(hash);
                log.info(sbIsStatus);
            }
            usuario.setStatus(Constantes.estadoUsuarioInactivo);
            retorno=getServicioUsuarios().updateUser(usuario);
        }else{
            if (log.isDebugEnabled())
            {
                StringBuffer sbUserNoLogado = new StringBuffer("WARNING. El usuario con hash: ")
                                .append(hash).append(" no se encuentra logado en la tienda, por lo que la operacion de logout no se realizará.");
                log.info(sbUserNoLogado);
            }
        }

        if (log.isDebugEnabled()){
            StringBuffer sbEND = new StringBuffer("END OperationLogOutCallable.logOut . hash: ")
                                .append(hash).append(" RETORNO: ").append(retorno);
            log.info(sbEND);
        }
        return retorno;
    }

    /*GETTERS AND SETTERS*/
    
    public ServicioUsuarios getServicioUsuarios() {
        return servicioUsuarios;
    }
}