package com.aironman.core.test;

import com.aironman.core.exceptions.StoreException;
import com.aironman.core.pojos.Items;
import com.aironman.core.service.ServicioItems;
import java.util.Collection;
import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.unitils.UnitilsJUnit4;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBean;


@SpringApplicationContext("classpath:applicationContext-persistence-files-test-coreTienda.xml")
public class ServicioItemsTest extends UnitilsJUnit4 {

	private Log log = LogFactory.getLog(ServicioItemsTest.class);
	
	@SpringBean("servicioItems")
	private ServicioItems servicioItems;

        @Test
        public void testAddTwoSameItems()
        {
            try {
                Items item = new Items("ISBN1", "descripcion1", 99.99f, 100);
                this.servicioItems.addItemToSystem(item);
                Items item1 = new Items("ISBN1", "descripcion12", 99.99f, 100);
                this.servicioItems.addItemToSystem(item1);
                Collection<Items> listaItems = this.servicioItems.getAllItems();
                Assert.assertTrue(listaItems.size()>=0);
                for (Items _item:listaItems)
                {
                    log.info("ITEM: " + _item.getIsbn());
                    Assert.assertNotNull(_item.getIsbn());
                }
                //borramos del sistema, tanto de bbdd como de la cache
                this.servicioItems.deleteItemFromSystem(item);
                //esto solo comprueba la cache
                listaItems = this.servicioItems.getAllItems();
                 Assert.assertTrue(listaItems.size()>=0);
            }
            catch (StoreException ex) {
                log.warn("ATENCION! StoreException in testAddAndDeleteItemToSystem...",ex);
                //provoco el fallo
                Assert.assertNull(ex);
            }
        }

        @Test
        public void testAddAndDeleteItemToSystem()
        {
            try {
                Items item = new Items("isbn4000", "descripcion4000", 99.99f, 100);
                this.servicioItems.addItemToSystem(item);
                Items item1 = new Items("isbn4001", "descripcion4001", 99.99f, 100);
                this.servicioItems.addItemToSystem(item1);
                Collection<Items> listaItems = this.servicioItems.getAllItems();
                Assert.assertTrue(listaItems.size()>=0);
                for (Items _item:listaItems)
                {
                    log.info("ITEM: " + _item.getIsbn());
                    Assert.assertNotNull(_item.getIsbn());
                }
                //borramos del sistema, tanto de bbdd como de la cache
                this.servicioItems.deleteItemFromSystem(item);
                //esto solo comprueba la cache
                listaItems = this.servicioItems.getAllItems();
                Assert.assertTrue(listaItems.size()>=0);

                //borramos del sistema, tanto de bbdd como de la cache
                this.servicioItems.deleteItemFromSystem(item1);
                //esto solo comprueba la cache
                listaItems = this.servicioItems.getAllItems();
                Assert.assertTrue(listaItems.size()>=0);
            }
            catch (StoreException ex) {
                log.warn("ATENCION! StoreException in testAddAndDeleteItemToSystem...",ex);
                //provoco el fallo
                Assert.assertNull(ex);
            }
        }

        
        @Test
        public void testUpdateItem()
        {
            try {
                Items item = new Items("ISBN1", "descripcion1", 99.99f, 100);
                this.servicioItems.addItemToSystem(item);
                item.setNumUnidades(1000);
                this.servicioItems.updateItem(item);
                Items itemcito = this.servicioItems.getItem("ISBN1");
                Assert.assertEquals(1000, itemcito.getNumUnidades());
                
            }
            catch (StoreException ex) {
                log.warn("testUpdateItem...",ex);
                Assert.assertNull(ex);
            }
        }
        
        @Test
        public void testFindAll()
        {
            try {
                Items item = new Items("isbn2000", "descripcion2000", 99.99f, 100);
                this.servicioItems.addItemToSystem(item);
                Collection<Items> listaItems = this.servicioItems.getAllItems();
                Assert.assertNotNull(listaItems);
                //debe estar isbn1 e isbn2
                Assert.assertTrue(listaItems.size() > 0);
                for (Items _item:listaItems)
                {
                    log.info("ITEM: " + _item.getIsbn());
                    Assert.assertNotNull(_item);
                }
            }
            catch (StoreException ex) {
                log.warn("testFindAll...",ex);
                Assert.assertNull(ex);
            }
        }
        
        @Test
        public void testFindByIsbn()
        {
            try {
                Items item = this.servicioItems.getItem("isbn2000");
                Assert.assertEquals("isbn2000", item.getIsbn());
                log.info("testFindByIsbn ITEM: " + item.getIsbn());
            }
            catch (StoreException ex) {
                log.warn("testFindByIsbn...",ex);
                Assert.assertNull(ex);
            }
        }

        @Test
        public void testgetDescriptionItem()
        {
            try {
                String description = this.servicioItems.getDescriptionItem("isbn2000");
                Assert.assertEquals("descripcion2000", description);
                log.info("testgetDescriptionItem description: " + description);
            }
            catch (StoreException ex) {
                log.warn("testgetDescriptionItem...",ex);
                Assert.assertNull(ex);
            }
        }

        @Test
        public void testgetPrizeItem()
        {
            try {
                float prize = this.servicioItems.getPrizeItem("isbn2000");
                Assert.assertEquals(99.99f, prize);
                log.info("testgetPrizeItem prize: " + prize);
            }
            catch (StoreException ex) {
                log.warn("testgetPrizeItem...",ex);
                Assert.assertNull(ex);
            }
        }
/*
        @Test
        public void testDeleteAllItems()
        {
            try {
                Collection<Items> lista = this.servicioItems.getAllItems();
                log.info("ServicioItems.testDeleteAllItems. numItems a borrar: " + lista.size());
                for (Items _item : lista) {
                    log.info("trying to delete item with isbn: " + _item.getIsbn());
                    this.servicioItems.deleteItemFromSystem(_item);
                }
                lista = this.servicioItems.getAllItems();
                log.info("ServicioItems.testDeleteAllItems. numItems DESPUES DE BORRAR: " + lista.size());
            }
            catch (StoreException ex) {
                log.warn("testDeleteAllItems...",ex);
                Assert.assertNull(ex);
            }
        }
 * 
 */
}