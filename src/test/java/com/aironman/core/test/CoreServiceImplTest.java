package com.aironman.core.test;

import com.aironman.core.exceptions.StoreException;
import com.aironman.core.pojos.Items;
import com.aironman.core.service.CoreService;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.unitils.UnitilsJUnit4;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBean;


@SpringApplicationContext("classpath:applicationContext-persistence-files-test-coreTienda.xml")
public class CoreServiceImplTest extends UnitilsJUnit4 {

	private Log log = LogFactory.getLog(CoreServiceImplTest.class);
	
	@SpringBean("coreServiceImpl")
	private CoreService coreService;

        @Test
        public void testLogIn()
        {
            //el usuario con email EMAIL1 tiene como legajo 89C726C7FD7F4E065C82EA11D03D14DE3C35E5C591644C874DA12F7606FA0BA0
            try {
                boolean retorno = this.coreService.logIn("89C726C7FD7F4E065C82EA11D03D14DE3C35E5C591644C874DA12F7606FA0BA0", "PASS1", null, null);
                Assert.assertTrue(retorno);
            }
            catch (StoreException ex) {
                Logger.getLogger(CoreServiceImplTest.class.getName()).log(Level.SEVERE, null, ex);
                Assert.assertNull(ex);
            }
        }

        @Test
        public void testLogOut()
        {
            try {
                boolean retorno = this.coreService.logOut("89C726C7FD7F4E065C82EA11D03D14DE3C35E5C591644C874DA12F7606FA0BA0");
                Assert.assertTrue(retorno);
            }
            catch (StoreException ex) {
                Logger.getLogger(CoreServiceImplTest.class.getName()).log(Level.SEVERE, null, ex);
                Assert.assertNull(ex);
            }
        }

        @Test
        public void testAddItemToCart()
        {
            //aqui hay un bug, el carro de la compra no esta inicializado correctamente...
            try {
                boolean retorno=this.coreService.addItemToCart("89C726C7FD7F4E065C82EA11D03D14DE3C35E5C591644C874DA12F7606FA0BA0", "ISBN1");
                boolean retorno1=this.coreService.addItemToCart("89C726C7FD7F4E065C82EA11D03D14DE3C35E5C591644C874DA12F7606FA0BA0", "ISBN1");
                Assert.assertTrue(retorno1);
                Assert.assertTrue(retorno);
            }
            catch (StoreException ex) {
                Logger.getLogger(CoreServiceImplTest.class.getName()).log(Level.SEVERE, null, ex);
                Assert.assertNull(ex);
            }
        }

        @Test
        public void testDeleteItemFromCart()
        {
            try {
                //89C726C7FD7F4E065C82EA11D03D14DE3C35E5C591644C874DA12F7606FA0BA0 es el hash del usuario
                boolean retorno=this.coreService.deleteItemFromCart("89C726C7FD7F4E065C82EA11D03D14DE3C35E5C591644C874DA12F7606FA0BA0", "ISBN1");
                Assert.assertTrue(retorno);
            }
            catch (StoreException ex) {
                Logger.getLogger(CoreServiceImplTest.class.getName()).log(Level.SEVERE, null, ex);
                Assert.assertNull(ex);
            }
        }

                @Test
        public void testGetDescriptionItem()
        {
            try {
                String description = this.coreService.getDescriptionItem("ISBN1");
                Assert.assertNotNull(description);
            }
            catch (StoreException ex) {
                Logger.getLogger(CoreServiceImplTest.class.getName()).log(Level.SEVERE, null, ex);
                Assert.assertNull(ex);
            }
        }

        @Test
        public void testGetItem()
        {
            try {
                Items item = this.coreService.getItem("89C726C7FD7F4E065C82EA11D03D14DE3C35E5C591644C874DA12F7606FA0BA0", "ISBN1");
                Assert.assertNotNull(item);
                log.info("TestgetItem: " + item.toString());
            }
            catch (StoreException ex) {
                Logger.getLogger(CoreServiceImplTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Test
        public void testConfirmCart()
        {
            try {
                
                boolean retorno = this.coreService.confirmCart("89C726C7FD7F4E065C82EA11D03D14DE3C35E5C591644C874DA12F7606FA0BA0", "PASS");
                log.info("testConfirmCart: " + retorno);
                //Assert.assertTrue(retorno);
            }
            catch (StoreException ex) {
                Logger.getLogger(CoreServiceImplTest.class.getName()).log(Level.SEVERE, null, ex);
                Assert.assertNull(ex);
            }
        }



}