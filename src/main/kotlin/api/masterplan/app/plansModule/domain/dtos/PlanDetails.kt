package api.masterplan.app.plansModule.domain.dtos

import api.masterplan.app.plansModule.domain.model.value.*

data class PlanDetails(
    val id: PlanId,
    val title: PlanTitle,
    val description: PlanDescription,
    val tasks: MutableList<TaskDetails>,
    val startDate: PlanDate,
    val endDate: PlanDate,
    val status: PlanStatus,
    val directorId: PlanDirectorId? = null,
    val documentId: PlanDocumentId? = null
)