package api.masterplan.app.plansModule.application.command

import api.masterplan.app.plansModule.domain.model.value.ExecutorId

data class SortAssignedTasksByEndDateCommand(
    val executorId: ExecutorId
)