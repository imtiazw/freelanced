/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SignUp;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author ALI
 */
public class GenerateEmail {

    public GenerateEmail() {
    }
    
    public boolean sendEmail(String primaryEmail, String messageText){
    
        boolean result = false;
        Properties props= new Properties();  
        props.put("mail.smtp.host","smtp.gmail.com");  
        props.put("mail.smtp.socketFactory.port", "465");  
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");  
        props.put("mail.smtp.auth", "true");  
        props.put("mail.smtp.port", "465");  
          
        Session session= Session.getDefaultInstance(props,   
                new javax.mail.Authenticator() {  
                @Override  
                protected PasswordAuthentication getPasswordAuthentication(){  
                return new PasswordAuthentication("mc120401463@vu.edu.pk", "vu974tbpq");  
                }  
                });  
          
        try{  
            Message message= new MimeMessage(session);  
            message.setFrom(new InternetAddress("mc120401463@vu.edu.pk"));  
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(primaryEmail));  
            message.setSubject("Online Chess Game. Verify Email!!!");  
            message.setText(messageText);  
            Transport.send(message);  
              
            System.out.println("Message sent successfully");  
            result = true;
        }catch(MessagingException ex){  
          
            System.out.println(ex);  
        }
        return result;
    }
}
