/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aironman.core.service;

import com.aironman.core.exceptions.StoreException;
import com.aironman.core.hibernate.ApunteContableHibernateDao;
import com.aironman.core.hibernate.HistoricoItemsApuntesDao;
import com.aironman.core.pojos.ApuntesContables;
import com.aironman.core.pojos.HistoricoItemsApuntes;
import com.aironman.core.pojos.HistoricoItemsApuntesId;
import java.util.List;
import org.springframework.stereotype.Service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 *
 * @author Alonso Isidoro Roman
 */
@Service("servicioPersistenciaApuntesContables")
public class ServicioPersistenciaApuntesContablesImpl implements ServicioPersistenciaApuntesContables
{
    @Autowired
    //@Qualifier("itemHibernateDao")
    private ApunteContableHibernateDao      apunteContableHibernateDao;

    @Autowired
    private HistoricoItemsApuntesDao        historicoItemsApuntesDao;
    
    private static final Log log = LogFactory.getLog(ServicioPersistenciaApuntesContablesImpl.class);

    
    public ServicioPersistenciaApuntesContablesImpl()
    {
        if (log.isDebugEnabled()){
            log.info("Constructor SIN tipo ServicioPersistenciaApuntesContablesImpl...");
        }
    }

    public Long addApunteContable(ApuntesContables value) throws StoreException
    {
        if (log.isDebugEnabled()){
            StringBuffer sb = new StringBuffer("Init ServicioPersistenciaApuntesContablesImpl.addApunteContable")
                                                .append(" value: ").append(value.toString());
            log.info(sb);

        }
        Long generatedKey = this.apunteContableHibernateDao.addApunteContable(value);
        if (log.isDebugEnabled()){
            StringBuffer sbEnd = new StringBuffer("End ServicioPersistenciaApuntesContablesImpl.addApunteContable")
                                                .append(" value: ").append(value.toString())
                                                .append(generatedKey!=null?generatedKey:" KO");
            log.info(sbEnd);

        }
        return generatedKey;
    }

    public ApuntesContables getApunteContableByClave(Long key) throws StoreException
    {
        if (log.isDebugEnabled()){
            StringBuffer sb = new StringBuffer("Init ServicioPersistenciaApuntesContablesImpl.getApunteContableByClaveCompleta")
                                                .append(" key: ").append(key.toString())
                                                ;
            log.info(sb);

        }
        ApuntesContables apunte=null;
        apunte = this.apunteContableHibernateDao.getApunteContableByClave(key);
        if (log.isDebugEnabled()){
            StringBuffer sbEnd = new StringBuffer("End ServicioPersistenciaApuntesContablesImpl.getApunteContableByClaveCompleta")
                                                .append(" key: ").append(key.toString())
                                                .append(apunte!=null ? apunte.toString():null);
            log.info(sbEnd);

        }
        return apunte;
    }

    public List<ApuntesContables> getAllApuntes() throws StoreException {
        if (log.isDebugEnabled()){
            StringBuffer sb = new StringBuffer("Init ServicioPersistenciaApuntesContablesImpl.getAllApuntes");
            log.info(sb);
        }
        List<ApuntesContables> lista;
        lista = this.apunteContableHibernateDao.getAllApuntes();
        if (log.isDebugEnabled()){
            StringBuffer sbEnd = new StringBuffer("End ServicioPersistenciaApuntesContablesImpl.getApunteContableByClaveCompleta")
                                                .append(" numApuntes: ")
                                                .append(lista!=null ? lista.size():null);
            log.info(sbEnd);

        }
        return lista;
    }

    public List<ApuntesContables> getApuntesByLegajo(String legajo) throws StoreException {

        if (log.isDebugEnabled()){
            StringBuffer sb = new StringBuffer("Init ServicioPersistenciaApuntesContablesImpl.getApuntesByLegajo")
                                                .append(" legajo: ").append(legajo)
                                                ;
            log.info(sb);

        }
        List<ApuntesContables> lista = this.apunteContableHibernateDao.getApuntesContableByLegajo(legajo);
        if (log.isDebugEnabled()){
            StringBuffer sbEnd = new StringBuffer("End ServicioPersistenciaApuntesContablesImpl.getApuntesByLegajo")
                                                .append(" legajo: ").append(legajo)
                                                .append(" numApuntes: ")
                                                .append(lista!=null ? lista.size():" NULO");
            log.info(sbEnd);

        }
        return lista;
    }

    public HistoricoItemsApuntesId addHistoricoItemApunte(HistoricoItemsApuntes historicoItemApunte) throws StoreException
    {
        if (log.isDebugEnabled())
        {
            StringBuffer sb = new StringBuffer("Init ServicioPersistenciaApuntesContables.addHistoricoItemApunte. ");
            sb.append(" historicoItemApunte: ").append(historicoItemApunte.toString());
            log.info(sb);
        }
        HistoricoItemsApuntesId generatedKey = this.historicoItemsApuntesDao.addHistoricoItemApunte(historicoItemApunte);
        if (log.isDebugEnabled())
        {
            StringBuffer sbEnd = new StringBuffer("End ServicioPersistenciaApuntesContables.addHistoricoItemApunte. ");
            sbEnd.append(" generatedKey: ").append(generatedKey!=null ? generatedKey.toString() : " KO");
            log.info(sbEnd);
        }
        return generatedKey;
    }

    /**
     * @return the apunteContableHibernateDao
     */
    public ApunteContableHibernateDao getApunteContableHibernateDao() {
        return apunteContableHibernateDao;
    }

    /**
     * @param apunteContableHibernateDao the apunteContableHibernateDao to set
     */
    public void setApunteContableHibernateDao(ApunteContableHibernateDao apunteContableHibernateDao) {
        this.apunteContableHibernateDao = apunteContableHibernateDao;
    }

    /**
     * @return the historicoItemsApuntesDao
     */
    public HistoricoItemsApuntesDao getHistoricoItemsApuntesDao()
    {
        return historicoItemsApuntesDao;
    }

    /**
     * @param historicoItemsApuntesDao the historicoItemsApuntesDao to set
     */
    public void setHistoricoItemsApuntesDao(HistoricoItemsApuntesDao historicoItemsApuntesDao)
    {
        this.historicoItemsApuntesDao = historicoItemsApuntesDao;
    }
}
