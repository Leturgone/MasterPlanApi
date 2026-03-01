package api.masterplan.app.plansModule.infrastructure.database.mapper

import api.masterplan.app.plansModule.domain.model.entity.Plan
import api.masterplan.app.plansModule.domain.model.value.*
import api.masterplan.app.plansModule.infrastructure.database.entity.PlanEntity
import api.masterplan.app.plansModule.infrastructure.database.entity.PlanStatusEntity

object PlanDatabaseMapper {
    fun toDomain(planEntity: PlanEntity): Plan{
        val domainStatus = PlanStatusDatabaseMapper.toDomain(planEntity.planStatus)
        return Plan.create(
            id = PlanId(planEntity.id),
            title = PlanTitle.validate(planEntity.title),
            description = PlanDescription.validate(planEntity.description),
            startDate = PlanDate(planEntity.startDate),
            endDate = PlanDate(planEntity.endDate),
            directorId = planEntity.directorId?.let {PlanDirectorId(it) },
            documentId = planEntity.documentId?.let { PlanDocumentId(it) },
        ).changePlanStatus(domainStatus)
    }

    fun toDomain(planList: List<PlanEntity>): List<Plan> {
        return planList.map { toDomain(it) }
    }

    fun toEntity(plan: Plan,statusSet: Set<PlanStatusEntity>): PlanEntity {
        val statusEntity = PlanStatusDatabaseMapper.toEntity(statusSet, plan.status)
        return PlanEntity(
            id = plan.id.value,
            title = plan.title.value,
            description = plan.description.value,
            startDate = plan.startDate.value,
            endDate = plan.endDate.value,
            planStatus = statusEntity,
            directorId = plan.directorId?.value,
            documentId = plan.documentId?.value,
        )

    }

}