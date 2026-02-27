package api.masterplan.app.plansModule.domain.interfaces

import api.masterplan.app.plansModule.domain.dtos.PlanDetails
import api.masterplan.app.plansModule.domain.model.entity.Plan
import api.masterplan.app.plansModule.domain.model.value.*

interface PlanService {

    fun getPlanById(planId: PlanId): PlanDetails

    fun createPlan(id: PlanId? = null, title: PlanTitle, description: PlanDescription, startDate: PlanDate? = null,
                   endDate: PlanDate, directorId: PlanDirectorId,
                   documentId: PlanDocumentId? = null): PlanId


    fun getAllDirPlans(directorId: PlanDirectorId): List<PlanDetails>

    fun updatePlan(planId: PlanId, updatedPlan: Plan): PlanId

    fun deletePlan(planId: PlanId): PlanId

    fun sortDirPlansByDate(directorId: PlanDirectorId): List<PlanDetails>

    fun filterDirPlansByStatus(directorId: PlanDirectorId, status: PlanStatus): List<PlanDetails>
}