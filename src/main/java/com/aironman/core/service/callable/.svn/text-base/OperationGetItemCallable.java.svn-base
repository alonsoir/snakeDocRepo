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
 * Esta operacion se trae el item del sistema y al mismo tiempo lo pone en la lista de la compra del usuario.
 * @author alonso
 */
public class OperationGetItemCallable implements Callable<Items>{

    private Log log = LogFactory.getLog(OperationGetDescriptionItemCallable.class);

    private String                      hash;
    private String                      isbn;
    private ServicioUsuarios            servicioUsuarios;

    private ServicioItems               servicioItems;

    public OperationGetItemCallable(String hash,String isbn,ServicioUsuarios servicioUsuarios,ServicioItems servicioItems)
    {
        StringBuffer sb = new StringBuffer("Constructor tipado OperationGetItemCallable")
                                    .append(" hash: ").append(hash)
                                    .append(" isbn: ")
                                    .append(isbn);
        this.hash=hash;
        this.isbn=isbn;
        this.servicioItems=servicioItems;
        this.servicioUsuarios=servicioUsuarios;
    }
    /***
     * lanza el hilo que me trae el item y me lo pone en la lista de deseos del usuario
     * TODO igual me arrepiento de hacer dos cosas en una, NO ME QUEDA MAS REMEDIO??? check
     * @return el item o nulo en caso de no encontrarse
     * @throws StoreException en caso que el item ya se encuentre en la lista de los deseos
     */
    public Items call() throws StoreException 
    {
        if (log.isDebugEnabled()){
            StringBuffer sbInitGetItem = new StringBuffer("INIT OperationGetItemCallable.getItem. hash: ")
                                        .append(hash).append(" isbn: ").append(isbn);
            log.info(sbInitGetItem);
        }
        //tienes que hacer las comprobaciones de existencia del email y el isbn
        if (isbn==null || (isbn!=null && isbn.equals("")))
        {
            log.warn("El isbn no puede ser nulo o vacio. Cancelando operacion.");
            return null;
        }
        if (hash == null || (hash!=null && hash.equals("")))
        {
            log.warn("El hash no puede ser nulo o vacio. Cancelando operacion.");
            return null;
        }
        Items item = getServicioItems().getItem(isbn);
        if (item == null){
            if(log.isDebugEnabled()){
                log.info("ATENCION!!. El item no existe en el sistema. ");
            }
            //ahora retorno nulo, igual lo suyo seria lanzar una excepcion para que el cliente sepa a q atenerse.
            return null;
        }
        //traemos el item y lo metemos en la lista de la compra
        CarroDeLaCompra carro = getServicioUsuarios().getCarroFromUsuario(hash);
        if (carro ==null){
            if (log.isDebugEnabled()){
                log.info("ATENCION!! el usuario no tiene carro de la compra inicializado. Esta UD seguro que se ha dado de alta en la tienda?.");
            }
            //lo suyo seria lanzar alguna excepcion. Si esto ocurre es que un usuario bien por error,
            //bien por intento de probar suerte en el sistema, ha intentado corromper el sistema. Por ahora devuelvo null
            return null;
            //carro = getServicioUsuarios().crearCarro(hash);
        }
        else{
            boolean retornoInsercion = carro.addItemToCarro(item);
        
            if (log.isDebugEnabled()){

                StringBuffer sbRetornoInsercion = new StringBuffer("getItem. retornoInsercion: ").append(retornoInsercion);
                log.info(sbRetornoInsercion);
                if (!retornoInsercion)
                {
                    log.warn("El item ya estaba en la lista de los deseos del usuario.");
                    throw new StoreException("29");
                }
            }
        }
        return item;
  }

    /**
     * @return the servicioUsuarios
     */
    public ServicioUsuarios getServicioUsuarios() {
        return servicioUsuarios;
    }

    /**
     * @return the servicioItems
     */
    public ServicioItems getServicioItems() {
        return servicioItems;
    }

}
