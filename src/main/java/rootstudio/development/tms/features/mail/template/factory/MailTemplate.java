package rootstudio.development.tms.features.mail.template.factory;

import org.thymeleaf.context.Context;

public abstract class MailTemplate {

    private Context context;

    public MailTemplate(Context context){
        this.context = context;
    }

    public abstract String getTemplate();

    public Context getContext(){
        return context;
    }
}