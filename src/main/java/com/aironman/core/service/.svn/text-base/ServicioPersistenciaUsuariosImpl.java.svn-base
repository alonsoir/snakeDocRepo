/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aironman.core.service;

import com.aironman.core.exceptions.StoreException;
import com.aironman.core.hibernate.HistoricoUsuariosItemsDao;
import com.aironman.core.hibernate.UsuarioHibernateDao;
import com.aironman.core.pojos.HistoricoUsuariosItems;
import com.aironman.core.pojos.Usuarios;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author alonso
 */import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/***
 *
 * @author Alonso Isidoro Roman
 */
@Service("servicioPersistenciaUsuarios")
public class ServicioPersistenciaUsuariosImpl implements ServicioPersistenciaUsuarios {

    @Autowired
    //@Qualifier("usuarioHibernateDao")
    private UsuarioHibernateDao usuarioHibernateDao;

    @Autowired
    private HistoricoUsuariosItemsDao historicoUsuariosItemsDao;
    
    private Log log = LogFactory.getLog(ServicioPersistenciaUsuariosImpl.class);

    public ServicioPersistenciaUsuariosImpl()
    {
        if (log.isDebugEnabled()){
            log.info("Constructor ServicioPersistenciaUsuariosImpl...");
        }
    }

    /***
     * Este metodo sera invocado al ppio de la instanciacion del servicio de usuarios para asi tener cacheados todos los usuarios del sistema
     * @return
     * @throws StoreException
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true,noRollbackFor=StoreException.class)
    public List<Usuarios> getAllUsuarios() throws StoreException
    {
        if (log.isDebugEnabled()){
            log.info("INIT ServicioPersistenciaUsuariosImpl.getAllUsuarios...");
        }
        List<Usuarios> lista = this.getUsuarioHibernateDao().findAll();
        if (log.isDebugEnabled()){
            StringBuilder sbEnd = new  StringBuilder("END ServicioPersistenciaUsuariosImpl.getAllUsuarios. ")
                                    .append(lista !=null ? lista.size():0);
            log.info(sbEnd);
        }
        return lista;
    }

    /***
     * decuelve un usuario dado el legajo
     * @param legajo
     * @return
     * @throws StoreException
     */
    @Transactional(readOnly=true,noRollbackFor=StoreException.class)
    public Usuarios getUsuarioByLegajo(String legajo) throws StoreException
    {
        if (log.isDebugEnabled()){
            StringBuffer sb = new StringBuffer("INIT ServicioPersistenciaUsuariosImpl.getUsuarioByLegajo. legajo: ")
                                        .append(legajo);
            log.info(sb);
        }
        Usuarios usuario = this.getUsuarioHibernateDao().findByLegajo(legajo);
        if (log.isDebugEnabled()){

            StringBuilder sbEnd = new StringBuilder("END ServicioPersistenciaUsuariosImpl.getUsuarioByLegajo...")
                                                .append(usuario !=null ? usuario.toString():"null");
            log.info("END ServicioPersistenciaUsuariosImpl.getUsuarioByLegajo...");
        }
        return usuario;
    }

    /***
     * inserta un usuario
     * @param usuario 
     * @throws StoreException
     */
    @Transactional(rollbackFor=StoreException.class)
    public String addUsuario(Usuarios usuario) throws StoreException
    {
        if (log.isDebugEnabled()){
            StringBuffer sb = new StringBuffer("INIT ServicioPersistenciaUsuariosImpl.addUsuario. usuario: ").append(usuario.toString());
            log.info(sb);
        }
        String generatedPK = this.getUsuarioHibernateDao().save(usuario);
        if (log.isDebugEnabled()){
            log.info("END ServicioPersistenciaUsuariosImpl.addUsuario. generatedPK: " + generatedPK);
        }
        return generatedPK;
    }
    @Transactional(rollbackFor=StoreException.class)
    public void updateUser(Usuarios usuario) throws StoreException {
        log.info("INIT ServicioPersistenciaUsuariosImpl.updateUser...");
        this.getUsuarioHibernateDao().saveOrUpdate(usuario);
        log.info("END ServicioPersistenciaUsuariosImpl.updateUser...");

    }
    /****
     * 
     * @param email
     * @return
     * @throws StoreException
     */
    @Transactional(readOnly=true,noRollbackFor=StoreException.class)
    public Usuarios getUsuarioByEmail(String email) throws StoreException {
        log.info("INIT ServicioPersistenciaUsuariosImpl.getUsuarioByEmail...");
        Usuarios usuario = this.getUsuarioHibernateDao().findByEmail(email);
        StringBuilder sbEnd = new StringBuilder("END ServicioPersistenciaUsuariosImpl.getUsuarioByEmail...")
                                                .append(usuario !=null ? usuario.toString():"null");
        log.info("END ServicioPersistenciaUsuariosImpl.getUsuarioByEmail...");

        return usuario;
    }
    @Transactional(rollbackFor=StoreException.class)
    public void deleteUser(Usuarios p) throws StoreException
    {
        log.info("Init ServicioPersistenciaUsuariosImpl.deleteUser...");
        this.usuarioHibernateDao.delete(p);
        log.info("End ServicioPersistenciaUsuariosImpl.deleteUser...");
    }

    @Transactional(rollbackFor=StoreException.class)
    public Long addHistoricoUsuarioItem(HistoricoUsuariosItems historicoUsuariosItems) throws StoreException
    {
        if (log.isDebugEnabled())
        {
            StringBuffer sbInit = new StringBuffer("Init ServicioPersistenciaUsuariosImpl.addHistoricoUsuarioItem. ");
            sbInit.append(" historicoUsuariosItems: ").append(historicoUsuariosItems.toString());
            log.debug(sbInit);
        }
        Long generatedKey = this.historicoUsuariosItemsDao.addHistoricoUsuarioItem(historicoUsuariosItems);
        if (log.isDebugEnabled())
        {
            StringBuffer sbEnd = new StringBuffer("End ServicioPersistenciaUsuariosImpl.addHistoricoUsuarioItem. ");
            sbEnd.append(" generatedKey: ").append(generatedKey!=null?generatedKey.toString() : " KO");
            log.debug(sbEnd);
        }
        return generatedKey;
    }

    /**
     * @return the usuarioHibernateDao
     */
    public UsuarioHibernateDao getUsuarioHibernateDao() {
        return usuarioHibernateDao;
    }

    /**
     * @param usuarioHibernateDao the usuarioHibernateDao to set
     */
    public void setUsuarioHibernateDao(UsuarioHibernateDao usuarioHibernateDao) {
        this.usuarioHibernateDao = usuarioHibernateDao;
    }

    /**
     * @return the historicoUsuariosItemsDao
     */
    public HistoricoUsuariosItemsDao getHistoricoUsuariosItemsDao()
    {
        return historicoUsuariosItemsDao;
    }

    /**
     * @param historicoUsuariosItemsDao the historicoUsuariosItemsDao to set
     */
    public void setHistoricoUsuariosItemsDao(HistoricoUsuariosItemsDao historicoUsuariosItemsDao)
    {
        this.historicoUsuariosItemsDao = historicoUsuariosItemsDao;
    }


    
}
