package api.masterplan.app.plansModule.domain.model.entity

import api.masterplan.app.plansModule.domain.model.value.*

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

    fun addDocument(documentId: TaskDocumentId): Task{
        return this.copy(documentId = documentId)
    }

    fun changeTaskStatus(taskStatus: TaskStatus): Task{
        return this.copy(status=taskStatus)
    }

}