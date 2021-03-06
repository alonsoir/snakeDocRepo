package com.aironman.core.hibernate;

import com.aironman.core.exceptions.StoreException;
import com.aironman.core.pojos.ApuntesContables;
import com.aironman.core.utils.HibernateGenericDao;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
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
public class ApunteContableHibernateDao extends HibernateGenericDao<ApuntesContables, Long> implements ApunteContableDao
{
    private static final Log log = LogFactory.getLog(ApunteContableHibernateDao.class);
    @Autowired
    public ApunteContableHibernateDao(@Qualifier("sessionFactory")   SessionFactory sessionFactory) {
        super(sessionFactory);
        if (log.isDebugEnabled()){
            log.info("Constructor ApunteContableHibernateDao...");
        }
    }

    public Long addApunteContable(ApuntesContables value) throws StoreException
    {
        Long generatedKey=null;
        try
        {
            if (log.isDebugEnabled()){
                StringBuffer sb = new StringBuffer("INIT ApunteContableHibernateDao.addApunteContable. ")
                                                .append(" Apunte: ")
                                                .append(value.toString());
                log.info(sb);
            }
            //value.setClave(key);
            generatedKey= super.save(value);
            
            }catch(StoreException e)
            {
                if (log.isDebugEnabled()){
                    log.info("StoreException on ApunteContableHibernateDao.addAPunteContable",e);
                }
                throw e;
            }finally
            {
                if (log.isDebugEnabled()){
                    StringBuffer sb = new StringBuffer("End ApunteContableHibernateDao.addApunteContable. ")
                                                .append(" Apunte: ")
                                                .append(value.toString())
                                                .append("generatedKey: ")
                                                .append(generatedKey!=null?generatedKey:" KO");
                    log.info(sb);
                }
            }
            return generatedKey;
    }

    public ApuntesContables getApunteContableByClave(Long key) throws StoreException
    {
        if (log.isDebugEnabled()){
            StringBuffer sbInit = new StringBuffer("Init ApunteContableHibernateDao.getApunteContableByClaveCompleta. key: ")
                                                    .append(key.toString());
            log.info(sbInit);
        }
        ApuntesContables apunte = null;
        try{
            //ATENCION! estas comparando objetos complejos, igual lo mejor es pasarle el String contenido en la clave, el legajo
            apunte = (ApuntesContables) this.getSession().createCriteria(ApuntesContables.class).add(Restrictions.eq("id",
                    key)).uniqueResult();
        }catch(Exception e){
            if (log.isDebugEnabled()){
                StringBuffer sb = new StringBuffer("Exception capturada en ApunteContableHibernateDao.getApunteContableByClaveCompleta.");
                log.warn(sb,e);
            }
            throw new StoreException(e);
        }finally{
            if (log.isDebugEnabled()){
                StringBuffer sbEnd = new StringBuffer("End ApunteContableHibernateDao.getApunteContableByClaveCompleta. key: ")
                                                    .append(key.toString())
                                                    .append("apunte: ")
                                                    .append(apunte==null?"KO":apunte.toString());
                log.info(sbEnd);
            }
            
        }
        return apunte;
    }
    @SuppressWarnings("unchecked")
    public List<ApuntesContables> getAllApuntes() throws StoreException
    {
        if (log.isDebugEnabled()){
            log.info("INIT ApunteContableHibernateDao.getAllApuntes...");
        }
        List<ApuntesContables> lista = null;
        try{
            lista = getSession().createCriteria(ApuntesContables.class).list();
        }catch(Exception e){
            if (log.isDebugEnabled()){
                StringBuffer sb = new StringBuffer("Capturada excepcion generica en ApunteContableHibernateDao.getAllApuntes");
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

    /**
     * 
     * @param legajo
     * @return
     * @throws StoreException
     */
    public List<ApuntesContables> getApuntesContableByLegajo(String legajo) throws StoreException {
        if (log.isDebugEnabled()){
            StringBuffer sbInit = new StringBuffer("Init ApunteContableHibernateDao.getApunteContableByLegajo. legajo: ")
                                                    .append(legajo);
            log.info(sbInit);
        }
        List <ApuntesContables> listaApuntes = null;
        try{
            //ATENCION! estas comparando objetos complejos, igual lo mejor es pasarle el String contenido en la clave, el legajo
            listaApuntes =  this.getSession().createCriteria(ApuntesContables.class)
                                             .add(Restrictions.eq("legajo", legajo))
                                             .list();
        }catch(Exception e){
            if (log.isDebugEnabled()){
                StringBuffer sb = new StringBuffer("Exception capturada en ApunteContableHibernateDao.getApunteContableByLegajo.");
                log.warn(sb,e);
            }
            throw new StoreException(e);
        }finally{
            if (log.isDebugEnabled()){
                StringBuffer sbEnd = new StringBuffer("End ApunteContableHibernateDao.getApunteContableByLegajo. legajo: ")
                                                    .append(legajo)
                                                    .append("numApuntes: ")
                                                    .append(listaApuntes!=null ?listaApuntes.size():" NULO");
                log.info(sbEnd);
            }

        }
        return listaApuntes;
    }
}