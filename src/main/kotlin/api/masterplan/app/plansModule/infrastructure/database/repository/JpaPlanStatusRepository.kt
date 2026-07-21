package api.masterplan.app.plansModule.infrastructure.database.repository

import api.masterplan.app.plansModule.infrastructure.database.entity.PlanStatusEntity
import org.springframework.data.repository.CrudRepository


interface JpaPlanStatusRepository: CrudRepository<PlanStatusEntity, Int> {
}