/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aironman.core.service;

import com.aironman.core.exceptions.StoreException;
import com.aironman.core.pojos.ApuntesContables;
import com.aironman.core.pojos.HistoricoItemsApuntes;
import com.aironman.core.pojos.HistoricoItemsApuntesId;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * TODO this is bullshit, esto hasta que no tengas implementado la persistencia, no vale de casi nada
 * @author Alonso Isidoro Roman
 */
@Service("servicioApuntes")
public class ServicioApuntesContablesImpl implements ServicioApuntesContables{

    private Log log = LogFactory.getLog(ServicioApuntesContablesImpl.class);
    private static ConcurrentHashMap <Long,ApuntesContables> mapaApuntesContables
                      = new ConcurrentHashMap <Long,ApuntesContables> ();
    @Autowired
    private ServicioPersistenciaApuntesContables servicioPersistenciaApuntesContables;

    //TODO al final tendre que persistir los apuntes contables, por ahora los mantendre en memoria...

    public ServicioApuntesContablesImpl()
    {
        if (log.isDebugEnabled()){
            log.info("Constructor ServicioApuntesContablesImpl...");
        }
    }

    @PostConstruct
    public void init() throws StoreException
    {
        if (log.isDebugEnabled()){
            log.info("Init method on ServicioApuntesContablesImpl class...");
        }
        List<ApuntesContables> lista = null;
        try
        {
            lista = this.servicioPersistenciaApuntesContables.getAllApuntes();
            for (ApuntesContables _apunte:lista)
            {
                Object putIfAbsent = ServicioApuntesContablesImpl.mapaApuntesContables.putIfAbsent(_apunte.getId(), _apunte);
                //si esto devuelve nulo significa que la insercion ha ido bien.
                if (putIfAbsent == null && log.isDebugEnabled())
                {
                    log.info("Insertado apunte contable en el hashMap...");
                }
            }
        }catch(Exception e)
        {
            log.warn("ATENCION!! EXCEPCION GRAVE AL RELLENAR EL HASHMAP DE LOS APUNTES CONTABLES.",e);
            throw new StoreException(e);
        }finally
        {
            StringBuffer sb = new StringBuffer("hashMap INITIALIZED. numApuntes: ")
                                                    .append(lista!=null?lista.size():" NULO.");
            log.info(sb);
            
        }
    }
    
    public Long addApunteContable(ApuntesContables value) throws StoreException
    {
        if (log.isDebugEnabled()){
                StringBuilder sbInit = new StringBuilder("INIT ServicioApuntesContablesImpl.addApunteContable. ")
                                                    .append(" value: ")
                                                    .append(value.toString());
                log.info(sbInit);
        }
        Long generatedKey = null;
        ApuntesContables putIfAbsent = null;
        try {
            generatedKey = servicioPersistenciaApuntesContables.addApunteContable(value);
            putIfAbsent = ServicioApuntesContablesImpl.mapaApuntesContables.putIfAbsent(generatedKey, value);
        }
        catch (Exception ex)
        {
            if (log.isDebugEnabled())
            {
                log.warn("EXCEPTION on ServicioApuntesContablesImpl.addApunteContable",ex);
            }
            throw new StoreException(ex);
        }finally{
            if (log.isDebugEnabled())
            {
                StringBuilder sbEnd = new StringBuilder("ServicioApuntesContablesImpl.addApunteContable. generatedKey: ")
                                        .append(generatedKey)
                                        .append(" putIfAbsent: ")
                                        .append(putIfAbsent);
                log.info(sbEnd);
            }
        }
        
        return generatedKey;
    }

    public List<ApuntesContables> getAllApuntes()
    {
        if (log.isDebugEnabled()){
            log.info("Init ServicioApuntesContablesImpl. METHOD: getAllApuntes. ");
        }
        List<ApuntesContables> lista = Collections.list(ServicioApuntesContablesImpl.mapaApuntesContables.elements());
        if (log.isDebugEnabled()){
            StringBuffer sb = new StringBuffer("End ServicioApuntesContablesImpl. METHOD: getAllApuntes. numApuntes: ")
                                            .append(lista!=null?lista.size():" NULO");
            log.info(sb);
        }
        return lista;
    }

    public ApuntesContables getApunteContableByClave(Long key) throws StoreException {

        ApuntesContables apunte = ServicioApuntesContablesImpl.mapaApuntesContables.get(key);
        if (apunte==null){
            if (log.isDebugEnabled())
            {
                log.info("El apunte no se encuentra en la cache. A ver si esta en BBDD...");
            }
            apunte = this.servicioPersistenciaApuntesContables.getApunteContableByClave(key);
            if (apunte==null){
                if (log.isDebugEnabled())
                {
                    log.info("El apunte tampoco esta en la BBDD. Retornando nulo");
                }
            }else{
                //lo introduzco en la cache
                ServicioApuntesContablesImpl.mapaApuntesContables.putIfAbsent(key, apunte);
                if (log.isDebugEnabled())
                {
                    log.info("Apunte introducido en la cache!");
                }
            }
        }
        return apunte;
    }

    public List<ApuntesContables> getApuntesByLegajo(String legajo) throws StoreException {
        if (log.isDebugEnabled())
        {
            StringBuffer sbInit = new StringBuffer("Init ServicioApuntesContablesImpl.getApuntesByLegajo")
                                    .append(" legajo").append(legajo);
            log.info(sbInit);

        }
        List<ApuntesContables> lista = null;
        try
        {
            lista = this.servicioPersistenciaApuntesContables.getApuntesByLegajo(legajo);
        }catch(Exception e)
        {
            if (log.isDebugEnabled())
            {
                StringBuffer sbEnd= new StringBuffer("Exception capturada en ServicioApuntesContablesImpl.getApuntesByLegajo");
                log.info(sbEnd,e);
            }
            throw new StoreException(e);
        }finally{
            if (log.isDebugEnabled())
            {
                StringBuffer sbEnd= new StringBuffer("End ServicioApuntesContablesImpl.getApuntesByLegajo. numElementos: ")
                                            .append(lista!=null?lista.size():" NULO");
                log.info(sbEnd);
            }
        }
        return lista;
    }

    public HistoricoItemsApuntesId addHistoricoItemApunte(HistoricoItemsApuntes historicoItemApunte) throws StoreException
    {
        return this.servicioPersistenciaApuntesContables.addHistoricoItemApunte(historicoItemApunte);
    }
    
    /**
     * @return the servicioPersistenciaApuntesContables
     */
    public ServicioPersistenciaApuntesContables getServicioPersistenciaApuntesContables() {
        return servicioPersistenciaApuntesContables;
    }

    /**
     * @param servicioPersistenciaApuntesContables the servicioPersistenciaApuntesContables to set
     */
    public void setServicioPersistenciaApuntesContables(ServicioPersistenciaApuntesContables servicioPersistenciaApuntesContables) {
        this.servicioPersistenciaApuntesContables = servicioPersistenciaApuntesContables;
    }

    
}
