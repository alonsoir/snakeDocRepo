/**
 * 
 */
package com.aironman.core.service;

import com.aironman.core.exceptions.StoreException;
import com.aironman.core.pojos.Items;
import com.aironman.core.service.callable.OperationAddItemToCartCallable;
import com.aironman.core.service.callable.OperationConfirmCartCallable;
import com.aironman.core.service.callable.OperationDeleteItemFromCartCallable;
import com.aironman.core.service.callable.OperationGetDescriptionItemCallable;
import com.aironman.core.service.callable.OperationGetItemCallable;
import com.aironman.core.service.callable.OperationLogInCallable;
import com.aironman.core.service.callable.OperationLogOutCallable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


/**
 * Implementacion de toda la funcionalidad de la tienda. 
 * @author alonso
 */
@Service("coreServiceImpl")
public class CoreServiceImpl implements CoreService {

    private static final Log log = LogFactory.getLog(CoreServiceImpl.class);

    //ejecutor que lanza los hilos que me provee el poolExecutor
    private ExecutorService             executor;
    //el pool de hilos de spring
    @Autowired
    private ThreadPoolTaskExecutor      poolExecutor;

    //el tiempo de espera que damos al hilo para ejecutarse. Ahora lo tengo hardcodeado en 10000 milisegundos, 10 seg
    private int                         timeOutResponse;

    @Autowired
    private ServicioUsuarios            servicioUsuarios;

    @Autowired
    private ServicioItems               servicioItems;

    @Autowired
    private ServicioApuntesContables    servicioApuntesContables;

    @Autowired
    private ServicioMail                servicioMail;

    public CoreServiceImpl(){
        if (log.isDebugEnabled()){
            log.info("CONSTRUCTOR SIN tipo CoreServiceImpl...");
        }
    }
    
    @PostConstruct
    public void init()
    {
        if (log.isDebugEnabled()){
            log.info("Initializing ExecutorService executor on CoreServiceImpl...");
        }
        executor = Executors.newCachedThreadPool(getPoolExecutor());
        //1000 segundo para terminar la tarea
        setTimeOutResponse(20000);
        if (log.isDebugEnabled()){
            log.info("End Method on CoreServiceImpl...");
        }
    }
    
    public boolean logIn(String hash,String pass, String lat,String lon) throws StoreException
    {
        if (log.isDebugEnabled()){
            StringBuffer sbInit = new StringBuffer("Init CoreServiceImpl.logIn. hash: ")
                                            .append(hash)
                                            .append(" pass: ").append(pass);
            log.info(sbInit);
        }
        boolean retorno = false;
        Callable <Boolean > callableLogIn = new OperationLogInCallable(hash, pass, servicioUsuarios);
        Future <Boolean> future = executor.submit(callableLogIn);
        try {
            retorno = future.get(getTimeOutResponse(), TimeUnit.MILLISECONDS);
        }
        catch (InterruptedException ex) {
            log.warn("InterruptedException on logIn",ex);
            throw new StoreException(ex);
        }
        catch (ExecutionException ex) {
            log.warn("ExecutionException on logIn",ex);
            throw new StoreException(ex);
        }
        catch (TimeoutException ex) {
            log.warn("TimeoutException on logIn",ex);
            throw new StoreException(ex);
        }
        catch (CancellationException ex) {
            log.warn("CancellationException on logIn",ex);
            throw new StoreException(ex);
        }finally
        {
            if (log.isDebugEnabled()){
                StringBuffer sbEnd = new StringBuffer("End CoreServiceImpl.logIn. hash: ")
                                            .append(hash)
                                            .append(" pass: ").append(pass)
                                            .append(" retorno: ").append(retorno);
                log.info(sbEnd);
            }
        }
        return retorno;
    }

