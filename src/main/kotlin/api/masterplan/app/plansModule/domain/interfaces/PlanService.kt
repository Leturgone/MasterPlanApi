package api.masterplan.app.plansModule.domain.interfaces

import api.masterplan.app.plansModule.domain.dtos.PlanDetails
import api.masterplan.app.plansModule.domain.model.entity.Plan
import api.masterplan.app.plansModule.domain.model.entity.Task
import api.masterplan.app.plansModule.domain.model.value.*

interface PlanService {

    fun getPlanById(planId: PlanId): PlanDetails

    fun createPlan(id: PlanId? = null, title: PlanTitle, description: PlanDescription, startDate: PlanDate,
                   endDate: PlanDate, directorId: PlanDirectorId,
                   documentId: PlanDocumentId? = null): PlanId

    fun addTaskToPlan(planId: PlanId, taskId: Task): PlanId

    fun getAllDirPlans(directorId: PlanDirectorId): List<PlanDetails>

    fun updatePlan(planId: PlanId, updatedPlan: Plan): PlanId

    fun deletePlan(planId: PlanId): PlanId

    fun filterDirPlansByDate(directorId: PlanDirectorId): List<PlanDetails>

    fun filterDirPlansByStatus(directorId: PlanDirectorId, status: TaskStatus): List<PlanDetails>
}