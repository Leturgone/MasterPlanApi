package api.masterplan.app.plansModule.application.service

import api.masterplan.app.logging.LoggingMethod
import api.masterplan.app.plansModule.application.mapper.TasksPlanToEntityMapper
import api.masterplan.app.plansModule.domain.dtos.PlanDetails
import api.masterplan.app.plansModule.domain.exceptions.PlanException
import api.masterplan.app.plansModule.domain.interfaces.PlanRepository
import api.masterplan.app.plansModule.domain.interfaces.PlanService
import api.masterplan.app.plansModule.domain.model.entity.Plan
import api.masterplan.app.plansModule.domain.model.entity.Task
import api.masterplan.app.plansModule.domain.model.value.*
import org.springframework.stereotype.Service

@Service
class PlanServiceImpl(
    private val planRepository: PlanRepository
): PlanService {

    @LoggingMethod("planModule")
    override fun getPlanById(planId: PlanId): PlanDetails {
        val plan = planRepository.getPlan(planId)?: throw PlanException.PlanNotExist(planId)

        return TasksPlanToEntityMapper.toPlanDetails(plan)
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

        val plan = planRepository.savePlan(planEntity)?: throw PlanException.FailedToCreatePlan(title, directorId)

        return plan
    }



    @LoggingMethod("planModule")
    override fun removeTaskFromPlan(planId: PlanId, task: Task): PlanId {
        val plan = planRepository.getPlan(planId)?: throw PlanException.PlanNotExist(planId)

        plan.removeTask(task)

        val removedId = planRepository.updatePlan(planId, plan)?: throw PlanException.FailedToRemoveTask(planId,task.id)

        return removedId
    }


    @LoggingMethod("planModule")
    override fun getAllDirPlans(directorId: PlanDirectorId): List<PlanDetails> {
        val planList = planRepository.getDirPlans(directorId)

        return planList.map { TasksPlanToEntityMapper.toPlanDetails(it)}
    }


    @LoggingMethod("planModule")
    override fun updatePlan(planId: PlanId, updatedPlan: Plan): PlanId {
        val plan = planRepository.getPlan(planId)?: throw PlanException.PlanNotExist(planId)


        val updatedPlanId = planRepository.updatePlan(planId, plan)?: throw PlanException.FailedToUpdatePlan(planId)
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
            TasksPlanToEntityMapper.toPlanDetails(it)
        }
    }



    @LoggingMethod("planModule")
    override fun filterDirPlansByStatus(directorId: PlanDirectorId, status: PlanStatus): List<PlanDetails> {
        val dirPlans = planRepository.getDirPlans(directorId)

        return dirPlans.filter { plan ->
            plan.status == status
        }.map {
            TasksPlanToEntityMapper.toPlanDetails(it)
        }
    }

}