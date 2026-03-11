package api.masterplan.app.reportsModule.application.command

import api.masterplan.app.reportsModule.domain.models.value.ReportEmployeeId

class GetSubordinatesTaskReportsCommand(
    val directorId: ReportEmployeeId
)