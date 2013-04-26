/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aironman.core.service;

import com.aironman.core.exceptions.StoreException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;


/**
 *
 * @author alonso
 */
@Service("servicioUtiles")
public class ServicioUtilesImpl implements ServicioUtiles{

    private Log log = LogFactory.getLog(ServicioUtilesImpl.class);

    public ServicioUtilesImpl()
    {
        if (log.isDebugEnabled()){
        log.info("Constructor ServicioUtilesImpl");
        }
    }
    
    public String nombreToHash(String nombre) throws StoreException
    {
        if (log.isDebugEnabled()){
            StringBuffer sb = new StringBuffer("Init ServicioUtilesImpl.nombreToHash. nombre: ")
                                                .append(nombre);
            log.info(sb);
        }
        if (nombre == null || (nombre!=null && nombre.equals("")))
        {
            log.warn("ATENCION! ServicioUtilesImpl.nombreToHash. Nombre no puede ser nulo o vacio. ");
            throw new StoreException("26");
        }
        String hash = String.valueOf(nombre.hashCode());
        if (log.isDebugEnabled()){
            StringBuilder sbEnd = new StringBuilder("End ServicioUtilesImpl.nombreToHash. nombre: ")
                                    .append(nombre).append(" hash: ").append(hash);
            log.info(sbEnd);
        }
        
        return hash;
    };
}
