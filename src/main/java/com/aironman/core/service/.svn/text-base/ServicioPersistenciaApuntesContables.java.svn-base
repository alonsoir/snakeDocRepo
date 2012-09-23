/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aironman.core.service;

import com.aironman.core.exceptions.StoreException;
import com.aironman.core.pojos.ApuntesContables;
import com.aironman.core.pojos.HistoricoItemsApuntes;
import com.aironman.core.pojos.HistoricoItemsApuntesId;
import java.util.List;

/**
 *
 * @author alonso
 */
public interface ServicioPersistenciaApuntesContables {

    Long addApunteContable(ApuntesContables value)                                              throws StoreException;

    ApuntesContables getApunteContableByClave(Long key)                                         throws StoreException;

    List<ApuntesContables> getAllApuntes()                                                      throws StoreException;

    List<ApuntesContables> getApuntesByLegajo(String legajo)                                    throws StoreException;

    HistoricoItemsApuntesId addHistoricoItemApunte(HistoricoItemsApuntes historicoItemApunte)   throws StoreException;
}
