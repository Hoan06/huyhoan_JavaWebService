package ra.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class MedicationAspect {

    @Before("execution(* ra.service.MedicationService.save(..))")
    public void beforeSave(JoinPoint joinPoint) {
        log.info("Request tới : " +  joinPoint.getSignature().getName());
    }

    @Before("execution(* ra.service.MedicationService.updateFull(..))")
    public void beforeUpdateFull(JoinPoint joinPoint) {
        log.info("Request tới : " +  joinPoint.getSignature().getName());
    }

    @Before("execution(* ra.service.MedicationService.updatePart(..))")
    public void beforeUpdatePart(JoinPoint joinPoint) {
        log.info("Request tới : " +  joinPoint.getSignature().getName());
    }
}
