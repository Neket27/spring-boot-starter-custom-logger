package custom.logger.config.logger;

import custom.logger.config.properties.HttpLoggerProperties;
import custom.logger.logger.HttpLogger;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@ConditionalOnProperty(name = "custom-logger.http.enabled", havingValue = "true", matchIfMissing = true)
@AutoConfiguration
@EnableConfigurationProperties(HttpLoggerProperties.class)
public class HttpLoggerAutoConfiguration {

    @Bean
    public HttpLogger httpLogger(HttpLoggerProperties httpLoggerProperties) {
        return new HttpLogger(httpLoggerProperties);
    }
}
