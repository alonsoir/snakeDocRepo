/**
 * 
 */
package com.aironman.core.cxf.service;

import com.aironman.core.exceptions.StoreException;
import com.aironman.core.pojos.Items;
import com.aironman.core.service.CoreService;
import javax.jws.WebService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/***
 * implementacion del Endpoint del WS
 * @author alonso
 */
@Service("storeServiceMT")
@WebService(endpointInterface = "com.aironman.core.cxf.service.StoreService")
public class StoreServiceMultiThreadImpl implements StoreService {

    //TODO tienes que superar todos los test unitarios
    //TODO tienes que hacer que de la salida en formato json ademas de xml
    private static final Log log = LogFactory.getLog(StoreServiceMultiThreadImpl.class);
    @Autowired
    private CoreService coreService;
    
    public StoreServiceMultiThreadImpl(){
        if (log.isDebugEnabled()){
            log.info("CONSTRUCTOR SIN tipo StoreServiceMultiThreadImpl...");
        }
    }

    /***
     * Metodo que hace el logIn al sistema
     * @param email
     * @param pass
     * @param lat
     * @param lon
     * @return true OK false KO
     * @throws StoreException
     */
    public boolean logIn(String hash,String pass, String lat,String lon) throws StoreException
    {
        if (log.isDebugEnabled()){
            StringBuffer sbInit = new StringBuffer("Init StoreServiceMultiThreadImpl.logIn. hash: ")
                                            .append(hash)
                                            .append(" pass: ").append(pass);
            log.info(sbInit);
        }
        boolean retorno = this.getCoreService().logIn(hash, pass, lat, lon);
        if (log.isDebugEnabled()){
            StringBuffer sbEnd = new StringBuffer("End StoreServiceMultiThreadImpl.logIn. hash: ")
                                            .append(hash)
                                            .append(" pass: ").append(pass)
                                            .append(" retorno: ").append(retorno);
            log.info(sbEnd);
        }
        
        return retorno;
    }

    /**
     Metodo que hace el logout del sistema. deja el campo activo de la persona encontrada a false
     * Estos dos metodos logIn y logOut son exactamente iguales en las dos implementaciones, por lo que debo hacer que StoreAdminImpl y StoreImpl
     * hereden una clase base que contenga estos dos metodos.
     * @param email 
     * @return
     * @throws StoreException 
     */
    public boolean logOut(String hash) throws StoreException
    {
        if (log.isDebugEnabled()){
            StringBuffer sbInit = new StringBuffer("Init StoreServiceMultiThreadImpl.logOut. hash: ")
                                            .append(hash);
            log.info(sbInit);
        }
        boolean retorno = this.getCoreService().logOut(hash);
        if (log.isDebugEnabled()){
                StringBuffer sbEnd = new StringBuffer("End StoreServiceMultiThreadImpl.logOut. hash: ")
                                            .append(hash)
                                            .append(" retorno: ").append(retorno);
                log.info(sbEnd);
        }
        
        return retorno;
    }

    /*
        Metodo que se encarga de añadir un item dado su isbn en el carrito del usuario idUsuario identificado en el sistema
     * @param email el identificador del usuario logado en el sistema que quiere añadir en su carrito el producto identificado por el isbn
       @param isbn el identificador del producto
     **/
    public boolean addItemToCart(String hash, String isbn) throws StoreException
    {
        if (log.isDebugEnabled()){
            StringBuffer sbInit = new StringBuffer("Init StoreServiceMultiThreadImpl.addItemToCart. hash: ")
                                            .append(hash)
                                            .append(" isbn: ").append(isbn);
            log.info(sbInit);
        }
        boolean retorno = this.getCoreService().addItemToCart(hash, isbn);
        
        if (log.isDebugEnabled()){
            StringBuffer sbEnd = new StringBuffer("End StoreServiceMultiThreadImpl.addItemToCart. hash: ")
                                            .append(hash)
                                            .append(" isbn: ")
                                            .append(isbn)
                                            .append(" retorno: ").append(retorno);
            log.info(sbEnd);
        }

        
        return retorno;
    }

