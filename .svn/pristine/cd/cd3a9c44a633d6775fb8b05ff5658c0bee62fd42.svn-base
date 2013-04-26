/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aironman.core.service;

import com.aironman.core.exceptions.StoreException;
import java.util.List;

/**
 *
 * @author alonso
 */
public interface ServicioPersistenciaItems {

    /**
     * 
     * @param _item
     * @return
     * @throws StoreException 
     */
    void addItemToSystem(com.aironman.core.pojos.Items _item)                    throws StoreException;
    void deleteItemFromSystem(com.aironman.core.pojos.Items _item)               throws StoreException;
    void updateItem(com.aironman.core.pojos.Items _item)                         throws StoreException;
    List<com.aironman.core.pojos.Items> getAllItems()                            throws StoreException;
    com.aironman.core.pojos.Items getItem(String isbn)                           throws StoreException;

}
