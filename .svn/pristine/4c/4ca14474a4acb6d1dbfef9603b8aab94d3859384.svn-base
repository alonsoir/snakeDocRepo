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
public class OperationAddItemToCartCallable implements Callable<Boolean>{

    private Log log = LogFactory.getLog(OperationAddItemToCartCallable.class);

    private String                      hash;
    private String                      isbn;
    private ServicioUsuarios            servicioUsuarios;
    private ServicioItems               servicioItems;


    public OperationAddItemToCartCallable(String hash,String isbn,ServicioUsuarios servicioUsuarios,ServicioItems servicioItems)
    {
        StringBuffer sb = new StringBuffer("Constructor TIPADO OperationAddItemToCartCallable.")
                                        .append(" hash: ").append(hash).append(" isbn: ").append(isbn);
        log.info(sb);
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
        StringBuffer sbINIT = new StringBuffer("INIT OperationAddItemToCartCallable.call . hash: ")
                                .append(hash).append(" isbn: ").append(isbn);
        log.info(sbINIT);


        Items item = this.getServicioItems().getItem(isbn);
        if (item == null)
        {
            StringBuffer sbItemNull = new StringBuffer("Error. NO SE ENCUENTRA EL ITEM EN EL SISTEMA. isbn: ").append(isbn);
            log.warn(sbItemNull);
            throw new StoreException("6");
        }
        boolean retorno=false;
        //sacamos el pojo del usuario del sistema, bien sea de la cache, bien de bbdd
        CarroDeLaCompra carro = this.getServicioUsuarios().getCarroFromUsuario(hash);
        if (carro == null)
        {
            if (log.isDebugEnabled()){
                log.info("ATENCION, el carro del usuario no existe. Esta Ud seguro que es usuario de la tienda?");
            }
            //igual me interesa lanzar una excepcion
            
        }else
             retorno= carro.addItemToCarro(item);

        StringBuffer sbEND = new StringBuffer("END OperationAddItemToCartCallable.addItemToCart . hash: ")
                                .append(hash).append(" isbn: ").append(isbn)
                                .append(" retorno: ").append(retorno?"OK":"KO");
        log.info(sbEND);

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
