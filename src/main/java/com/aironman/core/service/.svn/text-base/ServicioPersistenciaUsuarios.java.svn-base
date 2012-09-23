/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aironman.core.service;

import com.aironman.core.exceptions.StoreException;
import com.aironman.core.pojos.HistoricoUsuariosItems;
import com.aironman.core.pojos.Usuarios;

import java.util.List;

/**
 *
 * @author alonso
 */
public interface ServicioPersistenciaUsuarios {

    /**
     * inserta un usuario
     * @param p
     * @return 
     * @throws StoreException
     */
    String addUsuario(Usuarios p)                   throws StoreException;
    void updateUser(Usuarios p)                     throws StoreException;
    void deleteUser(Usuarios p)                     throws StoreException;
    /**
     * Este metodo sera invocado al ppio de la instanciacion del servicio de usuarios para asi tener cacheados todos los usuarios del sistema
     * @return
     * @throws StoreException
     */
    @SuppressWarnings(value = "unchecked")
    List<Usuarios> getAllUsuarios()                 throws StoreException;

    /**
     * decuelve un usuario dado el legajo
     * @param legajo
     * @return
     * @throws StoreException
     */
    Usuarios getUsuarioByLegajo(String legajo)      throws StoreException;
    Usuarios getUsuarioByEmail(String email)        throws StoreException;

    Long addHistoricoUsuarioItem(HistoricoUsuariosItems historicoUsuariosItems)throws StoreException;


}
