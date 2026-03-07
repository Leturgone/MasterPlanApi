package api.masterplan.app.reportsModule.domain.models.value

import api.masterplan.app.reportsModule.domain.exceptions.ReportException

@JvmInline
value class PlanReportTitle private constructor(val value: String) {
    companion object {
        fun validate(title: String): PlanReportTitle {
            try {
                require(title.isNotBlank()) { "Title cant be blank" }
                require(title.length <= 100) { "Title too long" }
            } catch (e: IllegalArgumentException) {
                throw ReportException.InvalidReportTitle(e.message)
            }
            return PlanReportTitle(title)
        }
    }
}