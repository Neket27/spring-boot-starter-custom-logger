package custom.logger.logger;

import custom.logger.config.properties.HttpLoggerProperties;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.event.Level;

@Aspect
public class HttpLogger extends LoggerLevel {

    public HttpLogger(HttpLoggerProperties loggerProperties) {
        super(loggerProperties);
    }

    @Before("@within(org.springframework.web.bind.annotation.RequestMapping) || @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void logBefore(JoinPoint joinPoint) {
        // Логируем только если уровень выше INFO
        logAtLevel(Level.INFO, "Вызов http метода: {}", joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "@within(org.springframework.web.bind.annotation.RequestMapping) || @annotation(org.springframework.web.bind.annotation.RequestMapping)", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        if (result == null)
            logAtLevel(Level.WARN, "Метод http {} вернул null", joinPoint.getSignature().getName());
        else
            logAtLevel(Level.DEBUG, "Метод http {} завершился успешно. Результат: {}", joinPoint.getSignature().getName(), result);
    }

    @AfterThrowing(pointcut = "@within(org.springframework.web.bind.annotation.RequestMapping) || @annotation(org.springframework.web.bind.annotation.RequestMapping)", throwing = "throwable")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable throwable) {
        logAtLevel(Level.ERROR, "Метод http {} завершился с ошибкой: {}", joinPoint.getSignature().getName(), throwable.getMessage());
    }
}

