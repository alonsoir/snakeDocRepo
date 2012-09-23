package com.aironman.core.test;

import com.aironman.core.exceptions.StoreException;
import com.aironman.core.pojos.Usuarios;
import com.aironman.core.service.ServicioUsuarios;
import com.aironman.core.utils.Constantes;
import java.util.Random;
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
public class ServicioUsuariosTest extends UnitilsJUnit4 {

	private Log log = LogFactory.getLog(ServicioUsuariosTest.class);
	
	@SpringBean("servicioUsuarios")
	private ServicioUsuarios servicioUsuarios;

        @Test
        public void testAddTwoSameUsers()
        {
            log.info("INIT testAddTwoSameUsers...");
            Random r = new Random();
            r.setSeed(r.nextLong());
            Float f = r.nextFloat();
            log.info("numero generado: " + f.toString());

            try {
                Usuarios usuario1 = new Usuarios();
                usuario1.setDireccion(f.toString());
                usuario1.setEmail("@" +  f.toString());
                usuario1.setLon("LON");
                usuario1.setLat("LAT");
                usuario1.setNombre( f.toString());
                usuario1.setNumeroCC( f.toString());
                usuario1.setPassword(f.toString());
                usuario1.setPassConfirm("PASS");
                usuario1.setStatus(Constantes.estadoUsuarioInactivo);
                //usuario1.setCart(null);

                boolean retorno = this.servicioUsuarios.addUser(usuario1);
                log.info("RETORNO: " + retorno);
                Assert.assertTrue(retorno);
                //lo intento añadir otra vez, me tendria que decir que nones
                boolean retornoSame = this.servicioUsuarios.addUser(usuario1);
                log.info("RETORNO_SAME: " + retornoSame);

                Assert.assertTrue(!retornoSame);


            }
            catch (StoreException ex) {
                log.warn("ATENCION! StoreException in testAddTwoSameUsers...",ex);
                //provoco el fallo
                Assert.assertNull(ex);
            }
            log.info("END testAddTwoSameUsers...");

        }

        @Test
        public void testAddAndDeleteUserToSystem()
        {
            log.info("INIT testAddAndDeleteUserToSystem...");
            Random r = new Random();
            r.setSeed(r.nextLong());
            Float f = r.nextFloat();
            log.info("numero generado: " + f.toString());

            try {
                Usuarios usuario1 = new Usuarios();
                usuario1.setDireccion(f.toString());
                usuario1.setEmail("@" + f.toString());
                usuario1.setLon("LON");
                usuario1.setLat("LAT");
                usuario1.setNombre( f.toString());
                usuario1.setNumeroCC( f.toString());
                usuario1.setPassword(f.toString());
                usuario1.setPassConfirm("PASS2");
                usuario1.setStatus(Constantes.estadoUsuarioInactivo);
                //usuario1.setCart(null);

                boolean retorno = this.servicioUsuarios.addUser(usuario1);
                log.info("RETORNO: " + retorno);
                Assert.assertTrue(retorno);
                //esta operacion va a cambiar el estado status a false, no hace un borrado fisico en el sistema
                boolean retornoDelete = this.servicioUsuarios.deleteUser(usuario1);
                log.info("retornoDelete: " + retornoDelete);

                Assert.assertTrue(retornoDelete);


            }
            catch (StoreException ex) {
                log.warn("ATENCION! StoreException in testAddAndDeleteUserToSystem...",ex);
                //provoco el fallo
                Assert.assertNull(ex);
            }
            log.info("INIT testAddAndDeleteUserToSystem...");

        }

        @Test
        public void testGetUser()
        {
            log.info("Init testGetUser...");
            try {
                Usuarios usuario = this.servicioUsuarios.getUser("LEGAJO2");
                Assert.assertNotNull(usuario);
                log.info("USUARIO: " + usuario.toString());

                Assert.assertEquals("email2", usuario.getEmail());
            
            }
            catch (StoreException ex) {
                Logger.getLogger(ServicioUsuariosTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        
        @Test
        public void testUpdateUser()
        {
            log.info("Init testUpdateUser...");
            try {
                Usuarios usuario = this.servicioUsuarios.getUser("LEGAJO2");
                Assert.assertNotNull(usuario);
                log.info("USUARIO: " + usuario.toString());
                usuario.setDireccion("DIRECCIONMODIFICADA");
                boolean retornoUpdate = this.servicioUsuarios.updateUser(usuario);
                Assert.assertTrue(retornoUpdate);
                usuario = this.servicioUsuarios.getUser("LEGAJO2");
                Assert.assertEquals("DIRECCIONMODIFICADA", usuario.getDireccion());
            }
            catch (StoreException ex) {
                log.warn("StoreException on testUpdateUser...",ex);
                Assert.assertNull(ex);
            }
            log.info("End testUpdateUser...");

        }

        @Test
        public void testCheckPassword()
        {
            log.info("Init testCheckPassword...");
            try {
                boolean retornoCheck = this.servicioUsuarios.checkPassword("LEGAJO2", "pass2");
                log.info("retornoCheck" + retornoCheck);
                Assert.assertTrue(retornoCheck);
            //induzco el error
                retornoCheck = this.servicioUsuarios.checkPassword("LEGAJO2", "PASS21");
                Assert.assertTrue(!retornoCheck);

            }
            catch (StoreException ex) {
                log.warn("StoreException on testCheckPassword...",ex);
                Assert.assertNull(ex);
            }
            log.info("End testCheckPassword...");
        }

        @Test
        public void testCheckConfirmPassword()
        {
            log.info("Init testCheckConfirmPassword...");
            try {
                boolean retornoCheck = this.servicioUsuarios.checkConfirmPassword("LEGAJO2", "pass2");
                log.info("retornoCheck" + retornoCheck);
                Assert.assertTrue(retornoCheck);
            //induzco el error
                retornoCheck = this.servicioUsuarios.checkPassword("LEGAJO2", "PASS21");
                Assert.assertTrue(!retornoCheck);

            }
            catch (StoreException ex) {
                log.warn("StoreException on testCheckConfirmPassword...",ex);
                Assert.assertNull(ex);
            }
            log.info("End testCheckConfirmPassword...");
        }
        
}