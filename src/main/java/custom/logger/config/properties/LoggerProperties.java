package custom.logger.config.properties;

import org.slf4j.event.Level;

public abstract class LoggerProperties {

    public abstract boolean isEnabled();

    public abstract void setEnabled(boolean enabled);

    public abstract Level getLevel();

    public abstract void setLevel(Level level);
}
