package api.masterplan.app.logging

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class LoggingDatabaseMethod(
    val moduleName: String = "",
)
