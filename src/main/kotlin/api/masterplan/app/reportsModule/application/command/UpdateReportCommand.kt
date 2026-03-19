package api.masterplan.app.reportsModule.application.command

import api.masterplan.app.reportsModule.application.dto.ReportFile
import api.masterplan.app.reportsModule.domain.dtos.ReportUpdateData
import api.masterplan.app.reportsModule.domain.models.value.ReportId
import api.masterplan.app.reportsModule.domain.models.value.ReportType

data class UpdateReportCommand(
    val reportId: ReportId,
    val updatedData: ReportUpdateData,
    val reportType: ReportType,
    val document: ReportFile,
)