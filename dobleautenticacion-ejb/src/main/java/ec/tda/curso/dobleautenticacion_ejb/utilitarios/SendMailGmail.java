package ec.tda.curso.dobleautenticacion_ejb.utilitarios;

import ec.tda.curso.dobleautenticacion_ejb.entidades.Usuario;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendMailGmail {

    private String correoEnvia = "santy2516@gmail.com";
    private String claveCorreo = "zdqmko2516crazygod";
    private Properties properties = new Properties();

    private String factorAutenticacion = "";
    private Usuario user;

    public SendMailGmail() {
        user = new Usuario();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.user", correoEnvia);
        properties.put("mail.password", claveCorreo);
    }

    public void send(String host, String port, final String userName, final String password, String toAddress,
            String subject, String htmlBody, Map<String, String> mapInlineImages)
            throws AddressException, MessagingException {
        // sets SMTP server properties

        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };
        Session session = Session.getInstance(properties, auth);

        // creates a new e-mail message
        Message msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(userName));
        InternetAddress[] toAddresses = {new InternetAddress(toAddress)};
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());

        // creates message part
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(htmlBody, "text/html");

        // creates multi-part
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        // adds inline image attachments
        if (mapInlineImages != null && mapInlineImages.size() > 0) {
            Set<String> setImageID = mapInlineImages.keySet();

            for (String contentId : setImageID) {
                MimeBodyPart imagePart = new MimeBodyPart();
                imagePart.setHeader("Content-ID", "<" + contentId + ">");
                imagePart.setDisposition(MimeBodyPart.INLINE);

                String imageFilePath = mapInlineImages.get(contentId);
                try {
                    imagePart.attachFile(imageFilePath);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                multipart.addBodyPart(imagePart);
            }
        }

        msg.setContent(multipart);

        Transport.send(msg);
    }

    public void enviarMail() {
        // SMTP info
        String host = properties.getProperty("mail.smtp.host");
        String port = properties.getProperty("mail.smtp.port");

        // message info
        String mailTo = user.getUsEmail();
        String subject = "INICIO DE SESIÓN TDA APPTECHNOLOGY WEB";
        StringBuffer body = new StringBuffer("<html><br>");
        body.append("<img src=\"cid:image1\" style=\"width:100%;height:100px\"/>");
        body.append(
                "<form style=\"padding: 0px 14px 0px 14px;border-bottom:none !important;border-top:none !important;border: solid 1px red;\"> Estimado Usuario.<br><br>");
        body.append(user.getUsUsername()+ "<br>");
        body.append("Inicio de sesión a TDA APPTECHNOLOGY Web:<br><br>");
        body.append("Fecha: " + new Date() + "<br>");
        body.append("SEGUNDO FACTOR DE AUTENTICACIÓN ES: " + factorAutenticacion + " <br><br><br>");
        body.append("Gracias por utilizar nuestros servicios.<br><br><br>");
        body.append("Atentamente,<br>");
        body.append("TDA APPTECHNOLOGY.</form>");
        body.append("<img src=\"cid:image2\" style=\"width:100%;height:130px\" /><br>");
        body.append("</html>");

        // inline images
        Map<String, String> inlineImages = new HashMap<String, String>();
        inlineImages.put("image1", "C:/Users/santi/Downloads/mail.png");
        inlineImages.put("image2", "C:/Users/santi/Downloads/email.png");

        try {
            send(host, port, correoEnvia, claveCorreo, mailTo, subject, body.toString(), inlineImages);
            System.out.println("Email sent.");
        } catch (Exception ex) {
            System.out.println("Could not send email.");
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SendMailGmail correoTexto = new SendMailGmail();

        correoTexto.enviarMail();

    }

    /**
     * SETTERS Y GETTERS *
     */
    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public String getFactorAutenticacion() {
        return factorAutenticacion;
    }

    public void setFactorAutenticacion(String factorAutenticacion) {
        this.factorAutenticacion = factorAutenticacion;
    }

}
