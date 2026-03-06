package api.masterplan.app.plansModule.infrastructure.database.mapper

import api.masterplan.app.plansModule.domain.exceptions.PlanException
import api.masterplan.app.plansModule.domain.model.value.TaskStatus
import api.masterplan.app.plansModule.infrastructure.database.entity.TaskStatusEntity

internal object TaskStatusDatabaseMapper {
    fun toDomain(entity: TaskStatusEntity): TaskStatus {
        return try {
            TaskStatus.valueOf(entity.status.uppercase())
        }catch (_: IllegalArgumentException){
            throw PlanException.InvalidTaskStatusTitle(entity.status.uppercase())
        }
    }

    fun toEntity(taskStatusList: Set<TaskStatusEntity>, taskStatus: TaskStatus): TaskStatusEntity {
        val statusByTitle = taskStatusList.associateBy { it.status.uppercase() }
        val domainStatus = statusByTitle[taskStatus.name]?:throw PlanException.InvalidTaskStatusTitle(taskStatus.name)
        return domainStatus
    }
}