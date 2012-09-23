/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aironman.core.service;

import com.aironman.core.exceptions.StoreException;
import com.aironman.core.pojos.Items;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Alonso Isidoro Roman
 */
@Service("servicioItems")
public class ServicioItemsImpl implements ServicioItems{

    /**
     *
     */
    private static final ConcurrentHashMap 
            <String,com.aironman.core.pojos.Items>
            //La pk es el isbn del item
            hashMapItems = new ConcurrentHashMap<String,com.aironman.core.pojos.Items>() ;

    private Log log = LogFactory.getLog(ServicioItemsImpl.class);

    @Autowired
    //@Qualifier("servicioPersistenciaItems")
    private ServicioPersistenciaItems servicioPersistenciaItems;

    public ServicioItemsImpl()
    {
        if (log.isDebugEnabled()){
            log.info("Constructor SIN TIPO ServicioItemsImpl");
        }
    }
    
    @PostConstruct
    public void init() throws StoreException
    {
        if (log.isDebugEnabled()){
            log.info("init method on ServicioItemsImpl. Initializing hashMap...");
         }
        try {

            //TODO AQUI habra que hacer una llamada al servicio de persistencia para rellenar el hashMap
            //ServicioItemsImpl.hashMapItems.putAll(servicioPersistenciaItems.getAllItems());
            List <Items> listaItems = servicioPersistenciaItems.getAllItems();
            for (Items _item:listaItems)
            {
                ServicioItemsImpl.hashMapItems.putIfAbsent(_item.getIsbn(), _item);
                if (log.isDebugEnabled()){
                    log.info("Item con isbn " + _item.getIsbn() + " INTRODUCIDO EN LA CACHE...");
                }
            }
        } catch (StoreException ex) {
            log.warn("EXCEPCION GRAVE AL RELLENAR EL HASHMAP DE LOS ITEMS MEDIANTE EL SERVICIO DE PERSISTENCIA", ex);
            //no querremos seguir si esto ocurre. lanzando excepcion.
            throw ex;
        }finally{
            log.info("hashMap INITIALIZED. numItems: " + ServicioItemsImpl.hashMapItems.size());

        }
        
    }
    
    /***
     * añadir item al mapa y bbdd
     * @param item
     * @return
     * @throws StoreException
     */
    public boolean addItemToSystem(Items item) throws StoreException
    {
        boolean retornoMapa=false;
        
        if (item==null || (item!=null && item.getIsbn()!=null && item.getDescripcion()!=null
                            && item.getIsbn().equals("")
                            && item.getDescripcion().equals("")))
        {
            if (log.isDebugEnabled())
                log.warn("ServicioItemsImpl. METHOD: addItemToSystem. ERROR, item es NULO, bien el isbn o la descripcion del mismo.");
            throw new StoreException("5");
        }

        if (log.isDebugEnabled()){
        //esta es la forma chula y eficiente de usar el logger
            StringBuilder sbInitAddItemToSystem = new StringBuilder("ServicioItemsImpl. METHOD: addItemToSystem. ISBN: ")
                                                .append(item.getIsbn()).append(" descripcion: ").append(item.getDescripcion());
            log.info(sbInitAddItemToSystem);
        }
        try
        {
            //compruebo que no la tengo en la cache ni en BBDD
            com.aironman.core.pojos.Items itemToCreate = this.getItem(item.getIsbn());
            if (itemToCreate!=null)
            {
                //if (log.isDebugEnabled())
                    log.warn("UN ITEM CON ESE ISBN YA SE ENCUENTRA EN EL SISTEMA.CANCELANDO INSERCION.");
            }
            else
            {
                //SE INTRODUCE EN EL MAPA
                retornoMapa=addItemToMap(item);
                //LO PERSISTO
                this.getServicioPersistenciaItems().addItemToSystem(item);
            }
            
        }catch(UnsupportedOperationException ex)
        {
            log.warn("ServicioItemsImpl.addItemToSystem. MOTIVO: UnsupportedOperationException",ex);
            throw new StoreException(ex,"1");
        }
        catch(ClassCastException  ex)
        {
            log.warn("ServicioItemsImpl.addItemToSystem. MOTIVO: ClassCastException",ex);
            throw new StoreException(ex,"2");
        }
        catch(IllegalArgumentException ex)
        {
            log.warn("ServicioItemsImpl.addItemToSystem. MOTIVO: IllegalArgumentException",ex);
            throw new StoreException(ex,"3");
        }
        catch(NullPointerException ex)
        {
            log.warn("ServicioItemsImpl.addItemToSystem. MOTIVO: NullPointerException",ex);
            throw new StoreException(ex,"4");
        }
        catch(StoreException ex)
        {
            log.warn("ServicioItemsImpl.addItemToSystem. MOTIVO: StoreException",ex);
            //la remonto? por ahora si, pero me da que no tendria que hacerlo...
            //throw ex;
        }
        if (log.isDebugEnabled()){
            StringBuilder sbEnd = new StringBuilder("END ServicioItemsImpl.addItemToSystem. retornoMapa:")
                                                    .append(retornoMapa);

            log.info(sbEnd);
        }
        return retornoMapa;
    }

