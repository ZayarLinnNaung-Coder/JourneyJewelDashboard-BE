package root.development.tms.global.utils;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MessageBundle {

    private final HttpRequestUtils httpRequestUtils;
    private final MessageSource messageSource;

    public String getMessage(String messageCode){
        return messageSource.getMessage(messageCode, null, httpRequestUtils.getLocale());
    }

}
