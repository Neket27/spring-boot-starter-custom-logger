package custom.logger.logger;

import custom.logger.config.properties.LoggerProperties;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;

@Aspect
public class HttpLogger {

    private final Logger log = LoggerFactory.getLogger(HttpLogger.class);
    private final LoggerProperties loggerProperties;

    public HttpLogger(@Qualifier("custom-logger.http-custom.logger.config.properties.HttpLoggerProperties") LoggerProperties loggerProperties) {
        this.loggerProperties = loggerProperties;
    }

    private boolean isLogLevel(String level) {
        return loggerProperties.getLevel().equalsIgnoreCase(level);
    }


    @Before("@within(org.springframework.web.bind.annotation.RequestMapping) || @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void logBefore(JoinPoint joinPoint) {
        if (isLogLevel("info")) {
            log.info("Вызов http метода: {}", joinPoint.getSignature().getName());
        }
    }

    @AfterReturning(pointcut = "@within(org.springframework.web.bind.annotation.RequestMapping) || @annotation(org.springframework.web.bind.annotation.RequestMapping)", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        if (isLogLevel("debug")) {
            log.debug("Метод http {} завершился успешно. Результат: {}", joinPoint.getSignature().getName(), result);
        } else if (isLogLevel("warn") && result == null) {
            log.warn("Метод http {} вернул null", joinPoint.getSignature().getName());
        }
    }

    @AfterThrowing(pointcut = "@within(org.springframework.web.bind.annotation.RequestMapping) || @annotation(org.springframework.web.bind.annotation.RequestMapping)", throwing = "throwable")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable throwable) {
        if (isLogLevel("error"))
            log.error("Метод http {} завершился с ошибкой: {}", joinPoint.getSignature().getName(), throwable.getMessage());
    }

}