    /***
     * añado item al mapa 
     * @param item
     * @return
     */
    private boolean addItemToMap(com.aironman.core.pojos.Items item) {

        StringBuilder sbInit = new StringBuilder("Init ServicioItemsImpl.addItemToMap...");
        log.info(log);
        boolean retorno=false;
        Object putIfAbsent = ServicioItemsImpl.hashMapItems.putIfAbsent(item.getIsbn(), item);
        //returns previous value associated with specified key, or null if there was no mapping for key.
        //si devuelve nulo significa que no HABIA un elemento previamente introducido en el mapa con esa clave
        if (putIfAbsent == null /*&& putIfAbsent.equals(item)*/)
        {
            retorno = true;
        }
        StringBuilder sb = new StringBuilder("ServicioItemsImpl. METHOD: addItemToMap. ")
                                            .append("isbn: ").append(item.getIsbn())
                                            .append(putIfAbsent == null ? " OK":" KO")
                                            .append(" retorno: ").append(retorno);
        log.info(sb);

        return retorno;
    }

    public boolean updateItem(Items item) throws StoreException
    {
        StringBuffer sbInit = new StringBuffer("Init ServicioItemsImpl.updateItem...");
        log.info(sbInit);
        boolean retorno=false;
        if (item==null || (item!=null && item.getIsbn()!=null && item.getIsbn().equals("")))
        {
                log.warn("ServicioItemsImpl. METHOD: updateItem. ERROR, item entrante por parametro es NULO.");
                throw new StoreException("5");

        }
        //en la cache guardaré los pojos hibernate, en ppio es buena idea
        Items _item =this.getItem(item.getIsbn());
        if (_item==null)
        {
            log.warn("ServicioItemsImpl. METHOD: updateItem. ERROR. el item ES NULO,  no existe en el sistema...");
            //StoreException(String _codigo,String _legajo,String _isbn)
            throw new StoreException("5");
        }
        //este primera condicion deberia ser implicita... o la ultima
        else if(_item!=null && item.getIsbn()!=null)
        {
            StringBuffer sb = new StringBuffer("ServicioItemsImpl.updateItem. Actualizando mapa. isbn: ")
                                                .append(item.getIsbn());
            log.info(sb);
            retorno=true;
            Items oldItem=null;
            do
            {
              oldItem = ServicioItemsImpl.hashMapItems.get(item.getIsbn());
            }while(ServicioItemsImpl.hashMapItems.replace(item.getIsbn(), oldItem, item) && oldItem!=item);
            StringBuffer sbUpdateMap = new StringBuffer("ServicioItemsImpl.updateItem. mapa actualizado.");
            log.info(sbUpdateMap);

            //tienes que hacer la implementacion de este metodo!!! solo lo tienes declarado en la interfaz.
            this.getServicioPersistenciaItems().updateItem(item);
        }
        StringBuffer sbEnd = new StringBuffer("End ServicioItemsImpl.updateItem... retorno: ").append(retorno);
        log.info(sbEnd);

        return retorno;
    }

    public String getDescriptionItem(String isbn) throws StoreException
    {
        StringBuilder sbInit = new StringBuilder("ServicioItemsImpl. INIT getDescriptionItem. ISBN: ").append(isbn);
        log.info(sbInit);
        
        //esto lo mismo, no deberia invocar al mapa, deberia invocar al metodo getItem que ya se encarga de buscar en la cache y en caso de error
        //en bbdd
        com.aironman.core.pojos.Items item = (Items ) this.getItem(isbn);

        //com.aironman.core.pojos.Item item = (com.aironman.core.pojos.Item ) ServicioItemsImpl.hashMapItems.get(isbn);
        if (item==null)
        {
            StringBuilder sbItemNull = new StringBuilder("el item no existe en el sistema. ISBN: ").append(isbn);
            log.warn(sbItemNull);
            throw new StoreException("5");
        }
        StringBuilder sbEnd = new StringBuilder("ServicioItemsImpl. END getDescriptionItem. ")
                                            .append(" isbn: ").append(item.getIsbn()).append(" descripcion: ").append(item.getDescripcion());
        log.info(sbEnd);
        return item.getDescripcion();
        
    }

