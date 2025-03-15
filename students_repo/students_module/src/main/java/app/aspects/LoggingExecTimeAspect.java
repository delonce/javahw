package app.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.logging.Logger;

@Aspect
@Component
public class LoggingExecTimeAspect {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Pointcut("@annotation(LogExecTime)")
    public void callLoggingTime() {}

    @Around("callLoggingTime()")
    public Object aroundLoggingExecTime(ProceedingJoinPoint jp) throws Throwable {
        StopWatch watch = new StopWatch(jp.toString());
        try {
            watch.start(jp.toShortString());
            return jp.proceed();
        } finally {
            watch.stop();
            logger.info(watch.prettyPrint());
        }
    }
}