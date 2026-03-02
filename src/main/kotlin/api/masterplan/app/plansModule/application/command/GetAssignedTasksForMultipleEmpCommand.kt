package api.masterplan.app.plansModule.application.command

import api.masterplan.app.plansModule.domain.model.value.ExecutorId

data class GetAssignedTasksForMultipleEmpCommand(
    val executorIds: Set<ExecutorId>
)