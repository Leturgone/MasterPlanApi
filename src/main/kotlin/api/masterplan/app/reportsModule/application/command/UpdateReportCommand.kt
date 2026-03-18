package api.masterplan.app.reportsModule.application.command

import api.masterplan.app.reportsModule.application.dto.ReportFile
import api.masterplan.app.reportsModule.domain.models.entity.Report
import api.masterplan.app.reportsModule.domain.models.value.ReportId
import api.masterplan.app.reportsModule.domain.models.value.ReportType

data class UpdateReportCommand(
    val reportId: ReportId,
    val updatedReport: Report,
    val reportType: ReportType,
    val document: ReportFile,
)