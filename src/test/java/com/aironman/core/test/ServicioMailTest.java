package com.aironman.core.test;

import com.aironman.core.exceptions.StoreException;
import com.aironman.core.service.ServicioMail;
import java.util.logging.Level;
import java.util.logging.Logger;

import junit.framework.Assert;
import org.unitils.UnitilsJUnit4;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;


@SpringApplicationContext("classpath:applicationContext-persistence-files-test-coreTienda.xml")
public class ServicioMailTest extends UnitilsJUnit4 {

	private Log log = LogFactory.getLog(ServicioMailTest.class);
	
	@SpringBean("servicioMail")
	private ServicioMail servicioMail;

        @Test
        public void testenviarMail()
        {
            /*
            log.info("init testenviarMail...");
            try
            {
                boolean retorno = this.servicioMail.enviarMail("alonsoir@gmail.com");
                log.info("retorno: " + retorno);
                Assert.assertTrue(retorno);
            } catch (StoreException ex)
            {
                Logger.getLogger(ServicioMailTest.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                log.info("end testenviarMail...");
            }
             * 
             */
        }
        
}