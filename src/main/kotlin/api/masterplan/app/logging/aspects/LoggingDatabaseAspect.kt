package api.masterplan.app.logging.aspects

import api.masterplan.app.logging.annotations.LoggingDatabaseMethod
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterThrowing
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
@Aspect
class LoggingDatabaseAspect {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Pointcut("@annotation(api.masterplan.app.logging.annotations.LoggingDatabaseMethod)")
    fun logDatabasePointcut() {}

    private fun getModule(methodSignature: MethodSignature): String{
        val method = methodSignature.method
        val annotation = method.getAnnotation(LoggingDatabaseMethod::class.java)
        val moduleName = annotation.moduleName
        return moduleName
    }

    @AfterThrowing(pointcut = "logDatabasePointcut()", throwing = "exception")
    fun logAfterDatabaseThrowing(joinPoint: JoinPoint,exception: Exception) {
        val methodSignature = joinPoint.signature as MethodSignature
        val moduleName = getModule(methodSignature)
        val methodName = methodSignature.name
        val exceptionMessage = exception.message
        logger.debug(
            "Module {}: Database Method {} thrown an exception: {}: {}",
            moduleName,
            methodName,
            exception,
            exceptionMessage
        )
        throw exception
    }
}