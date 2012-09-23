/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aironman.core.service;

import com.aironman.core.exceptions.StoreException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 *  Clase que me permite encriptar y comprobar si la cadena es la proporcionada
 * @author alonso Isidoro Roman
 * SEE http://www.rgagnon.com/javadetails/java-0400.html
 */
@Service("servicioEncriptacion")
public class ServicioEncriptacionImpl implements ServicioEncriptacion{

  private static final String  algoritmo = "SHA-256";
  private static final Log log = LogFactory.getLog(ServicioEncriptacionImpl.class);
  private static java.security.MessageDigest diggest ;

  public ServicioEncriptacionImpl()
  {
        if (log.isDebugEnabled()){
            log.info("INIT Constructor ServicioEncriptacionImpl...");
        }
        try {
            diggest = java.security.MessageDigest.getInstance(algoritmo);
        }
        catch (NoSuchAlgorithmException ex) {
            log.warn("EXCEPTION ON ServicioEncriptacionImpl.CONSTRUCTOR...",ex);
        }finally{
            if (log.isDebugEnabled()){
                log.info("END Constructor ServicioEncriptacionImpl...");
            }
        }
  }
  /**
   Metodo que se encarga de calcular el hash de una fuente
   * @param source la fuente a encriptar
   * @return String la cadena encriptada, o el hash correspondiente a la cadena
   * @throws StoreException 
   */
  public final String hash(String source) throws StoreException
  {
      if (log.isDebugEnabled()){
        StringBuilder sbInit = new StringBuilder("INIT ServicioEncriptacionImpl.hash. source: ").append(source);
        log.info(sbInit);
      }
      String hash = byteArrayToHexString(ServicioEncriptacionImpl.computeHash(source));
      if (log.isDebugEnabled()){
        StringBuilder sbEnd = new StringBuilder("END ServicioEncriptacionImpl.hash. computed hash: ").append(hash);
        log.info(sbEnd);
      }
      return hash;
  }
  /**
   Metodo que se encargar de comprobar el hash de la fuente.
   * @param hash el numero unico que se saca a partir de una fuente
   * @param source la cadena fuente
   * @return boolean, true si source h hash son iguales, false NO
   * @throws StoreException
   */
  public final boolean checkHash(String hash, String source) throws StoreException
  {
        if (log.isDebugEnabled()){
            StringBuilder sbInit = new StringBuilder("INIT ServicioEncriptacionImpl.checkHash. source: ")
                                            .append(source).append(" hash: ").append(hash);
            log.info(sbInit);
        }

        boolean ok=false;
    //String hash= this.encrypt(source);
        String inputHash = byteArrayToHexString(ServicioEncriptacionImpl.computeHash(source));
        if (hash.equals(inputHash)){
             ok = true;
        }
        if (log.isDebugEnabled()){
            StringBuilder sbEnd = new StringBuilder("END ServicioEncriptacionImpl.checkHash. hash: ")
                            .append(hash).append(" source: ").append(source).append("resultado: ").append((ok) ? " OK" : " KO");
            log.info(sbEnd);
    }

    return ok;
  }
  /**
     Metodo que se encarga de calcular el hash de una cadena.
   * @param source la cadena a encriptar
   * @return byte [] array de bytes con la cadena codificada
   */
  private final static byte[] computeHash(String source) throws StoreException
  {
        if (log.isDebugEnabled()){
            StringBuffer sbInit = new StringBuffer("INIT computeHash. source: ").append(source);
            log.info(sbInit);
        }
        try {
            diggest.reset();
            diggest.update(source.getBytes());
            return diggest.digest();
        } catch (Exception ex) {
            if (log.isDebugEnabled()){
                StringBuffer sbInit = new StringBuffer("ATENCION. Exception computeHash. source: ").append(source);
                log.warn(sbInit,ex);
            }
            throw new StoreException(ex,"17");
        }finally{
            if (log.isDebugEnabled()){
                StringBuffer sbInit = new StringBuffer("END computeHash. source: ").append(source);
                log.info(sbInit);
            }
        }

  }

  /**
   Metodo que se encarga de convertir en formato String un array de bytes.
   * @param buffer el array de bytes a convertir
   * @return  String el string convertido
   */
  private final static String byteArrayToHexString(byte[] buffer) throws StoreException
  {
     if (log.isDebugEnabled()){
                StringBuffer sbInit = new StringBuffer("INIT byteArrayToHexString. buffer: ").append(buffer);
                log.info(sbInit);
     }
     
     if (buffer == null || (buffer.length == 0))
     {
            if (log.isDebugEnabled()){
                StringBuffer sbInit = new StringBuffer("buffer viene vacio o nulo...");
                log.warn(sbInit);
            }
            throw new StoreException("18");
     }
     //Mejor usar StringBuilder, que tiene mejor rendimiento al no ser capaz de operar en modo hilo seguro
     //StringBuffer si es de hilo seguro.
     StringBuilder sb = new StringBuilder(buffer.length * 2);
     for (int i = 0; i < buffer.length; i++){
       int v = buffer[i] & 0xff;
       if (v < 16) {
         sb.append('0');
       }
       sb.append(Integer.toHexString(v));
     }
     String generated=sb.toString().toUpperCase();
     if (log.isDebugEnabled()){
                StringBuffer sbEnd = new StringBuffer("END byteArrayToHexString. buffer: ")
                        .append(buffer)
                        .append(" generated: ")
                        .append(generated);
                log.info(sbEnd);
     }
     return generated;
  }
}