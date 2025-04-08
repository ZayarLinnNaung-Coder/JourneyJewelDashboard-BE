package rootstudio.development.dms.global.utils;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

@Component
@AllArgsConstructor
public class MessageBundle {

    private final HttpRequestUtils httpRequestUtils;
    private final MessageSource messageSource;

    public String getMessage(String messageCode){
        return messageSource.getMessage(messageCode, null, httpRequestUtils.getLocale());
    }

}
