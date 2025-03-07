package custom.logger.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "custom-logger.methods")
public class MethodLoggerProperties extends LoggerProperties {

    private boolean enabled = true;
    private String logLevel = "INFO";

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String getLevel() {
        return logLevel;
    }

    @Override
    public void setLevel(String logLevel) {
        this.logLevel = logLevel;
    }
}
