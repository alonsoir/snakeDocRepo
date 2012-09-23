/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aironman.core.exceptions;

/**
 * No basta con guardar el motivo, por motivos de eficiencia, el motivo va a ser un numero que va a representar un error controlado.
 * Los errores controlados van a estar guardados en un mapa accesible para todos los servicios. un servicio de traduccion?
 * Por ahora los apunto aqui:
 *
 * CODIGO           EXPLICACION
 * 1                ServicioItemsImpl.addItem. MOTIVO: UnsupportedOperationException
 * 2                ServicioItemsImpl.addItem. MOTIVO: ClassCastException
 * 3                ServicioItemsImpl.addItem. MOTIVO: IllegalArgumentException
 * 4                ServicioItemsImpl.addItem. MOTIVO: NullPointerException
 * 5                ServicioItemsImpl. METHOD: updateItem. ERROR, item es NULO.
 * 6                ServicioItemsImpl. METHOD: getPrizeItem. ERROR. el item no existe en el sistema.
 * 7                Error. Operation: StoreImpl.logIn persona NO encontrada...
 * 8                Error. Operation: StoreImpl.logIn El usuario ya se encuentra logado...
 * 9                Error. Operation: StoreImpl.logOut. La persona no se encuentra en el sistema.
 * 10               Error. NO SE ENCUENTRA LA PERSONA EN EL SISTEMA. Operation: StoreImpl.confirmCart
 * 11               Error. LA PERSONA SE ENCUENTRA EN EL SISTEMA PERO NO HAY ASIGNADA UN NUMERO DE CUENTA BANCARIA. Operation: StoreImpl.confirmCart
 * 12               Error. LA PERSONA SE ENCUENTRA EN EL SISTEMA PERO NO tiene ASIGNADO UN nombre!!. Operation: StoreImpl.confirmCart
 * 13               Error. LA PERSONA SE ENCUENTRA EN EL SISTEMA PERO NO tiene ASIGNADO UNa direccion!!. Operation: StoreImpl.confirmCart
 * 14               Error. Operation: StoreAdminImpl.logIn persona NO encontrada...
 * 15               Error. Operation: StoreAdminImpl.logIn El usuario ya se encuentra logado...
 * 16               Error. Operation: StoreAdminImpl.logOut. La persona no se encuentra en el sistema.
 * 17               ServicioEncriptacionDESImpl.computeHash source
 * 18               ServicioEncriptacionDESImpl.byteArrayToHexString buffer
 * 19               ServicioUsuariosImpl.METHOD: addUser. LA PERSONA NO PUEDE VENIR NULA.
 * 20               ServicioUsuariosImpl.METHOD: addUser. LA PERSONA DEBE VENIR INFORMADA CON EL NOMBRE Y EL EMAIL.
 * 21               ServicioUsuariosImpl.addUser. El usuario con ese NOMBRE YA EXISTE.
 * 22               Error. Operation: ServicioUsuariosImpl.updateUser REASON: nombre no puede ser nulo o vacio...
 * 23               Error. Operation: ServicioUsuariosImpl.updateUser REASON: nombre de la persona recuperada no puede ser nulo o vacio...
 * 24               Error. Operation: ServicioUsuariosImpl.deleteUser REASON: nombre no puede ser nulo o vacio...
 * 25               Error. Operation: ServicioUsuariosImpl.deleteUser REASON: nombre de la persona recuperada no puede ser nulo o vacio...
 * 26               ERROR. Operation ServicioUtilesImpl.nombreToHash REASON: nombre de la persona no puede ser nulo o vacio...
 * 27               ServicioItemsImpl. METHOD: getItem. ERROR. el item no existe en el sistema.
 *
 * 28               StoreImpl.confirmCart. EL usuario no tiene items en el carro de la compra para poder confirmar la compra
 * 29               StoreImpl.getItem El item ya estaba en la lista de los deseos del usuario.
 * 30               ServicioUsuariosImpl.getUser. El email no puede venir nulo o vacio
 * 31               ServicioUsuariosImpl.checkPassword: pass proporcionado es nulo.
 * 32               ServicioUsuariosImpl.checkPassword: Persona no encontrada en el sistema.
   33               StoreImpl.confirmCart email: {0} pass: {1} PASSWORDS NO COINCIDEN. CANCELANDO OPERACION...
 * 34               OperationConfirmCartCallable. ATENCION. EL USUARIO NO TIENE UN CARRO ASIGNADO.
 * @author alonso
 */
public class StoreException extends Exception
{
    // TIENES QUE GENERAR UN SERIAL UID adecuado
    private static final long serialVersionUID = 1L;

    public StoreException(){};

    public StoreException(String _codigo)
    {
        super("CODE:" + _codigo);
    }

    public StoreException(Exception e)
    {
        super(e);
    }
    
    public StoreException(Exception e, String _codigo)
    {
        //super("CODE: " + _codigo + " LEGAJO:" + _legajo + " ISBN: " + _isbn,e);
        super("CODE:" + _codigo,e);
    }
}
