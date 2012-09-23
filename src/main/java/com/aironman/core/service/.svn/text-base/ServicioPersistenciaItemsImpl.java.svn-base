/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aironman.core.service;

import com.aironman.core.exceptions.StoreException;
import com.aironman.core.hibernate.ItemHibernateDao;
import com.aironman.core.pojos.Items;
import java.util.List;
import org.springframework.stereotype.Service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author Alonso Isidoro Roman
 */
@Service("servicioPersistenciaItems")
public class ServicioPersistenciaItemsImpl implements ServicioPersistenciaItems
{
    @Autowired
    //@Qualifier("itemHibernateDao")
    private ItemHibernateDao itemHibernateDao;

    private Log log = LogFactory.getLog(ServicioPersistenciaItemsImpl.class);

    
    public ServicioPersistenciaItemsImpl()
    {
        if (log.isDebugEnabled()){
            log.info("Constructor SIN tipo ServicioPersistenciaItemsImpl...");
        }
    }

    /***
     * RETORNA TODOS LOS ITEMS DEL SISTEMA PERSISTENTE
     * @return List<com.dosideas.cxf.hibernate.Item> uf, debo devolver los pojos hibernate?
     * @throws StoreException
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true,noRollbackFor=StoreException.class)
    public List<com.aironman.core.pojos.Items> getAllItems() throws StoreException
    {
        return this.getItemHibernateDao().findAll();
    }

    /***
     * AÑADO UN ITEM AL SISTEMA PERSISTENTE
     * @param _item
     * @return true OK, false KO
     */
    @Transactional(rollbackFor=StoreException.class)
    public void addItemToSystem(com.aironman.core.pojos.Items _item) throws StoreException
    {
        this.getItemHibernateDao().save(_item);
    }

    /***
     * metodo para ir a bbdd y recoger el item
     * @param isbn
     * @return el item buscado o nulo en caso contrario
     * @throws StoreException
     */
    @Transactional(readOnly=true,noRollbackFor=StoreException.class)
    public Items getItem(String isbn) throws StoreException
    {
        return this.getItemHibernateDao().findByIsbn(isbn);
    }
    /***
     *
     * @param _item
     * @throws StoreException
     */
    @Transactional(rollbackFor=StoreException.class)
    public void updateItem(Items _item) throws StoreException {

        this.getItemHibernateDao().saveOrUpdate(_item);
    }
    /***
     * 
     * @param _item
     * @throws StoreException
     */
    @Transactional(rollbackFor=StoreException.class)
    public void deleteItemFromSystem(Items _item) throws StoreException {
        this.getItemHibernateDao().delete(_item);
    }

    /**
     * @return the itemHibernateDao
     */
    public ItemHibernateDao getItemHibernateDao() {
        return itemHibernateDao;
    }

    /**
     * @param itemHibernateDao the itemHibernateDao to set
     */
    public void setItemHibernateDao(ItemHibernateDao itemHibernateDao) {
        this.itemHibernateDao = itemHibernateDao;
    }

    
}
