/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aironman.core.service.callable;

import com.aironman.core.exceptions.StoreException;
import com.aironman.core.service.ServicioItems;
import java.util.concurrent.Callable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author alonso
 */
public class OperationGetDescriptionItemCallable implements Callable<String>{

    private Log log = LogFactory.getLog(OperationGetDescriptionItemCallable.class);

    private String                      isbn;

    private ServicioItems               servicioItems;

    public OperationGetDescriptionItemCallable(String isbn,ServicioItems servicioItems)
    {
        if (log.isDebugEnabled()){
            StringBuffer sb =  new StringBuffer("Constructor tipado OperationGetDescriptionItemCallable. isbn: ").append(isbn);
            log.info("Constructor tipado OperationGetDescriptionItemCallable...");
        }
        this.isbn=isbn;
        this.servicioItems=servicioItems;
    }

    /***
     * lanza el hilo que consulta la descripcion del item dado un isbn
     * @return la descripcion
     * @throws StoreException en caso de problemas a la hora de acceder al backend
     */
    public String call() throws StoreException {
        if (log.isDebugEnabled()){
            log.info("Init OperationGetDescriptionItemCallable.call.");
        }
        String description = this.getServicioItems().getDescriptionItem(isbn);
        if (log.isDebugEnabled()){
            StringBuffer sbEnd = new StringBuffer("End OperationGetDescriptionItemCallable.call. description: ").append(description);
            log.info(sbEnd);
        }
        return description;
    }
    
    public ServicioItems getServicioItems() {
        return servicioItems;
    }

}
