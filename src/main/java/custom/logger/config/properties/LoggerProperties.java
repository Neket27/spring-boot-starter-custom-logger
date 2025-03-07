package custom.logger.config.properties;

public abstract class LoggerProperties {

    public abstract boolean isEnabled();

    public abstract void setEnabled(boolean enabled);

    public abstract String getLevel();

    public abstract void setLevel(String level);
}
