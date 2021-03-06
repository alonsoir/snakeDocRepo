package com.aironman.core.hibernate;

import com.aironman.core.exceptions.StoreException;
import com.aironman.core.pojos.HistoricoUsuariosItems;
import com.aironman.core.utils.HibernateGenericDao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
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
public class HistoricoUsuariosItemsHibernateDao extends HibernateGenericDao<HistoricoUsuariosItems, Long> implements HistoricoUsuariosItemsDao
{
    private static final Log log = LogFactory.getLog(HistoricoUsuariosItemsHibernateDao.class);
    @Autowired
    public HistoricoUsuariosItemsHibernateDao(@Qualifier("sessionFactory")   SessionFactory sessionFactory) {
        super(sessionFactory);
        if (log.isDebugEnabled()){
            log.info("Constructor HistoricoItemsApuntesHibernateDao...");
        }
    }

    /**
     * Añade una tupla en la tabla intermedia HistoricoUsuariosItems
     * @param historicoUsuariosItems
     * @return HistoricoUsuariosItemsId la pk
     * @throws StoreException
     */
    public Long addHistoricoUsuarioItem(HistoricoUsuariosItems historicoUsuariosItems) throws StoreException
    {
        Long generatedKey=null;
        try
        {
            if (log.isDebugEnabled()){
                StringBuffer sb = new StringBuffer("INIT HistoricoUsuariosItemsHibernateDao.addHistoricoUsuarioItem. ")
                                                .append(" historicoUsuariosItems: ")
                                                .append(historicoUsuariosItems.toString());
                log.info(sb);
            }
            generatedKey= super.save(historicoUsuariosItems);
        }catch(StoreException e)
            {
                if (log.isDebugEnabled()){
                    log.info("StoreException on HistoricoItemsApuntesHibernateDao.addHistoricoUsuarioItem",e);
                }
                throw e;
        }finally{
                if (log.isDebugEnabled()){
                    StringBuffer sb = new StringBuffer("End HistoricoItemsApuntesHibernateDao.addHistoricoUsuarioItem. ")
                                                .append(" historicoItemApunte: ")
                                                .append(historicoUsuariosItems.toString())
                                                .append("generatedKey: ")
                                                .append(generatedKey!=null?generatedKey.toString():" KO");
                    log.info(sb);
                }
            }
            return generatedKey;
    }
}