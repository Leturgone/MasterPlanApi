package api.masterplan.app.reportsModule.application.command

import api.masterplan.app.reportsModule.domain.models.value.ReportEmployeeId

data class GetCreatedTaskReportsCommand(
    val employeeId: ReportEmployeeId
)