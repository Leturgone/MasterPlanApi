package api.masterplan.app.plansModule.application.command

import api.masterplan.app.plansModule.domain.model.value.TaskId
import api.masterplan.app.plansModule.domain.model.value.TaskStatus

data class ChangeTaskStatusCommand(
    val taskId: TaskId,
    val status: TaskStatus
)