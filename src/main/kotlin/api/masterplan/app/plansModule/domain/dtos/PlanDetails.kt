package api.masterplan.app.plansModule.domain.dtos

import api.masterplan.app.plansModule.domain.model.entity.Task
import api.masterplan.app.plansModule.domain.model.value.PlanDate
import api.masterplan.app.plansModule.domain.model.value.PlanDescription
import api.masterplan.app.plansModule.domain.model.value.PlanDirectorId
import api.masterplan.app.plansModule.domain.model.value.PlanDocumentId
import api.masterplan.app.plansModule.domain.model.value.PlanId
import api.masterplan.app.plansModule.domain.model.value.PlanStatus
import api.masterplan.app.plansModule.domain.model.value.PlanTitle

data class PlanDetails(
    val id: PlanId,
    val title: PlanTitle,
    val description: PlanDescription,
    val tasks: MutableList<Task>,
    val startDate: PlanDate,
    val endDate: PlanDate,
    val status: PlanStatus,
    val directorId: PlanDirectorId? = null,
    val documentId: PlanDocumentId? = null
)