package kz.iitu.spring_lab_01.aspect;

import kz.iitu.spring_lab_01.audit.RetryOnFailure;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(0)
public class RetryAspect {

    private static final Logger log = LoggerFactory.getLogger(RetryAspect.class);

    @Around("@annotation(retry)")
    public Object retry(ProceedingJoinPoint pjp, RetryOnFailure retry) throws Throwable {
        int attempts = retry.attempts();
        String method = pjp.getSignature().toShortString();
        Throwable last = null;

        for (int i = 1; i <= attempts; i++) {
            try {
                log.info("[RETRY] attempt {}/{} on {}", i, attempts, method);
                Object result = pjp.proceed();
                log.info("[RETRY] {} succeeded on attempt {}", method, i);
                return result;
            } catch (Throwable ex) {
                last = ex;
                log.warn("[RETRY] {} failed on attempt {}: {}", method, i, ex.getMessage());
            }
        }

        log.error("[RETRY] {} exhausted all {} attempts", method, attempts);
        throw last;
    }
}
