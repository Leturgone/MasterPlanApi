package api.masterplan.app.logging

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.*
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
@Aspect
class LoggingAspect {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Pointcut("@annotation(LoggingMethod)")
    fun logPointcut() {}

    private fun getModule(methodSignature: MethodSignature): String{
        val method = methodSignature.method
        val annotation = method.getAnnotation(LoggingMethod::class.java)
        val moduleName = annotation.moduleName
        return moduleName
    }

    @Before("logPointcut()")
    fun logBefore(joinPoint: JoinPoint) {
        val methodSignature = joinPoint.signature as MethodSignature
        val moduleName = getModule(methodSignature)
        val methodName = methodSignature.name
        val methodArgs = joinPoint.args.joinToString(", ") { it?.toString()?:"null" }
        logger.info("Module {} : Method {} is called with args: {}", moduleName,methodName, methodArgs)
    }

    @AfterReturning(pointcut = "logPointcut()", returning = "result")
    fun logAfterReturning(joinPoint: JoinPoint,result: Any?) {
        val methodSignature = joinPoint.signature as MethodSignature
        val moduleName = getModule(methodSignature)
        val methodName = methodSignature.name
        logger.info("Module {} : Method {} returned with result: {}", moduleName, methodName, result)
    }

    @AfterThrowing(pointcut = "logPointcut()", throwing = "exception")
    fun logAfterThrowing(joinPoint: JoinPoint,exception: Exception) {
        val methodSignature = joinPoint.signature as MethodSignature
        val moduleName = getModule(methodSignature)
        val methodName = methodSignature.name
        val exceptionMessage = exception.message
        logger.warn("Module $moduleName : Method $methodName thrown an exception: $exception: $exceptionMessage")
    }

}