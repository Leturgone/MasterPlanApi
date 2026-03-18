package api.masterplan.app.reportsModule.application.command

import api.masterplan.app.reportsModule.domain.models.value.ReportId
import api.masterplan.app.reportsModule.domain.models.value.ReportStatus
import api.masterplan.app.reportsModule.domain.models.value.ReportType

data class ChangeReportStatusCommand(
    val reportId: ReportId,
    val status: ReportStatus,
    val reportType: ReportType
)