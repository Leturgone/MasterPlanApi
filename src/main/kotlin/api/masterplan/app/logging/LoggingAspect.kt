package api.masterplan.app.logging

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.*
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
@Aspect
class LoggingAspect {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Pointcut("@annotation(LoggingMethod)")
    fun logPointcut() {}

    @Before("logPointcut()")
    fun logBefore(joinPoint: JoinPoint) {
        val methodName = joinPoint.signature.name
        val methodArgs = joinPoint.args.joinToString(", ") { it.toString() }
        logger.info("Method {} is called with args: {}", methodName, methodArgs)
    }

    @AfterReturning(pointcut = "logPointcut()", returning = "result")
    fun logAfterReturning(joinPoint: JoinPoint,result: Any?) {
        val methodName = joinPoint.signature.name
        logger.info("Method {} returned with result: {}", methodName, result)
    }

    @AfterThrowing(pointcut = "logPointcut()", throwing = "exception")
    fun logAfterThrowing(joinPoint: JoinPoint,exception: Exception) {
        val methodName = joinPoint.signature.name
        val exceptionMessage = exception.message
        logger.warn("Method {} thrown an exception: {}: {}".format(methodName, exception, exceptionMessage))
    }

}