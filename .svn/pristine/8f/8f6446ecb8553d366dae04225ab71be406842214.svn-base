package com.aironman.core.hibernate;

import com.aironman.core.exceptions.StoreException;
import com.aironman.core.pojos.Usuarios;
import com.aironman.core.utils.GenericDao;
import java.util.List;

/**
 * @author Alonso Isidoro Roman
 *
 */
public interface UsuarioDao extends GenericDao<Usuarios, String> {
    
	List<Usuarios> findAll()                       throws StoreException;

	Usuarios findByUserName(String nom)            throws StoreException;

        Usuarios findByLegajo(String legajo)           throws StoreException;

        Usuarios findByEmail(String email)             throws StoreException;

	boolean exists(String legajo)                 throws StoreException;
	
}