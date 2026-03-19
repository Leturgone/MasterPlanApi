package api.masterplan.app.reportsModule.application.command

import api.masterplan.app.reportsModule.domain.models.value.ReportStatus
import api.masterplan.app.reportsModule.domain.models.value.ReportEmployeeId
import api.masterplan.app.reportsModule.domain.models.value.ReportType

data class FilterByStatusCreatedReportsCommand(
    val employeeId: ReportEmployeeId,
    val status: ReportStatus,
    val reportType: ReportType
)