    public float getPrizeItem(String isbn) throws StoreException
    {

        StringBuilder sbInit = new StringBuilder("ServicioItemsImpl. INIT getPrizeItem. ISBN: ").append(isbn);
        log.info(sbInit);

        com.aironman.core.pojos.Items item = this.getItem(isbn);

        if (item==null)
        {
            StringBuilder sbItemNull = new StringBuilder("ServicioItemsImpl. METHOD: getPrizeItem. ERROR. el item no existe en el sistema. ISBN: ").append(isbn);
            log.warn(sbItemNull);
            throw new StoreException("6");
        }
        StringBuilder sbEnd = new StringBuilder("ServicioItemsImpl. END getPrizeItem. precio:")
                                .append((item !=null && item.getPrecio() > 0? item.getPrecio():"WARNING"));
        
        log.info(sbEnd);
        
        return item.getPrecio();

    }

    /***
     * 
     * @param isbn
     * @return
     * @throws StoreException
     */
    public Items getItem(String isbn)throws StoreException
    {
        StringBuilder sbInit = new StringBuilder("ServicioItemsImpl. INIT getItem. ISBN: ").append(isbn);
        log.info(sbInit);

        Items item = ServicioItemsImpl.hashMapItems.get(isbn);
        if (item==null)
        {
            StringBuilder sbItemNull = new StringBuilder("ServicioItemsImpl. METHOD: getItem. ERROR. el item no existe la cache. Buscando en BBDD.")
                                        .append(isbn);
            log.warn(sbItemNull);
            //item = new Item();
            //que no este en la cache no implica que no pueda estar en la BBDD
            //habria que hacer una llamada al servicio de persistencia de los items, buscarlo y si lo encuentro, introducirlo en la cache y devolverlo
            //si no lo encuentro, devolver la excepcion.
            item = this.getServicioPersistenciaItems().getItem(isbn);
            if (item==null)
            {//esto no deberia ocurrir nunca, pues si ha capturado el isbn de un item de la tienda y no lo encontramos ni en la cache ni en BBDD...
                StringBuilder sbItemNullPersistency = new 
                StringBuilder("ServicioItemsImpl. METHOD: getItem. ERROR. El item NO se encuentra en BBDD ni en la cache" +
                        "       . Se encuentra realmente en una tienda del sistema?");
                log.warn(sbItemNullPersistency);
                //throw new StoreException("27");
            }else{
                StringBuilder sbItemNotNullPersistency = new StringBuilder("ServicioItemsImpl. METHOD: getItem. ERROR" +
                        "                                   . El item SI se encuentra en BBDD. Introduciendolo en la cache");
                ServicioItemsImpl.hashMapItems.putIfAbsent(isbn, item);
            }
        }
        else{
            StringBuilder sbEnd = new StringBuilder("ServicioItemsImpl. END getItem.  ISBN: ").append(item.getIsbn());
            log.info(sbEnd);
        }
        
        return item;

    }

    public Collection<Items> getAllItems() {
        if (log.isDebugEnabled()){
            log.info("Init ServicioItemsImpl.getAllItems");
        }
        Collection<Items> listaItems = ServicioItemsImpl.hashMapItems.values();
        if (log.isDebugEnabled()){
            log.info("END Init ServicioItemsImpl.getAllItems. numItems: " + listaItems.size());
        }
        return listaItems;
    }

    /***
     * metodo que borra el item tanto de bbdd como de la cache...
     * @param item
     * @throws StoreException si ha habido algun problema con la bbdd, en ese caso hace un rollback
     */
    public void deleteItemFromSystem(Items item) throws StoreException {

        log.info("Init ServicioItemsImpl.deleteItemFromSystem. isbn: " + item.getIsbn());
        //primero lo borro de la capa de persistencia
        this.servicioPersistenciaItems.deleteItemFromSystem(item);
        log.info("deleted from persistence layer...");
        //luego de la cache
        Items oldItem=null;
        do
        {
            oldItem = ServicioItemsImpl.hashMapItems.get(item.getIsbn());
        }while(ServicioItemsImpl.hashMapItems.remove(item.getIsbn(), item));
        log.info("deleted from hashMap...");
        log.info("End ServicioItemsImpl.deleteItemFromSystem...");
    }

    /**GETTTERS AND SETTERS*/
    public ServicioPersistenciaItems getServicioPersistenciaItems() {
        return servicioPersistenciaItems;
    }

    public void setServicioPersistenciaItems(ServicioPersistenciaItems servicioPersistenciaItems) {
        this.servicioPersistenciaItems = servicioPersistenciaItems;
    }

}
