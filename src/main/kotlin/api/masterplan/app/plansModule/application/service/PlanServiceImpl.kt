package api.masterplan.app.plansModule.application.service

import api.masterplan.app.logging.LoggingMethod
import api.masterplan.app.plansModule.application.mapper.TasksPlanToDetailsMapper
import api.masterplan.app.plansModule.domain.dtos.PlanDetails
import api.masterplan.app.plansModule.domain.exceptions.PlanException
import api.masterplan.app.plansModule.domain.interfaces.PlanRepository
import api.masterplan.app.plansModule.domain.interfaces.PlanService
import api.masterplan.app.plansModule.domain.model.entity.Plan
import api.masterplan.app.plansModule.domain.model.value.*
import org.springframework.stereotype.Service

@Service
class PlanServiceImpl(
    private val planRepository: PlanRepository
): PlanService {

    @LoggingMethod("planModule")
    override fun getPlanById(planId: PlanId): PlanDetails {
        val plan = planRepository.getPlan(planId)?: throw PlanException.PlanNotExist(planId)

        return TasksPlanToDetailsMapper.toPlanDetails(plan)
    }


    @LoggingMethod("planModule")
    override fun createPlan(id: PlanId? , title: PlanTitle, description: PlanDescription,
                            startDate: PlanDate?, endDate: PlanDate, directorId: PlanDirectorId, documentId: PlanDocumentId?): PlanId {

        if (planRepository.isPlanExist(title,directorId)) throw PlanException.PlanAlreadyExists(title)

        val planEntity = Plan.create(
            id = id,
            title = title,
            description = description,
            startDate = startDate,
            endDate = endDate,
            directorId = directorId,
            documentId = documentId
        )

        val planId = planRepository.savePlan(planEntity)?: throw PlanException.FailedToCreatePlan(title, directorId)

        return planId
    }



    @LoggingMethod("planModule")
    override fun getAllDirPlans(directorId: PlanDirectorId): List<PlanDetails> {
        val planList = planRepository.getDirPlans(directorId)

        return planList.map { TasksPlanToDetailsMapper.toPlanDetails(it)}
    }


    @LoggingMethod("planModule")
    override fun updatePlan(planId: PlanId, updatedPlan: Plan): PlanId {
        planRepository.getPlan(planId)?: throw PlanException.PlanNotExist(planId)

        val updatedPlanId = planRepository.updatePlan(planId, updatedPlan)?: throw PlanException.FailedToUpdatePlan(planId)
        return updatedPlanId
    }


    @LoggingMethod("planModule")
    override fun deletePlan(planId: PlanId): PlanId {

        val deletedPlanId = planRepository.deletePlan(planId)?: throw PlanException.FailedToDeletePlan(planId)

        return deletedPlanId
    }


    @LoggingMethod("planModule")
    override fun sortDirPlansByDate(directorId: PlanDirectorId): List<PlanDetails> {
        val dirPlans = planRepository.getDirPlans(directorId)

        return dirPlans.sortedBy{ plan ->
            plan.endDate.value
        }.map {
            TasksPlanToDetailsMapper.toPlanDetails(it)
        }
    }



    @LoggingMethod("planModule")
    override fun filterDirPlansByStatus(directorId: PlanDirectorId, status: PlanStatus): List<PlanDetails> {
        val dirPlans = planRepository.getDirPlans(directorId)

        return dirPlans.filter { plan ->
            plan.status == status
        }.map {
            TasksPlanToDetailsMapper.toPlanDetails(it)
        }
    }


    @LoggingMethod("planModule")
    override fun assignPlanDocumentToPlan(planId: PlanId, documentId: PlanDocumentId): PlanId {
        val plan = planRepository.getPlan(planId)?: throw PlanException.PlanNotExist(planId)
        val planWithDocument = plan.addDocument(documentId)

        val updatedPlanId = planRepository.updatePlan(planId, planWithDocument)?: throw PlanException.FailedToAssignDocumentToPlan(
            planId,documentId
        )

        return updatedPlanId
    }


    @LoggingMethod("planModule")
    override fun updatePlanStatus(planId: PlanId, status: PlanStatus): PlanId {
        val plan = planRepository.getPlan(planId)?: throw PlanException.PlanNotExist(planId)

        val planWithNewStatus = plan.changePlanStatus(status)

        val updatedPlanId = planRepository.updatePlan(planId, planWithNewStatus)?: throw PlanException.FailedToUpdatePlanStatus(
            planId,status
        )

        return updatedPlanId
    }

}