/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aironman.core.service;

import com.aironman.core.exceptions.StoreException;
import com.aironman.core.pojos.Items;
import java.util.Collection;

/**
 * clase que va a gestionar los items disponibles en el sistema
 * Un item va a tener un isbn como clave en el mapa, el objeto a guardar sera
 * un item que contendra metainformacion
 * el hashMap va a actuar al igual que en todos los servicios como una cache.
 * La persistencia se hara con un conector. Me gusta la idea de enviar el item a persistir
 * pasando la info mediante mensaje a otra maquina remota, mediante un ESB? jms?
 * @author alonso
 */
public interface ServicioItems {

    boolean addItemToSystem(Items item)                                      throws StoreException;
    void deleteItemFromSystem(Items item)                                 throws StoreException;
    boolean updateItem(Items item)                                           throws StoreException;
    String getDescriptionItem(String isbn)                                  throws StoreException;
    float getPrizeItem(String isbn)                                        throws StoreException;
    Items getItem(String isbn)                                               throws StoreException;
    Collection<Items> getAllItems()                                          throws StoreException;
}
