package api.masterplan.app.plansModule.domain.model.entity

import api.masterplan.app.plansModule.domain.model.value.ExecutorId
import api.masterplan.app.plansModule.domain.model.value.PlanId
import api.masterplan.app.plansModule.domain.model.value.TaskDate
import api.masterplan.app.plansModule.domain.model.value.TaskDescription
import api.masterplan.app.plansModule.domain.model.value.TaskDocumentId
import api.masterplan.app.plansModule.domain.model.value.TaskId
import api.masterplan.app.plansModule.domain.model.value.TaskStatus
import api.masterplan.app.plansModule.domain.model.value.TaskTitle

@ConsistentCopyVisibility
data class Task private constructor(
    val id: TaskId,
    val title: TaskTitle,
    val description: TaskDescription,
    val endDate: TaskDate,
    val status: TaskStatus,
    val planId: PlanId,
    val documentId: TaskDocumentId? = null,
    val executorsIds: MutableList<ExecutorId>
){
    companion object{
        fun create(id: TaskId? = null, title: TaskTitle, description: TaskDescription, endDate: TaskDate,
                   planId: PlanId, documentId: TaskDocumentId? = null,
                   executorsId: MutableList<ExecutorId>): Task {
            return Task(
                id = id?: TaskId.generate(),
                title = title,
                description = description,
                endDate = endDate,
                status = TaskStatus.NOT_STARTED,
                planId = planId,
                documentId = documentId,
                executorsIds = executorsId,
            )
        }
    }


    fun addExecutor(executorId: ExecutorId) = executorsIds.add(executorId)


    fun removeExecutor(executorId: ExecutorId) = executorsIds.remove(executorId)

    fun changeTaskStatus(taskStatus: TaskStatus) = taskStatus

    fun isInProgress() = status == TaskStatus.IN_PROGRESS


    fun isNotInProgress() = status == TaskStatus.NOT_STARTED


    fun isDone() = status == TaskStatus.DONE
}