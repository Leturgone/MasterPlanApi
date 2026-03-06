package api.masterplan.app.plansModule.application.command

import api.masterplan.app.plansModule.domain.model.value.PlanId

data class DeletePlanCommand(
    val planId: PlanId,
)