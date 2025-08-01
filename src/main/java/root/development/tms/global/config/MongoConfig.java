package root.development.tms.global.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import root.development.tms.global.document.BaseDocument;
import root.development.tms.global.utils.HttpRequestUtils;

import java.util.Optional;

@Configuration
@EnableMongoAuditing
@AllArgsConstructor
public class MongoConfig {

    private final HttpRequestUtils httpRequestUtils;

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.ofNullable(httpRequestUtils.getCurrentLoginId());
    }

    @EventListener
    public void onBeforeConvert(BeforeConvertEvent<BaseDocument> event) {
        BaseDocument document = event.getSource();
        String accountId = httpRequestUtils.getAccountIdFromHeader();

        if (document.getAccountId() == null) {
            document.setAccountId(accountId);
        }
    }

}
