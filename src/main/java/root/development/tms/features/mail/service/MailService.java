package root.development.tms.features.mail.service;

import root.development.tms.features.mail.template.factory.MailTemplate;

public interface MailService {
    void sendEmail(String to, String title, MailTemplate template);
}
