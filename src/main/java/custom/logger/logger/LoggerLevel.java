package custom.logger.logger;

import custom.logger.config.properties.LoggerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

class LoggerLevel {

    private final LoggerProperties loggerProperties;
    private final Logger log = LoggerFactory.getLogger(HttpLogger.class);

    protected LoggerLevel(LoggerProperties loggerProperties) {
        this.loggerProperties = loggerProperties;
    }

    protected void logAtLevel(Level level, String message, Object... args) {
        if (!isLevelEnabled(level)) return; // Если уровень ниже текущего - не логируем

        log.atLevel(level).log(message, args);
    }

    private boolean isLevelEnabled(Level level) {
        Level currentLevel = loggerProperties.getLevel();
        return switch (currentLevel) {
            case TRACE -> true;  // Все уровни разрешены
            case DEBUG -> level != Level.TRACE; // Разрешены DEBUG, INFO, WARN, ERROR
            case INFO -> level == Level.INFO || level == Level.WARN || level == Level.ERROR;
            case WARN -> level == Level.WARN || level == Level.ERROR;
            case ERROR -> level == Level.ERROR;
        };
    }
}
