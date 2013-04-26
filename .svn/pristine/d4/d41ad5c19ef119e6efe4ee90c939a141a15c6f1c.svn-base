package com.aironman.core.hibernate;

import com.aironman.core.exceptions.StoreException;
import com.aironman.core.pojos.Usuarios;
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
public class UsuarioHibernateDao extends HibernateGenericDao<Usuarios, String> implements UsuarioDao
{
            private static final Log log = LogFactory.getLog(UsuarioHibernateDao.class);

	    @Autowired
	    public UsuarioHibernateDao(@Qualifier("sessionFactory")   SessionFactory sessionFactory) {
                super(sessionFactory);
                if (log.isDebugEnabled()){
                    log.info("Constructor UsuarioHibernateDao...");
                }
            }

            /**
             * 
             * @return
             * @throws StoreException
             */
            @SuppressWarnings("unchecked")
	    public final List<Usuarios> findAll() throws StoreException
            {
                if (log.isDebugEnabled()){
                    log.info("INIT UsuarioHibernateDao.findAll...");
                }
                List<Usuarios> lista = null;
                try
                {
                    lista = getSession().createCriteria(Usuarios.class).list();
                }catch(Exception e)
                {
                    if (log.isDebugEnabled()){
                        StringBuilder sb = new StringBuilder();
                        sb.append("ATENCION exception en UsuarioHibernateDao.findAll.");
                        log.warn(sb,e);
                    }
                    throw new StoreException(e);
                }finally{
                    if (log.isDebugEnabled()){
                        StringBuilder sb = new StringBuilder();
                        sb.append("END UsuarioHibernateDao.findAll. numUsuarios: ").append(lista == null?"NO USERS.":lista.size());
                        log.info(sb);
                    }
                }
                return lista;
	    }

            public final Usuarios findByLegajo(String legajo) throws StoreException
            {
                if (log.isDebugEnabled()){
                    StringBuilder sbInit = new StringBuilder("INIT UsuarioHibernateDao.findByLegajo. legajo: ").append(legajo);
                    log.info(sbInit);
                }
                Usuarios usuario = null;
                try
                {
                    usuario = (Usuarios)getSession().createCriteria(Usuarios.class)
                                                    .add(Restrictions.eq("legajo", legajo))
                                                    .uniqueResult();
                }catch(Exception e)
                {
                    if (log.isDebugEnabled()){
                        log.error("UsuarioHibernateDao.findByLegajo",e);
                    }
                    throw new StoreException(e);
                }finally{
                    if (log.isDebugEnabled()){
                        StringBuilder sbEnd = new StringBuilder("END UsuarioHibernateDao.findByLegajo. encontrado? ");
                        sbEnd.append((usuario==null?"NO":"SI"));
                        log.info(sbEnd);
                    }
                }
                return usuario;
	    }

            public final Usuarios findByUserName(String userName) throws StoreException
            {
                if (log.isDebugEnabled()){
                    StringBuilder sbInit = new StringBuilder("INIT UsuarioHibernateDao.findByUserName. userName: ").append(userName);
                    log.info(sbInit);
                }
                Usuarios usuario = null;
                try{
                    usuario = (Usuarios)getSession().createCriteria(Usuarios.class).add(Restrictions.eq("nombre", userName)).uniqueResult();
                }catch(Exception e)
                {
                    if (log.isDebugEnabled()){
                        log.error("UsuarioHibernateDao.findByUserName",e);
                    }
                    throw new StoreException(e);
                }finally{
                    if (log.isDebugEnabled()){
                        StringBuilder sbEnd = new StringBuilder("END UsuarioHibernateDao.findByUserName. encontrado? ");
                        sbEnd.append((usuario==null?"NO":"SI"));
                    log.info(sbEnd);
                    }
                }
                return usuario;
	    }

           @Override
	   public final String save(Usuarios newInstance) throws StoreException
            {
                if (log.isDebugEnabled()){
                    StringBuffer sb = new StringBuffer("INIT UsuarioHibernateDao.save. newInstance: ").append(newInstance.toString());
                    log.info(sb);
                }
	    	if(exists(newInstance.getLegajo()))
                {
                    if (log.isDebugEnabled()){
                        log.warn("ATENCION! UsuarioHibernateDao.save. User already exist !!");
                    }
                }
                String generated=null;
                try{
                    generated= super.save(newInstance);
                }catch(Exception e)
                {
                    if (log.isDebugEnabled()){
                        log.warn("ATENCION! UsuarioHibernateDao.save. Exception tratando de salvar el nuevo usuario.",e);
                    }
                    throw new StoreException(e);
                }
	    	if (log.isDebugEnabled()){
                    StringBuffer sb = new StringBuffer("END UsuarioHibernateDao.save. newInstance: ")
                                                .append(newInstance.toString())
                                                .append(" generated: ").append(generated!=null?generated:" NULO");
                    log.info(sb);
                }
                return generated;
	    }
	   
	    public final boolean exists(String legajo)
            {
	        boolean result = (Integer ) getSession().createCriteria(Usuarios.class).setProjection(Projections.rowCount()).add(
	                        Restrictions.eq("legajo", legajo)).uniqueResult() == 1;
	        if (log.isDebugEnabled()){
                    log.info("UsuarioHibernateDao.exists. Result: "+result);
                }
		return result;
	    }

            public final Usuarios findByEmail(String email) throws StoreException
            {
                if (log.isDebugEnabled()){
                    StringBuilder sbInit = new StringBuilder("INIT UsuarioHibernateDao.findByEmail. email: ").append(email);
                    log.info(sbInit);
                }
                Usuarios usuario = null;
                try{
                    usuario = (Usuarios)getSession().createCriteria(Usuarios.class).add(Restrictions.eq("email", email)).uniqueResult();
                }catch(Exception e)
                {
                    if (log.isDebugEnabled()){
                        log.error("UsuarioHibernateDao.findByEmail",e);
                    }
                    throw new StoreException(e);
                }finally{
                    if (log.isDebugEnabled()){
                        StringBuilder sbEnd = new StringBuilder("END UsuarioHibernateDao.findByEmail. encontrado? ");
                        sbEnd.append((usuario==null?"NO":"SI"));
                        log.info(sbEnd);
                    }
                }
                return usuario;
            }
}