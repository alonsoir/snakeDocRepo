package com.aironman.core.hibernate;

import com.aironman.core.exceptions.StoreException;
import com.aironman.core.pojos.ApuntesContables;
import com.aironman.core.utils.GenericDao;
import java.util.List;

/**
 * @author Alonso Isidoro Roman
 *
 */
public interface ApunteContableDao extends GenericDao<ApuntesContables, Long> {
    
	Long addApunteContable(ApuntesContables value)                        throws StoreException;

        ApuntesContables getApunteContableByClave(Long key)                   throws StoreException;

        List<ApuntesContables> getApuntesContableByLegajo(String legajo)      throws StoreException;

        List<ApuntesContables> getAllApuntes()                                throws StoreException;
	
}