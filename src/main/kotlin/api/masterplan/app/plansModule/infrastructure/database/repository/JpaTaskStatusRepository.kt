package api.masterplan.app.plansModule.infrastructure.database.repository

import api.masterplan.app.plansModule.infrastructure.database.entity.TaskStatusEntity
import org.springframework.data.repository.CrudRepository

interface JpaTaskStatusRepository: CrudRepository<TaskStatusEntity, Int> {
}