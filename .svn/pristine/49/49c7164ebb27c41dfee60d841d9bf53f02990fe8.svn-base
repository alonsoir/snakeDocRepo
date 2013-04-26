/**
 * 
 */
package com.aironman.core.service;

import com.aironman.core.exceptions.StoreException;
import com.aironman.core.pojos.Items;

/**
 * Interfaz del servicio que sirve de punto de entrada al core del sistema.
 * @author Alonso Isidoro Roman
 *
 */

public interface CoreService
{
    boolean logIn(String hash
                 ,String pass
                 ,String lat
                 ,String lon)                                                           throws StoreException;

    boolean logOut(String hash)                                                        throws StoreException;

    boolean addItemToCart(String hash,String isbn)                                    throws StoreException;

    boolean deleteItemFromCart(String hash,String isbn)                               throws StoreException;

    boolean confirmCart(String hash,String pass)                                       throws StoreException;

    String getDescriptionItem(String isbn)                                              throws StoreException;

    Items getItem(String hash,String isbn)                                              throws StoreException;

}

