package custom.logger.logger;

import custom.logger.config.properties.LoggerProperties;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;

@Aspect
public class MethodLogger {

    private final LoggerProperties loggerProperties;
    private final Logger log = LoggerFactory.getLogger(MethodLogger.class);

    public MethodLogger(@Qualifier("custom-logger.methods-custom.logger.config.properties.MethodLoggerProperties") LoggerProperties loggerProperties) {
        this.loggerProperties = loggerProperties;
    }

    private boolean isLogLevel(String level) {
        return loggerProperties.getLevel().equalsIgnoreCase(level);
    }

    @Before("@within(custom.logger.annotation.CustomLogging) || @annotation(custom.logger.annotation.CustomLogging)")
    public void logBefore(JoinPoint joinPoint) {
        if (isLogLevel("info")) {
            log.info("Вызов метода: {}", joinPoint.getSignature().getName());
        }
    }

    @AfterReturning(pointcut = "@within(custom.logger.annotation.CustomLogging) || @annotation(custom.logger.annotation.CustomLogging)", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        if (isLogLevel("debug")) {
            log.debug("Метод {} завершился успешно с результатом: {}", joinPoint.getSignature().getName(), result);
        }
    }

    @AfterThrowing(pointcut = "@within(custom.logger.annotation.CustomLogging) || @annotation(custom.logger.annotation.CustomLogging)", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        if (isLogLevel("error")) {
            log.error("Метод {} завершился с ошибкой: {}", joinPoint.getSignature().getName(), error.getMessage(), error);
        }
    }

    @Around("@within(custom.logger.annotation.CustomLogging) || @annotation(custom.logger.annotation.CustomLogging)")
    public Object logAround(ProceedingJoinPoint joinPoint) {
        long startTime = System.currentTimeMillis();
        Object result = null;
        try {
            result = joinPoint.proceed();
            long totalTime = System.currentTimeMillis() - startTime;

            if (isLogLevel("debug")) {
                log.debug("Метод {} выполнен за {} миллисекунд", joinPoint.getSignature().getName(), totalTime);
            }

            if (totalTime > 4000 && isLogLevel("warn")) {
                log.warn("Долгое выполнение метода {}: {} ms", joinPoint.getSignature().getName(), totalTime);
            }
        } catch (Throwable throwable) {
            if (isLogLevel("error")) {
                log.error("Ошибка при выполнении метода {}: {}", joinPoint.getSignature().getName(), throwable.getMessage(), throwable);
            }
        }
        return result;
    }
}
