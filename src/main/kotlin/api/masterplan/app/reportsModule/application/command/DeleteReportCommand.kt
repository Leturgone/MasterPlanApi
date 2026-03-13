package api.masterplan.app.reportsModule.application.command

import api.masterplan.app.reportsModule.domain.models.value.ReportId
import api.masterplan.app.reportsModule.domain.models.value.ReportType

data class DeleteReportCommand(
    val reportId: ReportId,
    val reportType: ReportType,
)