    /***
     * Metodo que se encarga de borrar de la lista de la compra del usuario el item identificado por el isbn
     * @param hash el hash del usuario
     * @param isbn
     * @return
     * @throws StoreException
     */
    public boolean deleteItemFromCart(String hash, String isbn) throws StoreException
    {
        if (log.isDebugEnabled()){
        StringBuffer sbInit = new StringBuffer("Init StoreServiceMultiThreadImpl.deleteItemFromCart. hash: ")
                                            .append(hash)
                                            .append(" isbn: ").append(isbn);
        log.info(sbInit);
        }
        boolean retorno = this.getCoreService().deleteItemFromCart(hash, isbn);
        if (log.isDebugEnabled()){
            StringBuffer sbEnd = new StringBuffer("End StoreServiceMultiThreadImpl.deleteItemFromCart. hash: ")
                                            .append(hash)
                                            .append(" isbn: ")
                                            .append(isbn)
                                            .append(" retorno: ").append(retorno);
            log.info(sbEnd);
        }

        return retorno;
    }
    /***
     * METODO que se encarga de confirmar el carro del usuario
     * @param hash que representa al usuario
     * @param passConfirm el pass que coonfirma la compra
     * @return true si OK, false KO
     * @throws StoreException
     */
    public boolean confirmCart(String hash,String passConfirm) throws StoreException
    {
        if (log.isDebugEnabled()){
        StringBuffer sbInit = new StringBuffer("Init StoreServiceMultiThreadImpl.confirmCart. hash: ")
                                            .append(hash)
                                            .append(" passConfirm: ").append(passConfirm);
        log.info(sbInit);
        }
        boolean retorno = this.getCoreService().confirmCart(hash, passConfirm);
        if (log.isDebugEnabled()){
            StringBuffer sbEnd = new StringBuffer("End StoreServiceMultiThreadImpl.confirmCart. hash: ")
                                            .append(hash)
                                            .append(" passConfirm: ")
                                            .append(passConfirm)
                                            .append(" retorno: ").append(retorno);
            log.info(sbEnd);
        }

        return retorno;
    }

    public String getDescriptionItem(String isbn) throws StoreException
    {
        if (log.isDebugEnabled()){
            StringBuffer sbInit = new StringBuffer("Init StoreServiceMultiThreadImpl.getDescriptionItem. isbn: ")
                                            .append(isbn);
            log.info(sbInit);
        }
        String description = this.getCoreService().getDescriptionItem(isbn);
        if (log.isDebugEnabled()){
            StringBuffer sbEnd = new StringBuffer("End StoreServiceMultiThreadImpl.getDescriptionItem. isbn: ")
                                            .append(isbn)
                                            .append(" description: ").append(description);
            log.info(sbEnd);
        }

        return description;
    }

    /*
     Este metodo va a traerte el item y va a meterlo en la lista de la compra de la persona que lo ha solicitado
     */
    public Items getItem(String hash,String isbn) throws StoreException
    {
        if (log.isDebugEnabled()){
            StringBuffer sbInit = new StringBuffer("Init StoreServiceMultiThreadImpl.getItem. isbn: ")
                                            .append(isbn)
                                            .append(" hash: ")
                                            .append(hash);
            log.info(sbInit);
        }
        Items item = this.getCoreService().getItem(hash, isbn);
        if (log.isDebugEnabled()){
            StringBuffer sbEnd = new StringBuffer("End StoreServiceMultiThreadImpl.getItem. isbn: ")
                                            .append(isbn)
                                            .append(" hash: ")
                                            .append(hash)
                                            .append(" item: ").append(item);
            log.info(sbEnd);
        }

        return item;
    }
    /**
     * @return the coreService
     */
    public CoreService getCoreService() {
        return coreService;
    }

    /**
     * @param coreService the coreService to set
     */
    public void setCoreService(CoreService coreService) {
        this.coreService = coreService;
    }

}
