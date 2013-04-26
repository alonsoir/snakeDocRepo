package com.aironman.core.hibernate;

import com.aironman.core.exceptions.StoreException;
import com.aironman.core.pojos.Items;
import com.aironman.core.utils.HibernateGenericDao;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;


/**
 * @author Alonso Isidoro Roman
 * 
 * la anotacion Repository indica que esta clase va a ser un Data access object, vamos un dao de toda la vida
 * la anotacion qualifier indica que el objeto dado por ese nombre se va a inyectar mediante autowiring, vamos que spring lo va a hacer por
 * nosotros...
 *
 * Debo extender asi de HibernateGenericDao? 
 */
@Repository
public class ItemHibernateDao extends HibernateGenericDao<Items, String> implements ItemDao
{
            private Log log = LogFactory.getLog(ItemHibernateDao.class);

	    @Autowired
	    public ItemHibernateDao(@Qualifier("sessionFactory")   SessionFactory sessionFactory) {
	        super(sessionFactory);
	    }

	    /**
	     * @see com.lb.jwitter.hibernate.UsuarioDao#findAll()
	     */
	    @SuppressWarnings("unchecked")
            public final List<Items> findAll() throws StoreException
            {
                if (log.isDebugEnabled()){
                    log.info("INIT ItemHibernateDao.findAll...");
                }
                List<Items> lista = null;
                try{
                    lista = getSession().createCriteria(Items.class).list();
                }catch(Exception e)
                {
                    if (log.isDebugEnabled()){
                        StringBuilder sb = new StringBuilder();
                        sb.append("ATENCION EXCEPCION en ItemHibernateDao.findAll.");
                        log.warn(sb,e);
                     }
                    throw new StoreException(e);
                }finally{
                    if (log.isDebugEnabled()){
                        StringBuilder sb = new StringBuilder();
                        sb.append("END ItemHibernateDao.findAll. numItems: ").append(lista == null?"LISTA NULA.":lista.size());
                        log.info(sb);
                    }
                }
                return lista;
	    }

	    /**
             * @param isbn 
             * @return
             * @throws StoreException
             * @see com.lb.jwitter.hibernate.UsuarioDao#findByUserName(java.lang.String)
	     */
	    public final Items findByIsbn(String isbn) throws StoreException
            {
                if (log.isDebugEnabled()){
                    StringBuilder sbInit = new StringBuilder("INIT ItemHibernateDao.findByIsbn. isbn: ").append(isbn);
                    log.info(sbInit);
                }
                Items item = null;
                try{
                    item = (Items)getSession().createCriteria(Items.class).add(Restrictions.eq("isbn", isbn)).uniqueResult();
                }catch(Exception e)
                {
                    if (log.isDebugEnabled()){
                        log.error("ATENCION!! UsuarioHibernateDao.findByUserName",e);
                    }
                    throw new StoreException(e);
                }finally{
                    if (log.isDebugEnabled()){
                        StringBuilder sbEnd = new StringBuilder("END ItemHibernateDao.findByIsbn. encontrado? ");
                        sbEnd.append((item==null?"NO":"SI"));
                        log.info(sbEnd);
                    }
                }
                return item;
	    }

	   /**
            * @param newInstance
            * @throws StoreException
            * @see com.lb.utils.orm.hibernate.HibernateGenericDao#save(java.lang.Object)
	     */
            @Override
    	    public final String save(Items newInstance) throws StoreException
            {
                if (log.isDebugEnabled()){
                    log.info("INIT ItemHibernateDao.save...");
                }
                String pk = null;
	    	if(exists(newInstance.getIsbn()))
                {
                    if (log.isDebugEnabled()){
                        log.error("ItemHibernateDao.save. ATENCION!! Item already exist !!");
                    }
				
                }else{
                    pk = super.save(newInstance);
                    if (log.isDebugEnabled()){
                        StringBuffer sbEnd =  new StringBuffer("End ItemHibernateDao.save. generatedPK: ")
                                            .append(pk!=null?pk:" NULO.");
                        log.info(sbEnd);
                    }
                }
	    	return pk;
	    }
	   
	    /**
             * @param isbn 
             * @see com.lb.jwitter.hibernate.UsuarioDao#exists(java.lang.String)
	     */
	    public final boolean exists(String isbn)
            {
	        boolean result = (Integer ) getSession().createCriteria(Items.class).setProjection(Projections.rowCount()).add(
	                        Restrictions.eq("isbn", isbn)).uniqueResult() == 1;
	        if (log.isDebugEnabled()){

                    log.info("method exists on ItemHibernateDao. Result: "+result);
                }
		return result;
	    }
}