/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aironman.core.service;

import com.aironman.core.exceptions.StoreException;
import com.aironman.core.pojos.HistoricoUsuariosItems;
import com.aironman.core.pojos.Usuarios;
import com.aironman.core.utils.CarroDeLaCompra;

/**
 * Clase que gestiona los usuarios del sistema
 * @author alonso
 */
public interface ServicioUsuarios
{
    /**
     * 
     * @param p
     * @return
     * @throws StoreException
     */
    public boolean addUser(Usuarios p)                                                                      throws StoreException;
    public boolean updateUser(Usuarios p)                                                                   throws StoreException;
    public boolean deleteUser(Usuarios p)                                                                   throws StoreException;
    public Usuarios getUser(String email)                                                                   throws StoreException;
    public boolean checkPassword(String email, String pass)                                                 throws StoreException;
    public boolean checkConfirmPassword(String email, String passConfirm)                                   throws StoreException;
    public int getCountUsers();
    public Long addHistoricoUsuarioItem(HistoricoUsuariosItems historicoUsuariosItems)                      throws StoreException;
    public CarroDeLaCompra getCarroFromUsuario(String hash)                                                 throws StoreException;
    public CarroDeLaCompra crearCarro(String hash);
}
