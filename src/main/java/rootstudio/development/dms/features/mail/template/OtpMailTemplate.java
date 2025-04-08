package rootstudio.development.dms.features.mail.template;
import org.thymeleaf.context.Context;
import rootstudio.development.dms.features.mail.template.factory.MailTemplate;

public class OtpMailTemplate extends MailTemplate {

    public OtpMailTemplate(Context context) {
        super(context);
    }

    @Override
    public String getTemplate() {
        return "otp-template";
    }
}