package com.aironman.core.utils;

import com.aironman.core.exceptions.StoreException;
import java.io.Serializable;

/**
 *
 * @author lbouin
 */
public interface GenericDao<EntityClass, PKClass extends Serializable> {
   
    /**
     * Save an instance
     * @param transientInstance The instance to save
     * @return The PK of the saved instance
     */
    PKClass save(EntityClass transientInstance) throws StoreException;

    /**
     * Update the given instance 
     * @param detachedInstance The instance to update
     */
    void update(EntityClass detachedInstance) throws StoreException;

    /**
     * Save or Update the given instance 
     * @param object The instance to save or update
     */
    void saveOrUpdate(EntityClass object) throws StoreException;

    /**
     * Get the entity with the given id
     * @param id the id of the instance to get
     * @return The existing entity
     */
    EntityClass get(PKClass id) throws StoreException;

    /**
     * Load the entity with the given id
     * @param id the id of the instance to load
     * @return The existing entity
     */
    EntityClass load(PKClass id) throws StoreException;

    /**
     * Delete the given instance from the DB
     * @param persistentInstance The instance to delete
     */
    void delete(EntityClass persistentInstance) throws StoreException;

    /**
     * Delete the instance with the given id from the DB
     * @param persistentObjectPK The PK of the instance to delete
     */
    void delete(PKClass persistentObjectPK) throws StoreException;
}