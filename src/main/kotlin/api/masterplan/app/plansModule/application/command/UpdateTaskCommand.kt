package api.masterplan.app.plansModule.application.command

import api.masterplan.app.plansModule.application.dto.TaskFile
import api.masterplan.app.plansModule.domain.model.entity.Task
import api.masterplan.app.plansModule.domain.model.value.TaskId

data class UpdateTaskCommand(
    val taskId: TaskId,
    val updatedTask: Task,
    val document: TaskFile? = null
)