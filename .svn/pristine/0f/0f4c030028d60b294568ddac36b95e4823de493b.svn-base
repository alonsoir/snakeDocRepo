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
public class OperationLogInCallable implements Callable<Boolean>{

    private Log log = LogFactory.getLog(OperationLogInCallable.class);

    private String                      hash;
    private String                      pass;
    private ServicioUsuarios            servicioUsuarios;

    public OperationLogInCallable(String hash,String pass,ServicioUsuarios servicioUsuarios)
    {
        StringBuffer sb = new StringBuffer("Constructor TIPADO OperationLogInCallable. hash: ")
                                            .append(hash).append(" pass: ").append(pass);
        log.info(sb);
        this.hash=hash;
        this.pass=pass;
        this.servicioUsuarios=servicioUsuarios;
    }

    /**
     * LANZA EL HILO para la operacion logIn. Operacion thread safe
     * @return true OK, false KO
     * @throws StoreException
     */
    public Boolean call() throws StoreException
    {
        //el compilador no se queja, me parece que esta haciendo de forma automatica el autoboxing.
        boolean retorno=false;
        StringBuffer sbInit = new StringBuffer("INIT OperationLogInCallable.call...")
                                        .append(" hash: ").append(hash).append(" pass: ").append(pass);
        log.info(sbInit);

        boolean retornoCheckPass = getServicioUsuarios().checkPassword(hash, pass);
        if (!retornoCheckPass)
        {
            StringBuffer sbCheckPass = new StringBuffer("OperationLogInCallable. call method. persona con hash: ")
                                            .append(hash).append(" y pass: ").append(pass)
                                            .append(" NO COINCIDE con ningun usuario.CANCELANDO ACCESO.");
            log.warn(sbCheckPass);
            return retorno;
        }
        Usuarios usuario = getServicioUsuarios().getUser(hash);
        if (usuario == null)
        {
            StringBuffer sbPersonaNull = new StringBuffer("OperationLogInCallable. logIn persona con hash: ")
                                                            .append(hash).append(" NO encontrada.");
            log.warn(sbPersonaNull);
            return retorno;
        }
        //true activo, false inactivo. Por defecto inactivo.
        //activo: usuario logado en el sistema. Para que se loguee el cliente debera enviar las coordenadas adecuadas para cada tienda...
        else
        {
            StringBuffer sbPersonaEncontrada = new  StringBuffer("OperationLogInCallable. persona encontrada.");
            log.info(sbPersonaEncontrada);

            //if (usuario.isStatus())
            if (usuario.getStatus().equalsIgnoreCase(Constantes.estadoUsuarioActivo))
            {
                StringBuffer sbIsStatus = new StringBuffer("WARNING. El usuario ya se encuentra logado...");
                log.warn(sbIsStatus);
                retorno=true;
                //no tengo que actualizar el estado del usuario, o a lo mejor si...
                //throw new StoreException("8",legajo,null);
            }else
            {
                usuario.setStatus(Constantes.estadoUsuarioActivo);
                //esta operacion es de hilo seguro?
                retorno=getServicioUsuarios().updateUser(usuario);
            }
            StringBuffer sbEnd  = new StringBuffer("END OperationLogInCallable. personaActualizada: ").append(retorno ? "OK" : "KO");
            log.info(sbEnd);
        }
        return retorno;
    }

    /*GETTERS AND SETTERS*/

    public ServicioUsuarios getServicioUsuarios() {
        return servicioUsuarios;
    }

}