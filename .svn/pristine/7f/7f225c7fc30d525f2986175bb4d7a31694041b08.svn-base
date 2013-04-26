package com.aironman.core.hibernate;

import com.aironman.core.exceptions.StoreException;
import com.aironman.core.pojos.Items;
import com.aironman.core.utils.GenericDao;
import java.util.List;

/**
 * @author Alonso Isidoro Roman
 *
 */
public interface ItemDao extends GenericDao<Items, String> {
    
    /**
     * 
     * @return
     * @throws StoreException
     */
        List<Items> findAll()                    throws StoreException;

	Items findByIsbn(String isbn)                  throws StoreException;
	
        /**
         * 
         * @param isbn 
         * @return
         * @throws StoreException
         */
        boolean exists(String isbn)                 throws StoreException;
	
}