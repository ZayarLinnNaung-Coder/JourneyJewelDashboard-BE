package rootstudio.development.tms.global.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class LocalizationConfig {

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages"); // Base name of the property files
        messageSource.setDefaultEncoding("UTF-8"); // Character encoding
        messageSource.setUseCodeAsDefaultMessage(true); // Use key as fallback
        return messageSource;
    }
}
