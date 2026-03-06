package api.masterplan.app.plansModule.application.command

import api.masterplan.app.plansModule.application.dto.TaskFile
import api.masterplan.app.plansModule.domain.model.value.*

data class AddTaskToPlanCommand(
    val planId: PlanId,
    val taskId: TaskId? = null,
    val title: TaskTitle,
    val description: TaskDescription,
    val endDate: TaskDate,
    val document: TaskFile? = null,
    val executorsId: MutableList<ExecutorId>
)