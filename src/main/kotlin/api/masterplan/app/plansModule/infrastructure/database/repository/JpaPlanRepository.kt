package api.masterplan.app.plansModule.infrastructure.database.repository

import api.masterplan.app.plansModule.infrastructure.database.entity.PlanEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

interface JpaPlanRepository: CrudRepository<PlanEntity, UUID> {

    fun findByDirectorId(directorId: UUID): List<PlanEntity>

    fun existsByTitleAndDirectorId(title: String, directorId: UUID): Boolean
}