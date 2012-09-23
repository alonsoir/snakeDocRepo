package com.aironman.core.test;

import com.aironman.core.exceptions.StoreException;
import com.aironman.core.pojos.ApuntesContables;
import com.aironman.core.service.ServicioApuntesContables;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
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
public class ServicioApuntesContablesTest extends UnitilsJUnit4 {

	private Log log = LogFactory.getLog(ServicioApuntesContablesTest.class);
	
	@SpringBean("servicioApuntes")
	private ServicioApuntesContables servicioApuntesContables;

        @Test
        public void testaddApunteContable()
        {
            log.info("INIT testaddApunteContable...");
            try {
                ApuntesContables apunte = new ApuntesContables();
                //value.setId(null);
                //esto asume que LEGAJO1 existe en la tabla de Usuarios
                apunte.setLegajo("LEGAJO1");
                apunte.setCargo(99.99f);
                //esta fecha me la calcula la bbdd en el momento de hacer la insercion. NI DE COÑA. atencion que tienes aqui un bug de la leche
                java.util.Date date = new Date();
                apunte.setFechaConfirmacion(new Timestamp(date.getTime()));
                
                Long generatedKey = this.servicioApuntesContables.addApunteContable(apunte);
                Assert.assertNotNull(generatedKey);
                log.info("generatedKey: " + generatedKey);
            }
            catch (StoreException ex) {
                Logger.getLogger(ServicioApuntesContablesTest.class.getName()).log(Level.SEVERE, null, ex);
                Assert.assertNull(ex);
            }
            log.info("ENd testaddApunteContable...");

        }

        @Test
        public void getApunteContableByClave()
        {
            log.info("INIT getApunteContableByClave...");
            try {
                ApuntesContables apunte = new ApuntesContables();
                //value.setId(null);
                //esto asume que LEGAJO1 existe en la tabla de Usuarios
                apunte.setLegajo("LEGAJO1");
                apunte.setCargo(99.99f);
                java.util.Date date = new Date();
                apunte.setFechaConfirmacion(new Timestamp(date.getTime()));

                Long generatedKey = this.servicioApuntesContables.addApunteContable(apunte);
                Assert.assertNotNull(generatedKey);
                log.info("generatedKey: " + generatedKey);
                ApuntesContables apunte1 =this.servicioApuntesContables.getApunteContableByClave(generatedKey);
                Assert.assertNotNull(apunte1);
                Assert.assertEquals(apunte1.getLegajo(), apunte.getLegajo());
            }
            catch (StoreException ex) {
                Logger.getLogger(ServicioApuntesContablesTest.class.getName()).log(Level.SEVERE, null, ex);
                Assert.assertNull(ex);
            }
            log.info("ENd getApunteContableByClave...");

        }

        @Test
        public void testgetAllApuntes()
        {
            log.info("Init testgetAllApuntes...");
            try {
                List<ApuntesContables> lista = this.servicioApuntesContables .getAllApuntes();
                Assert.assertNotNull(lista);
                log.info("numApuntes: " + lista.size());
            }
            catch (StoreException ex) {
                Logger.getLogger(ServicioApuntesContablesTest.class.getName()).log(Level.SEVERE, null, ex);
                Assert.assertNull(ex);
            }
            log.info("End testgetAllApuntes...");

        }
}