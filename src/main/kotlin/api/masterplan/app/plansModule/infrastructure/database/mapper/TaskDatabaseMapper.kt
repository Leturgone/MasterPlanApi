package api.masterplan.app.plansModule.infrastructure.database.mapper

import api.masterplan.app.plansModule.domain.model.entity.Task
import api.masterplan.app.plansModule.domain.model.value.*
import api.masterplan.app.plansModule.infrastructure.database.entity.TaskEntity
import api.masterplan.app.plansModule.infrastructure.database.entity.TaskExecutorId
import api.masterplan.app.plansModule.infrastructure.database.entity.TaskHasExecutorEntity
import api.masterplan.app.plansModule.infrastructure.database.entity.TaskStatusEntity

object TaskDatabaseMapper {
    
    fun toDomain(task: TaskEntity): Task {
        val domainStatus = TaskStatusDatabaseMapper.toDomain(task.taskStatus)
        val executors = task.executorLinks.map { ExecutorId(it.id.executorId) }.toMutableList()
        return Task.create(
            id = TaskId(task.id),
            title = TaskTitle.validate(task.title),
            description = TaskDescription.validate(task.description),
            endDate = TaskDate(task.endDate),
            planId = PlanId(task.planId),
            documentId = task.documentId?.let { TaskDocumentId(it) },
            urgency = TaskUrgency.validate(task.urgency),
            executorsId = executors,
        ).changeTaskStatus(domainStatus)
    }
    
    fun toDomain(tasks: List<TaskEntity>): List<Task> {
        return tasks.map { toDomain(it) }
    }
    
    fun toEntity(task: Task,statusSet: Set<TaskStatusEntity>): TaskEntity {
        val statusEntity = TaskStatusDatabaseMapper.toEntity(statusSet,task.status)
        val executorLinks = task.executorsIds.map { executorId ->
            TaskHasExecutorEntity(
                id = TaskExecutorId(
                    taskId = task.id.value,
                    executorId = executorId.value,
                )
            )

        }.toMutableSet()
        return TaskEntity(
            id = task.id.value,
            title = task.title.value,
            description = task.description.value,
            endDate = task.endDate.value,
            taskStatus = statusEntity,
            planId = task.planId.value,
            documentId = task.documentId?.value,
            urgency = task.urgency.value,
            executorLinks = executorLinks
        )
    }
}