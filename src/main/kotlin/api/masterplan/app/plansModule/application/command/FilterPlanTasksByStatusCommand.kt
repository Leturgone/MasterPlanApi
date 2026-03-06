package api.masterplan.app.plansModule.application.command

import api.masterplan.app.plansModule.domain.model.value.PlanId
import api.masterplan.app.plansModule.domain.model.value.TaskStatus

data class FilterPlanTasksByStatusCommand(
    val planId: PlanId,
    val taskStatus: TaskStatus
)