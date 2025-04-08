package rootstudio.development.tms.features.mail.template;

import org.thymeleaf.context.Context;
import rootstudio.development.tms.features.mail.template.factory.MailTemplate;

public class InviteTeammateTemplate extends MailTemplate {

    public InviteTeammateTemplate(Context context){
        super(context);
    }

    @Override
    public String getTemplate() {
        return "InviteTeammateTemplate";
    }

}
