package rootstudio.development.dms.features.mail.service;

import rootstudio.development.dms.features.mail.template.factory.MailTemplate;

public interface MailService {
    void sendEmail(String to, String title, MailTemplate template);
}
