package com.aironman.core.utils;

import com.aironman.core.exceptions.StoreException;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public abstract class HibernateGenericDao<T, PK extends Serializable> extends
		HibernateDaoSupport implements GenericDao<T, PK> {
        
        private Log log = LogFactory.getLog(HibernateGenericDao.class);

	@Autowired
	public HibernateGenericDao(@Qualifier("sessionFactory") SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
                if (log.isDebugEnabled()){
                    log.info("CONSTRUCTOR HibernateGenericDao...");
                }
	}
	
	@SuppressWarnings("unchecked")
	public final Class getType() {
		ParameterizedType parameterizedType = (ParameterizedType) getClass()
				.getGenericSuperclass();
		return (Class) parameterizedType.getActualTypeArguments()[0];
	}

	
	/**
         * @param o
         * @throws StoreException 
         * @see com.lb.utils.orm.hibernate.GenericDao#update(java.lang.Object)
	 */
	public final void update(T o) throws StoreException
        {
            if (log.isDebugEnabled()){
                log.info("INIT HibernateGenericDao.update...");
            }
            try{
		getHibernateTemplate().update(o);
            }catch(Exception e){
                if (log.isDebugEnabled()){
                    log.warn("CAPTURADA EXCEPCION",e);
                }
                throw new StoreException(e);
            }finally{
                if (log.isDebugEnabled()){
                    log.info("END HibernateGenericDao.update...");
                }
            }
	}

	/**
	 * {@inheritDoc}
         *
         * @param o
         * @throws StoreException 
         */
	public final void delete(T o) throws StoreException
        {
            if (log.isDebugEnabled()){
                log.info("INIT HibernateGenericDao.delete...");
            }
            try{
                getHibernateTemplate().delete(o);
            }catch(Exception e){
                if (log.isDebugEnabled()){
                    log.warn("CAPTURADA EXCEPCION",e);
                }
                throw new StoreException(e);
            }finally{
                if (log.isDebugEnabled()){
                    log.info("END HibernateGenericDao.delete...");
                }
            }
	}

	/**
	 * {@inheritDoc}
         *
         * @throws StoreException 
         */
	@SuppressWarnings("unchecked")
	public final T get(PK id) throws StoreException 
        {
            if (log.isDebugEnabled()){
                log.info("INIT HibernateGenericDao.get...");
            }
            Object result = null;
            try{
		result = getHibernateTemplate().get(getType(), id);
            }catch(Exception e){
                if (log.isDebugEnabled()){
                    log.warn("CAPTURADA EXCEPCION",e);
                }
                throw new StoreException(e);
            }finally{
                log.info("END HibernateGenericDao.get...");
            }
            return result == null ? null : (T) result;
	}

	/**
	 * {@inheritDoc}
         *
         * @throws StoreException 
         */
	@SuppressWarnings("unchecked")
	public final T load(PK id)
        {
            return (T) getHibernateTemplate().load(getType(), id);
	}

	/**
	 * {@inheritDoc}
         *
         * @param newInstance
         * @throws StoreException 
         */
	@SuppressWarnings("unchecked")
	public PK save(T newInstance) throws StoreException
        {
                if (log.isDebugEnabled()){
                    log.info("INIT HibernateGenericDao.save...");
                }
                try{
                    return (PK) getHibernateTemplate().save(newInstance);
                }catch(Exception e)
                {
                    if (log.isDebugEnabled()){
                        log.warn("CAPTURADA EXCEPCION",e);
                    }
                    throw new StoreException(e);
                }finally{
                    if (log.isDebugEnabled()){
                        log.info("END HibernateGenericDao.save...");
                    }
                }
	}

	/**
	 * {@inheritDoc}
         *
         * @param transientObject
         * @throws StoreException 
         */
	public final void saveOrUpdate(T transientObject) throws StoreException 
        {
            if (log.isDebugEnabled()){
                log.info("INIT HibernateGenericDao.saveOrUpdate...");
            }
            try{
		getHibernateTemplate().saveOrUpdate(transientObject);
                
            }catch(Exception e){
                if (log.isDebugEnabled()){
                    log.warn("CAPTURADA EXCEPCION",e);
                }
                throw new StoreException(e);
            }finally{
                if (log.isDebugEnabled()){
                    log.info("END HibernateGenericDao.saveOrUpdate...");
                }
            }
	}

	/**
	 * {@inheritDoc}
         *
         * @param query 
         * @param pageNumber
         * @param nbPerPage
         * @return
         * @throws StoreException
         */
	@SuppressWarnings("unchecked")
	public final List findByPaginatedQuery(String query, int pageNumber, int nbPerPage) throws StoreException
        {
                if (log.isDebugEnabled()){
                    log.info("INIT HibernateGenericDao.findByPaginatedQuery...");
                }
                List lista=null;
                try{
                    lista= findByPaginatedQuery(query, pageNumber, nbPerPage,(Object[]) null);
                }catch(Exception e){
                    if (log.isDebugEnabled()){
                        log.warn("CAPTURADA EXCEPCION",e);
                    }
                    throw new StoreException(e);
                }finally{
                    if (log.isDebugEnabled()){
                        StringBuffer sb = new StringBuffer("END HibernateGenericDao.findByPaginatedQuery. numItems: ")
                                .append(lista!=null? lista.size():" NULO");
                        log.info(sb);
                    }
                }
                return lista;
	}

	/**
	 * {@inheritDoc}
         *
         * @param query
         * @param pageNumber 
         * @param nbPerPage 
         * @param value
         * @return
         * @throws StoreException
         */
	@SuppressWarnings("unchecked")
	public final List findByPaginatedQuery(String query, int pageNumber,
			int nbPerPage, Object value) throws StoreException
        {
                if (log.isDebugEnabled()){
                    log.info("INIT HibernateGenericDao.findByPaginatedQuery...");
                }
                List lista = null;
                try{
                    lista = findByPaginatedQuery(query, pageNumber, nbPerPage,new Object[] { value });
                }catch(Exception e){
                    log.warn("CAPTURADA EXCEPCION",e);
                    throw new StoreException(e);
                }finally{
                    if (log.isDebugEnabled()){
                        StringBuffer sb = new StringBuffer("END HibernateGenericDao.findByPaginatedQuery...")
                                                    .append(lista!=null?lista.size():" NULO.");
                        log.info(sb);
                    }
                }
                return lista;
	}

	/**
	 * {@inheritDoc}
         *
         * @param query 
         * @param pageNumber
         * @param nbPerPage
         * @param values
         * @return
         * @throws StoreException
         */
	@SuppressWarnings("unchecked")
	public final List findByPaginatedQuery(final String query, final int pageNumber,
			final int nbPerPage, final Object[] values) throws StoreException
        {
                if (log.isDebugEnabled()){
                    log.info("INIT HibernateGenericDao.findByPaginatedQuery...");
                }
                List lista=null;
                try{
                    lista = getHibernateTemplate().executeFind(new HibernateCallback() {

                public Object doInHibernate(Session session) {
                    Query queryObject = session.createQuery(query);
                    if (values != null) {
                        for (int i = 0; i < values.length; i++) {
                            queryObject.setParameter(i, values[i]);
                        }
                    }
                    queryObject.setFirstResult(pageNumber * nbPerPage);
                    queryObject.setMaxResults(nbPerPage);
                    return queryObject.list();
                }
            });
                }catch(Exception e){
                    log.warn("CAPTURADA EXCEPCION",e);
                    throw new StoreException(e);
                }finally{
                    if (log.isDebugEnabled()){
                        StringBuffer sb = new StringBuffer("END HibernateGenericDao.findByPaginatedQuery. ")
                                                    .append(lista!=null?lista.size():" NULO");
                        log.info(sb);
                    }
                }
                return lista;
	}

	/**
	 * {@inheritDoc}
         *
         * @throws StoreException 
         */
	@SuppressWarnings("unchecked")
	public final void delete(PK persistentObjectPK) throws StoreException
        {
                if (log.isDebugEnabled()){
                    log.info("INIT HibernateGenericDao.delete. persistentObjectPK: " + persistentObjectPK.toString());
                }
                try{
                    T o = (T) getHibernateTemplate().load(getType(), persistentObjectPK);
                    getHibernateTemplate().delete(o);
                }catch(Exception e) {
                    if (log.isDebugEnabled()){
                        log.warn("CAPTURADA EXCEPCION",e);
                    }
                    throw new StoreException(e);
                }finally{
                    if (log.isDebugEnabled()){
                        log.info("END HibernateGenericDao.delete...");
                    }
                }
	}
}