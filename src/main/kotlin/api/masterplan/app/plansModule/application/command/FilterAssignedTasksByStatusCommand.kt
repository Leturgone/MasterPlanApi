package api.masterplan.app.plansModule.application.command

import api.masterplan.app.plansModule.domain.model.value.ExecutorId
import api.masterplan.app.plansModule.domain.model.value.TaskStatus

data class FilterAssignedTasksByStatusCommand(
    val executorId: ExecutorId,
    val taskStatus: TaskStatus
)