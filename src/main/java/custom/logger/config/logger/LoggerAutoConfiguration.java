package custom.logger.config.logger;

import custom.logger.config.properties.MethodLoggerProperties;
import custom.logger.logger.MethodLogger;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@ConditionalOnProperty(name = "custom-logger.methods.enabled", havingValue = "true", matchIfMissing = true)
@AutoConfiguration
@EnableConfigurationProperties(MethodLoggerProperties.class)
public class LoggerAutoConfiguration {

    @Bean
    public MethodLogger methodLogger(MethodLoggerProperties methodLoggerProperties) {
        return new MethodLogger(methodLoggerProperties);
    }

}
