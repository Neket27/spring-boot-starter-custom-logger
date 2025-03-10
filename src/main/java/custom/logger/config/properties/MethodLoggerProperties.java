package custom.logger.config.properties;

import org.slf4j.event.Level;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "custom-logger.methods")
public class MethodLoggerProperties extends LoggerProperties {

    private boolean enabled = true;
    private Level logLevel = Level.INFO;

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public Level getLevel() {
        return logLevel;
    }

    @Override
    public void setLevel(Level logLevel) {
        this.logLevel = logLevel;
    }
}
