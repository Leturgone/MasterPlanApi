package api.masterplan.app.plansModule.infrastructure.database.repository

import api.masterplan.app.plansModule.infrastructure.database.entity.TaskEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TaskJpaRepository: CrudRepository<TaskEntity, UUID> {
}