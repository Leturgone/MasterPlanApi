package api.masterplan.app.logging.aspects

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterThrowing
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
@Aspect
class LoggingNotificationAspect {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Pointcut("@annotation(api.masterplan.app.logging.annotations.LoggingNotificationMethod")
    fun logNotificationPointcut() {}

    @AfterThrowing(pointcut = "logNotificationPointcut()", throwing = "exception")
    fun logAfterDatabaseThrowing(joinPoint: JoinPoint,exception: Exception) {
        val methodSignature = joinPoint.signature as MethodSignature
        val methodName = methodSignature.name
        val exceptionMessage = exception.message
        logger.debug(
            "Module {}: Notification Method {} thrown an exception: {}: {}",
            "notificationModule",
            methodName,
            exception,
            exceptionMessage
        )
        throw exception
    }
}