package api.masterplan.app.plansModule.application.command

import api.masterplan.app.plansModule.domain.model.value.TaskId

data class DeleteTaskFromPlanCommand (
    val taskId: TaskId
)
