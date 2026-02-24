package api.masterplan.app.plansModule.domain.model.entity

import api.masterplan.app.plansModule.domain.model.value.*

@ConsistentCopyVisibility
data class Plan private constructor(
    val id: PlanId,
    val title: PlanTitle,
    val description: PlanDescription,
    val tasks: MutableList<Task>,
    val startDate: PlanDate,
    val endDate: PlanDate,
    val status: PlanStatus,
    val directorId: PlanDirectorId? = null,
    val documentId: PlanDocumentId? = null
){
    companion object{
        fun create(id: PlanId? = null, title: PlanTitle, description: PlanDescription, startDate: PlanDate,
                   endDate: PlanDate, directorId: PlanDirectorId,
                   documentId: PlanDocumentId? = null): Plan{
            return Plan(
                id = id ?: PlanId.generate(),
                title = title,
                description = description,
                tasks = emptyList<Task>() as MutableList<Task>,
                startDate = startDate,
                endDate = endDate,
                status = PlanStatus.NOT_STARTED,
                directorId = directorId,
                documentId = documentId,
            )
        }
    }

    fun addTask(task: Task) = tasks.add(task)

    fun removeTask(task: Task) = tasks.remove(task)

    fun changePlanStatus(planStatus: PlanStatus) = planStatus

    fun isInProgress() = status == PlanStatus.IN_PROGRESS


    fun isNotInProgress() = status == PlanStatus.NOT_STARTED


    fun isCompleted() = status == PlanStatus.COMPLETED


}
