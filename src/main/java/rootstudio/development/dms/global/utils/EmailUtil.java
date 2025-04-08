package rootstudio.development.dms.global.utils;

import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import rootstudio.development.dms.features.mail.constant.EmailConstant;
import rootstudio.development.dms.features.mail.template.factory.MailTemplate;

@Component
@AllArgsConstructor
public class EmailUtil {

    private final TemplateEngine templateEngine;

    public void sendEmail(Session session, String toEmail, String subject, MailTemplate template){
        try
        {
            MimeMessage msg = new MimeMessage(session);

            MimeMessageHelper helper = new MimeMessageHelper(msg, "UTF-8");
            helper.setFrom(EmailConstant.SENDER_MAIL);
            helper.setTo(toEmail);
            helper.setSubject(subject);

            /* Convert to htmlContent */
            String htmlContent = templateEngine.process(template.getTemplate(), template.getContext());
            helper.setText(htmlContent, true);

            Transport.send(msg);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}