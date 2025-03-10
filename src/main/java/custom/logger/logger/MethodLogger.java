package custom.logger.logger;

import custom.logger.config.properties.MethodLoggerProperties;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.event.Level;

@Aspect
public class MethodLogger extends LoggerLevel {

    public MethodLogger(MethodLoggerProperties loggerProperties) {
        super(loggerProperties);
    }

    @Before("@within(custom.logger.annotation.CustomLogging) || @annotation(custom.logger.annotation.CustomLogging)")
    public void logBefore(JoinPoint joinPoint) {
        logAtLevel(Level.INFO, "Вызов метода: {}", joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "@within(custom.logger.annotation.CustomLogging) || @annotation(custom.logger.annotation.CustomLogging)", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logAtLevel(Level.DEBUG, "Метод {} завершился успешно с результатом: {}", joinPoint.getSignature().getName(), result);
    }

    @AfterThrowing(pointcut = "@within(custom.logger.annotation.CustomLogging) || @annotation(custom.logger.annotation.CustomLogging)", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        logAtLevel(Level.ERROR, "Метод {} завершился с ошибкой: {}", joinPoint.getSignature().getName(), error.getMessage(), error);
    }

    @Around("@within(custom.logger.annotation.CustomLogging) || @annotation(custom.logger.annotation.CustomLogging)")
    public Object logAround(ProceedingJoinPoint joinPoint) {
        long startTime = System.currentTimeMillis();
        Object result = null;
        try {
            result = joinPoint.proceed();
            long totalTime = System.currentTimeMillis() - startTime;

            logAtLevel(Level.DEBUG, "Метод {} выполнен за {} миллисекунд", joinPoint.getSignature().getName(), totalTime);

            if (totalTime > 4000) {
                logAtLevel(Level.WARN, "Долгое выполнение метода {}: {} ms", joinPoint.getSignature().getName(), totalTime);
            }
        } catch (Throwable throwable) {
            logAtLevel(Level.ERROR, "Ошибка при выполнении метода {}: {}", joinPoint.getSignature().getName(), throwable.getMessage(), throwable);
        }
        return result;
    }
}
