package rootstudio.development.dms.features.mail.service.impl;

import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import rootstudio.development.dms.features.mail.constant.EmailConstant;
import rootstudio.development.dms.features.mail.service.MailService;
import rootstudio.development.dms.features.mail.template.factory.MailTemplate;
import rootstudio.development.dms.global.utils.EmailUtil;

import java.util.Properties;

@Service
@AllArgsConstructor
@Async
public class MailServiceImpl implements MailService {
    
    private final EmailUtil emailUtil;

    @Override
    public void sendEmail(String toEmail, String subject, MailTemplate template) {

        try {
            send(toEmail, subject, template);
        }catch (Exception e){
            try {
                send(toEmail, subject, template);
            }catch (Exception ex){
                send(toEmail, subject, template);
            }
        }

    }

    private void send(String toEmail, String subject, MailTemplate template){
        final String fromEmail = EmailConstant.SENDER_MAIL;
        final String password = EmailConstant.SENDER_MAIL_PASSWORD;

        Properties props = getProperties();

        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };

        Session session = Session.getInstance(props, auth);
        emailUtil.sendEmail(session, toEmail, subject, template);
    }

    private Properties getProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.host", EmailConstant.HOST); //SMTP Host
        props.put("mail.smtp.socketFactory.port", EmailConstant.PORT); //SSL Port
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
        props.put("mail.smtp.port", EmailConstant.PORT); //SMTP Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
        props.put("mail.debug", "true");
        return props;
    }

}