    /**
     Metodo que hace el logout del sistema. deja el campo activo de la persona encontrada a false
     * Estos dos metodos logIn y logOut son exactamente iguales en las dos implementaciones, por lo que debo hacer que StoreAdminImpl y StoreImpl
     * hereden una clase base que contenga estos dos metodos.
     * @param email 
     * @return
     * @throws StoreException 
     */
    public boolean logOut(String hash) throws StoreException
    {
        if (log.isDebugEnabled()){
            StringBuffer sbInit = new StringBuffer("Init CoreServiceImpl.logOut. hash: ")
                                            .append(hash);
            log.info(sbInit);
        }
        boolean retorno = false;
        Callable <Boolean > callableLogOut = new OperationLogOutCallable(hash, servicioUsuarios);
        Future <Boolean> future = executor.submit(callableLogOut);
        try {
            retorno = future.get(getTimeOutResponse(), TimeUnit.MILLISECONDS);
        }
        catch (InterruptedException ex) {
            log.warn("InterruptedException on logOut",ex);
            throw new StoreException(ex);
        }
        catch (ExecutionException ex) {
            log.warn("ExecutionException on logOut",ex);
            throw new StoreException(ex);
        }
        catch (TimeoutException ex) {
            log.warn("TimeoutException on logOut",ex);
            throw new StoreException(ex);
        }
        catch (CancellationException ex) {
            log.warn("CancellationException on logOut",ex);
            throw new StoreException(ex);
        }finally
        {
            if (log.isDebugEnabled()){
                StringBuffer sbEnd = new StringBuffer("End CoreServiceImpl.logOut. hash: ")
                                            .append(hash)
                                            .append(" retorno: ").append(retorno);
                log.info(sbEnd);
            }
        }
        return retorno;
    }

    /*
        metodo que se encarga de añadir un item dado su isbn en el carrito del usuario idUsuario identificado en el sistema
     * @param email el identificador del usuario logado en el sistema que quiere añadir en su carrito el producto identificado por el isbn
       @param isbn el identificador del producto
     **/
    public boolean addItemToCart(String hash, String isbn) throws StoreException
    {
        if (log.isDebugEnabled()){
            StringBuffer sbInit = new StringBuffer("Init CoreServiceImpl.addItemToCart. hash: ")
                                            .append(hash)
                                            .append(" isbn: ").append(isbn);
            log.info(sbInit);
        }
        boolean retorno = false;
        Callable <Boolean > callableAddItemToCart = new OperationAddItemToCartCallable(hash, isbn, servicioUsuarios, servicioItems);
        Future <Boolean> future = executor.submit(callableAddItemToCart);
        try {
            retorno = future.get(getTimeOutResponse(), TimeUnit.MILLISECONDS);
        }
        catch (InterruptedException ex) {
            log.warn("InterruptedException on addItemToCart",ex);
            throw new StoreException(ex);
        }
        catch (ExecutionException ex) {
            log.warn("ExecutionException on addItemToCart",ex);
            throw new StoreException(ex);
        }
        catch (TimeoutException ex) {
            log.warn("TimeoutException on addItemToCart",ex);
            throw new StoreException(ex);
        }
        catch (CancellationException ex) {
            log.warn("CancellationException on addItemToCart",ex);
            throw new StoreException(ex);
        }finally
        {
            if (log.isDebugEnabled()){
            StringBuffer sbEnd = new StringBuffer("End CoreServiceImpl.addItemToCart. hash: ")
                                            .append(hash)
                                            .append(" isbn: ")
                                            .append(isbn)
                                            .append(" retorno: ").append(retorno);
            log.info(sbEnd);
            }

        }
        return retorno;
    }

