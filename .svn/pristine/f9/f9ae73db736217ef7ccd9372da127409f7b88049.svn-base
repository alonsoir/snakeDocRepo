package com.aironman.core.service;


import java.security.Security;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SimpleMail
{
    /*
     working code que sirve para mandar emails usando gmail. Que tal una implementacion JMS con un publicador consumidor, de forma que cuando
     * se haya terminado de hacer la operacion confirmCart, el publicador mande un mensaje al consumidor para que envie un mail tan pronto como
     * el consumidor pueda. El publicador y el consumidor son operaciones asincronicas, ademas poder situar al consumidor en una maquina lejana
     * a dd este el servidor de aplicaciones que aloje el core.
     * Que tal usar un modelo jms Punto a punto?
     *
     * http://es.wikipedia.org/wiki/Java_Message_Service
     * Modelo Punto a Punto (point to point): Este modelo cuenta con solo dos clientes,
       uno que envía el mensaje y otro que lo recibe.
       Este modelo asegura la llegada del mensaje ya que si el receptor no esta disponible para aceptar el mensaje o atenderlo
       , de cualquier forma se le envía el mensaje y este se agrega en una cola del tipo FIFO para luego ser recibido según haya entrado.
     
     * El publicador sera algo inyectado al coreService de forma que en la operacionConfirmCart enviara un mensaje con el mensaje de confirmacion
     * al otro punto que recogerá el mensaje y usando el servicioMail creara un mail y lo enviara a la direccion de destino.    
     * 
     * http://www.programacion.com/articulo/introduccion_a_jms_java_message_service_142
     *
     */
    public static void main(String[] args) throws Exception
    {
    Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
    Properties props = new Properties();
    props.setProperty("mail.transport.protocol", "smtp");
    props.setProperty("mail.host", "smtp.gmail.com");

    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.port", "465");

    props.put("mail.debug", "true");
    props.put("mail.smtp.socketFactory.port", "465");

    props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
    props.put("mail.smtp.socketFactory.fallback", "false");

    Session session = Session.getDefaultInstance(props,new javax.mail.Authenticator()
    {
        protected PasswordAuthentication getPasswordAuthentication()
        {
        return new PasswordAuthentication("alonsoir@gmail.com","auz77dbt");
        }
    });//fin de la instanciacion de session

    session.setDebug(true);
    Transport transport = session.getTransport();
    InternetAddress addressFrom = new InternetAddress("alonsoir@gmail.com");
    MimeMessage message = new MimeMessage(session);
    message.setSender(addressFrom);

    for(int j=0;j<1;j++)
    {
        message.setSubject("Testing javamail plain"+Math.random());
        message.setContent("This is a test", "text/plain");
        String sendTo [] = {"alonsoir@gmail.com"};
        if (sendTo != null) {
            InternetAddress[] addressTo = new InternetAddress[sendTo.length];
            for (int i = 0; i < sendTo.length; i++) {
                addressTo[i] = new InternetAddress(sendTo[i]);
            }
        message.setRecipients(Message.RecipientType.TO, addressTo);

        }
        transport.connect();
        transport.send(message);
        transport.close();
//Transport.send(message);
        System.out.println("DONE");
    }//del for
    
    }//del main
}