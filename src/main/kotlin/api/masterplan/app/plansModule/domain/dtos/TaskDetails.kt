package api.masterplan.app.plansModule.domain.dtos

import api.masterplan.app.plansModule.domain.model.value.ExecutorId
import api.masterplan.app.plansModule.domain.model.value.PlanId
import api.masterplan.app.plansModule.domain.model.value.TaskDate
import api.masterplan.app.plansModule.domain.model.value.TaskDescription
import api.masterplan.app.plansModule.domain.model.value.TaskDocumentId
import api.masterplan.app.plansModule.domain.model.value.TaskId
import api.masterplan.app.plansModule.domain.model.value.TaskStatus
import api.masterplan.app.plansModule.domain.model.value.TaskTitle
import api.masterplan.app.plansModule.domain.model.value.TaskUrgency

data class TaskDetails(
    val id: TaskId,
    val title: TaskTitle,
    val description: TaskDescription,
    val endDate: TaskDate,
    val status: TaskStatus,
    val planId: PlanId,
    val documentId: TaskDocumentId? = null,
    val urgency: TaskUrgency,
    val executorsIds: MutableList<ExecutorId>
)
