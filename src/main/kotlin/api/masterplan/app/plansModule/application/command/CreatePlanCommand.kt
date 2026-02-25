package api.masterplan.app.plansModule.application.command

import api.masterplan.app.plansModule.application.dto.PlanFile
import api.masterplan.app.plansModule.domain.model.value.PlanDate
import api.masterplan.app.plansModule.domain.model.value.PlanDescription
import api.masterplan.app.plansModule.domain.model.value.PlanDirectorId
import api.masterplan.app.plansModule.domain.model.value.PlanId
import api.masterplan.app.plansModule.domain.model.value.PlanTitle

data class CreatePlanCommand(
    val id: PlanId? = null,
    val title: PlanTitle,
    val description: PlanDescription,
    val startDate: PlanDate? = null,
    val endDate: PlanDate,
    val directorId: PlanDirectorId,
    val document: PlanFile? = null
)