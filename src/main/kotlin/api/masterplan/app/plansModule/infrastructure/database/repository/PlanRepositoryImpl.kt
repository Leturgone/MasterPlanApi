package api.masterplan.app.plansModule.infrastructure.database.repository

import api.masterplan.app.logging.LoggingDatabaseMethod
import api.masterplan.app.plansModule.domain.interfaces.PlanRepository
import api.masterplan.app.plansModule.domain.model.entity.Plan
import api.masterplan.app.plansModule.domain.model.value.PlanDirectorId
import api.masterplan.app.plansModule.domain.model.value.PlanId
import api.masterplan.app.plansModule.domain.model.value.PlanTitle
import api.masterplan.app.plansModule.infrastructure.database.mapper.PlanDatabaseMapper
import org.springframework.stereotype.Repository
import kotlin.jvm.optionals.getOrElse

@Repository
class PlanRepositoryImpl(
    private val jpaPlanRepository: JpaPlanRepository,
    private val jpaPlanStatusRepository: JpaPlanStatusRepository
): PlanRepository {

    @LoggingDatabaseMethod(moduleName = "planModule")
    override fun getPlan(planId: PlanId): Plan? {
        val plan = jpaPlanRepository.findById(planId.value).getOrElse { return null }
        return PlanDatabaseMapper.toDomain(plan)
    }


    @LoggingDatabaseMethod(moduleName = "planModule")
    override fun deletePlan(planId: PlanId): PlanId? {
        jpaPlanRepository.deleteById(planId.value)
        return planId
    }


    @LoggingDatabaseMethod(moduleName = "planModule")
    override fun savePlan(plan: Plan): PlanId? {
        val status = jpaPlanStatusRepository.findAll().toSet()
        val planEntity = PlanDatabaseMapper.toEntity(plan,status)
        val planId = jpaPlanRepository.save(planEntity).id
        return PlanId(planId)
    }


    @LoggingDatabaseMethod(moduleName = "planModule")
    override fun getDirPlans(directorId: PlanDirectorId): List<Plan> {
        val plans = jpaPlanRepository.findByDirectorId(directorId.value)
        return PlanDatabaseMapper.toDomain(plans)
    }


    @LoggingDatabaseMethod(moduleName = "planModule")
    override fun isPlanExist(planTitle: PlanTitle, directorId: PlanDirectorId): Boolean {
        return jpaPlanRepository.existsByTitleAndDirectorId(planTitle.value,directorId.value)
    }


    @LoggingDatabaseMethod(moduleName = "planModule")
    override fun updatePlan(planId: PlanId, plan: Plan): PlanId? {
        jpaPlanRepository.findById(planId.value).getOrElse { return null }
        val status = jpaPlanStatusRepository.findAll().toSet()
        val updatedPlanEntity = PlanDatabaseMapper.toEntity(plan,status)
        val userId = jpaPlanRepository.save(updatedPlanEntity).id
        return PlanId(userId)
    }
}