package api.masterplan.app.plansModule.application.command

import api.masterplan.app.plansModule.domain.model.value.PlanDirectorId
import api.masterplan.app.plansModule.domain.model.value.PlanStatus

data class FilterDirPlansByStatusCommand(
    val directorId: PlanDirectorId,
    val status: PlanStatus
)