package api.masterplan.app.export

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class ExportDisplayName(val value: String)
