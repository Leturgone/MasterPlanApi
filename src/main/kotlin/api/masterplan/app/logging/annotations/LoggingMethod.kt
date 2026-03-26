package api.masterplan.app.logging.annotations

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class LoggingMethod(
    val moduleName: String = "",
)
