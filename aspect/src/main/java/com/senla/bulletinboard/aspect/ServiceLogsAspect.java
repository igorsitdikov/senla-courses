package com.senla.bulletinboard.aspect;

import com.senla.bulletinboard.exception.BusinessLogicException;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Aspect
@Log4j2
@Component
public class ServiceLogsAspect {

    private static final String METHOD_WITH_ARGS = "Call method: [%s] with args: [%s]";

    @Pointcut("execution(* com.senla.bulletinboard.service.*.*(..))")
    public void serviceMethods() {
    }

    @Before("serviceMethods()")
    public void logMethodCall(JoinPoint jp) {
        String methodNameWithClass = String.format("%s.%s",
                jp.getTarget().getClass().getName(),
                jp.getSignature().getName());
        Object[] signatureArgs = jp.getArgs();
        List<String> args = new ArrayList<>();
        for (Object signatureArg : signatureArgs) {
            String arg = String.format("arg: %s", signatureArg);
            args.add(arg);
        }
        String message = String.format(
                METHOD_WITH_ARGS,
                methodNameWithClass,
                String.join(", ", args));
        log.debug(message);
    }

    @AfterThrowing(pointcut = "serviceMethods()", throwing = "ex")
    public void catchAllErrors(BusinessLogicException ex) {
        log.error(ex.getMessage());
    }
}
