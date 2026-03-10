package api.masterplan.app.reportsModule.application.command

import api.masterplan.app.reportsModule.domain.models.value.TaskReportId

data class DeleteTaskReportCommand(
    val reportId: TaskReportId
)