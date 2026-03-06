package api.masterplan.app.plansModule.infrastructure.database.mapper

import api.masterplan.app.plansModule.domain.exceptions.PlanException
import api.masterplan.app.plansModule.domain.model.value.PlanStatus
import api.masterplan.app.plansModule.infrastructure.database.entity.PlanStatusEntity

internal object PlanStatusDatabaseMapper {
    fun toDomain(entity: PlanStatusEntity): PlanStatus {
        return try {
            PlanStatus.valueOf(entity.status.uppercase())
        }catch (_: IllegalArgumentException){
            throw PlanException.InvalidPlanStatusTitle(entity.status.uppercase())
        }
    }

    fun toEntity(planStatusList: Set<PlanStatusEntity>, planStatus: PlanStatus): PlanStatusEntity {
        val statusByTitle = planStatusList.associateBy { it.status.uppercase() }
        val domainStatus = statusByTitle[planStatus.name]?:throw PlanException.InvalidPlanStatusTitle(planStatus.name)
        return domainStatus
    }
}