    public boolean deleteItemFromCart(String hash, String isbn) throws StoreException
    {
        if (log.isDebugEnabled()){
            StringBuffer sbInit = new StringBuffer("Init CoreServiceImpl.deleteItemFromCart. hash: ")
                                            .append(hash)
                                            .append(" isbn: ").append(isbn);
            log.info(sbInit);
        }
        boolean retorno = false;
        Callable <Boolean > callableDeleteItemToCart = new OperationDeleteItemFromCartCallable(hash, isbn, servicioUsuarios, servicioItems);
        Future <Boolean> future = executor.submit(callableDeleteItemToCart);
        try {
            retorno = future.get(getTimeOutResponse(), TimeUnit.MILLISECONDS);
        }
        catch (InterruptedException ex) {
            log.warn("InterruptedException on deleteItemFromCart",ex);
            throw new StoreException(ex);
        }
        catch (ExecutionException ex) {
            log.warn("ExecutionException on deleteItemFromCart",ex);
            throw new StoreException(ex);
        }
        catch (TimeoutException ex) {
            log.warn("TimeoutException on deleteItemFromCart",ex);
            throw new StoreException(ex);
        }
        catch (CancellationException ex) {
            log.warn("CancellationException on deleteItemFromCart",ex);
            throw new StoreException(ex);
        }finally
        {
            if (log.isDebugEnabled()){
            StringBuffer sbEnd = new StringBuffer("End CoreServiceImpl.deleteItemFromCart. hash: ")
                                            .append(hash)
                                            .append(" isbn: ")
                                            .append(isbn)
                                            .append(" retorno: ").append(retorno);
            log.info(sbEnd);
            }

        }
        return retorno;
    }

    /**
        AHORA PASO EL EMAIL Y EL PASS EN TEXTO PLANO, VALE PARA ESTA FASE, PERO DEBEN IR CIFRADOS, HASTA LA INVOCACION DEL SERVICIO
     *  CON LOS PARAMETROS DEBEN IR CIFRADOS, O SEA, email sera otra cosa, como liame pej,  y pass puede ser por ejemplo ssap
     *  el caso es ponerselo dificil a los cacos
     */
    public boolean confirmCart(String hash,String passConfirm) throws StoreException
    {
        if (log.isDebugEnabled()){
            StringBuffer sbInit = new StringBuffer("Init CoreServiceImpl.confirmCart. hash: ")
                                            .append(hash)
                                            .append(" passConfirm: ").append(passConfirm);
            log.info(sbInit);
        }
        boolean retorno = false;
        Callable <Boolean > callableConfirmCartCallable = new OperationConfirmCartCallable(hash, passConfirm
                                                                                        , servicioUsuarios
                                                                                        , servicioItems
                                                                                        , servicioApuntesContables
                                                                                        , servicioMail);
        Future <Boolean> future = executor.submit(callableConfirmCartCallable);
        try {
            retorno = future.get(getTimeOutResponse(), TimeUnit.MILLISECONDS);
        }
        catch (InterruptedException ex) {
            log.warn("InterruptedException on confirmCart",ex);
            throw new StoreException(ex);
        }
        catch (ExecutionException ex) {
            log.warn("ExecutionException on confirmCart",ex);
            throw new StoreException(ex);
        }
        catch (TimeoutException ex) {
            log.warn("TimeoutException on confirmCart",ex);
            throw new StoreException(ex);
        }
        catch (CancellationException ex) {
            log.warn("CancellationException on confirmCart",ex);
            throw new StoreException(ex);
        }finally
        {
            if (log.isDebugEnabled()){
            StringBuffer sbEnd = new StringBuffer("End CoreServiceImpl.confirmCart. hash: ")
                                            .append(hash)
                                            .append(" passConfirm: ")
                                            .append(passConfirm)
                                            .append(" retorno: ").append(retorno);
            log.info(sbEnd);
            }

        }
        return retorno;
    }

    public String getDescriptionItem(String isbn) throws StoreException
    {
        if (log.isDebugEnabled()){
        StringBuffer sbInit = new StringBuffer("Init CoreServiceImpl.getDescriptionItem. isbn: ")
                                            .append(isbn);
        log.info(sbInit);
        }
        String description = null;
        Callable <String > callableGetDescriptionItemCallable = new OperationGetDescriptionItemCallable(isbn, servicioItems);
        Future <String> future = executor.submit(callableGetDescriptionItemCallable);
        try {
            description = future.get(getTimeOutResponse(), TimeUnit.MILLISECONDS);
        }
        catch (InterruptedException ex) {
            log.warn("InterruptedException on getDescriptionItem",ex);
            throw new StoreException(ex);
        }
        catch (ExecutionException ex) {
            log.warn("ExecutionException on getDescriptionItem",ex);
            throw new StoreException(ex);
        }
        catch (TimeoutException ex) {
            log.warn("TimeoutException on getDescriptionItem",ex);
            throw new StoreException(ex);
        }
        catch (CancellationException ex) {
            log.warn("CancellationException on getDescriptionItem",ex);
            throw new StoreException(ex);
        }finally
        {
            if (log.isDebugEnabled()){
            StringBuffer sbEnd = new StringBuffer("End CoreServiceImpl.getDescriptionItem. isbn: ")
                                            .append(isbn)
                                            .append(" description: ").append(description);
            log.info(sbEnd);
            }

        }
        return description;
    }

