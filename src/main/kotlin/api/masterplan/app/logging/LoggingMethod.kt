package api.masterplan.app.logging

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class LoggingMethod(
    val value: String = "",
)
