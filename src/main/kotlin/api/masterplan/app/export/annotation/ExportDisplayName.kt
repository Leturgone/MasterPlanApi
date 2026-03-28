package api.masterplan.app.export.annotation

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class ExportDisplayName(val value: String)