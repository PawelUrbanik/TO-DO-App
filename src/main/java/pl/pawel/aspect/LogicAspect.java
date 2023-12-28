package pl.pawel.aspect;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogicAspect {

    public static final Logger LOGGER = LoggerFactory.getLogger(LogicAspect.class);
    private final Timer projectCreateGroupTimer;

    public LogicAspect(MeterRegistry meterRegistry) {
        this.projectCreateGroupTimer = meterRegistry.timer("service.project.create.group");
    }


    @Pointcut("execution(* pl.pawel.service.ProjectService.createGroup(..))")
    void projectServiceCreateGroup() {

    }
    @Before("projectServiceCreateGroup()")
    void beforeProjectCreateGroup(JoinPoint joinPoint) {
        LOGGER.info("Before {} with {}", joinPoint.getSignature().getName(), joinPoint.getArgs());
    }

    @Around("projectServiceCreateGroup()")
    Object aroundProjectCreateGroup(ProceedingJoinPoint jp) {
        return projectCreateGroupTimer.record(() ->
        {
            try {
                return jp.proceed();
            } catch (Throwable e) {
                if (e instanceof RuntimeException) {
                    throw (RuntimeException) e;
                }
                throw new RuntimeException(e);
            }
        });
    }

}
