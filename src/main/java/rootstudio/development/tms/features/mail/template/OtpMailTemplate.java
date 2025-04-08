package rootstudio.development.tms.features.mail.template;
import org.thymeleaf.context.Context;
import rootstudio.development.tms.features.mail.template.factory.MailTemplate;

public class OtpMailTemplate extends MailTemplate {

    public OtpMailTemplate(Context context) {
        super(context);
    }

    @Override
    public String getTemplate() {
        return "otp-template";
    }
}