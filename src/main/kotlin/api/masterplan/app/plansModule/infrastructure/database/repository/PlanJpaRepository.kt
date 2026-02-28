package api.masterplan.app.plansModule.infrastructure.database.repository

import api.masterplan.app.plansModule.infrastructure.database.entity.PlanEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PlanJpaRepository: CrudRepository<PlanEntity, UUID> {
}