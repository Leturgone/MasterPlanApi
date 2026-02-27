package api.masterplan.app.plansModule.application.command

import api.masterplan.app.plansModule.application.dto.PlanFile
import api.masterplan.app.plansModule.domain.model.entity.Plan
import api.masterplan.app.plansModule.domain.model.value.PlanId

data class UpdatePlanCommand(
    val planId: PlanId,
    val updatedPlan: Plan,
    val document: PlanFile? = null
)