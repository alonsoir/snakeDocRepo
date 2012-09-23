/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aironman.core.utils;

import com.aironman.core.pojos.Items;
import java.util.ArrayList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author alonso
 */
public class CarroDeLaCompra
{
     private static final Log log = LogFactory.getLog(CarroDeLaCompra.class);

    //esto no se va a persistir
     private ArrayList<Items> carroDelaCompra;

     public CarroDeLaCompra(){
        carroDelaCompra=new ArrayList<Items>();
     }
    /**
     * @return the carroDelaCompra
     */
    public ArrayList<Items> getCarroDelaCompra()
    {
        return carroDelaCompra;
    }

    /**
     * @param carroDelaCompra the carroDelaCompra to set
     */
    public void setCarroDelaCompra(ArrayList<Items> carroDelaCompra)
    {
        this.carroDelaCompra = carroDelaCompra;
    }
    public boolean addItemToCarro(Items item)
    {
        if (log.isDebugEnabled()){
            log.info("CarroDeLaCompra.addItemToCarro. item: " + item.toString());
        }
        boolean retorno=this.getCarroDelaCompra().add(item);
        if (log.isDebugEnabled()){
            log.info("CarroDeLaCompra.addItemToCarro. item: " + item.toString() + " retorno: " + retorno);
        }
        return retorno;
    }

    public boolean deleteItemFromCarro(Items item)
    {
        if (log.isDebugEnabled()){
            log.info("Init CarroDeLaCompra.deleteItemFromCarro. item: " + item.toString());
        }
        boolean retorno=this.getCarroDelaCompra().remove(item);
        if (log.isDebugEnabled()){
            log.info("End CarroDeLaCompra.deleteItemFromCarro. item: " + item.toString() + " retorno: " + retorno);
        }
        return retorno;
    }

    public void deleteAllItemsFromCarro()
    {
        if (log.isDebugEnabled()){
            log.info("Init CarroDeLaCompra.deleteItemFromCarro. ");
        }
        int numElementos = this.getCarroDelaCompra().size();
        this.getCarroDelaCompra().clear();
        if (log.isDebugEnabled()){
            log.info("End CarroDeLaCompra.deleteItemFromCarro. Borrados " + numElementos + " elementos de la lista de la compra");
        }

    }

}
