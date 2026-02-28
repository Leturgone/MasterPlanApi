package api.masterplan.app.plansModule.application.command

import api.masterplan.app.plansModule.domain.model.value.PlanId
import api.masterplan.app.plansModule.domain.model.value.PlanStatus

data class ChangePlanStatusCommand(
    val planId: PlanId,
    val status: PlanStatus
)