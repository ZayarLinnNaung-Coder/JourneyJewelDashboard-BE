package root.development.tms.features.mail.template.factory;

import org.thymeleaf.context.Context;
import root.development.tms.features.mail.template.InviteTeammateTemplate;
import root.development.tms.features.mail.template.OtpMailTemplate;

public class MailTemplateFactory {

    public static MailTemplate produceInviteTeammateTemplate(Context context){
        return new InviteTeammateTemplate(context);
    }
    public static MailTemplate produceOtpMailTemplate(Context context) {
        return new OtpMailTemplate(context);
    }
//    public static MailTemplate produceAccountActivateTemplate(Context context){
//        return new AccountActivateTemplate(context);
//    }

}