    /*
     Este metodo va a traerte el item y va a meterlo en la lista de la compra de la persona que lo ha solicitado
     */
    public Items getItem(String hash,String isbn) throws StoreException
    {
        if (log.isDebugEnabled()){
        StringBuffer sbInit = new StringBuffer("Init CoreServiceImpl.getItem. isbn: ")
                                            .append(isbn)
                                            .append(" hash: ")
                                            .append(hash);
        log.info(sbInit);
        }
        Items item = null;
        Callable <Items > callableGetItemCallable = new OperationGetItemCallable(hash, isbn, servicioUsuarios, servicioItems);
        Future <Items> future = executor.submit(callableGetItemCallable);
        try {
            item = future.get(getTimeOutResponse(), TimeUnit.MILLISECONDS);
        }
        catch (InterruptedException ex) {
            log.warn("InterruptedException on getItem",ex);
            throw new StoreException(ex);
        }
        catch (ExecutionException ex) {
            log.warn("ExecutionException on getItem",ex);
            throw new StoreException(ex);
        }
        catch (TimeoutException ex) {
            log.warn("TimeoutException on getItem",ex);
            throw new StoreException(ex);
        }
        catch (CancellationException ex) {
            log.warn("CancellationException on getItem",ex);
            throw new StoreException(ex);
        }finally
        {
            if (log.isDebugEnabled()){
            StringBuffer sbEnd = new StringBuffer("End CoreServiceImpl.getItem. isbn: ")
                                            .append(isbn)
                                            .append(" hash: ")
                                            .append(hash)
                                            .append(" item: ").append(item);
            log.info(sbEnd);
            }

        }
        return item;
    }
    
    /***GETTERS AND SETTERS**/
    /**
     * @return the servicioUsuarios
     */
    public ServicioUsuarios getServicioUsuarios() {
        return servicioUsuarios;
    }

    /**
     * @param servicioUsuarios the servicioUsuarios to set
     */
    public void setServicioUsuarios(ServicioUsuarios servicioUsuarios) {
        this.servicioUsuarios = servicioUsuarios;
    }


    /**
     * @return the servicioItems
     */
    public ServicioItems getServicioItems() {
        return servicioItems;
    }

    /**
     * @param servicioItems the servicioItems to set
     */
    public void setServicioItems(ServicioItems servicioItems) {
        this.servicioItems = servicioItems;
    }

    /**
     * @return the servicioApuntesContables
     */
    public ServicioApuntesContables getServicioApuntesContables() {
        return servicioApuntesContables;
    }

    /**
     * @param servicioApuntesContables the servicioApuntesContables to set
     */
    public void setServicioApuntesContables(ServicioApuntesContables servicioApuntesContables) {
        this.servicioApuntesContables = servicioApuntesContables;
    }

    /**
     * @return the poolExecutor
     */
    public ThreadPoolTaskExecutor getPoolExecutor() {
        return poolExecutor;
    }

    /**
     * @param poolExecutor the poolExecutor to set
     */
    public void setPoolExecutor(ThreadPoolTaskExecutor poolExecutor) {
        this.poolExecutor = poolExecutor;
    }

    /**
     * @return the timeOutResponse
     */
    public int getTimeOutResponse() {
        return timeOutResponse;
    }

    /**
     * @param timeOutResponse the timeOutResponse to set
     */
    public void setTimeOutResponse(int timeOutResponse) {
        this.timeOutResponse = timeOutResponse;
    }

    
}