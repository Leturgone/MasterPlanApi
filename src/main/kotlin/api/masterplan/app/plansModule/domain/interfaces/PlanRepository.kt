package api.masterplan.app.plansModule.domain.interfaces

import api.masterplan.app.plansModule.domain.model.entity.Plan
import api.masterplan.app.plansModule.domain.model.value.PlanDirectorId
import api.masterplan.app.plansModule.domain.model.value.PlanId
import api.masterplan.app.plansModule.domain.model.value.PlanTitle

interface PlanRepository {

    fun getPlan(planId: PlanId): Plan?

    fun deletePlan(planId: PlanId): PlanId?

    fun savePlan(plan: Plan): PlanId?

    fun getDirPlans(directorId: PlanDirectorId): List<Plan>

    fun isPlanExist(planTitle: PlanTitle, directorId: PlanDirectorId): Boolean

    fun updatePlan(planId: PlanId, plan: Plan): PlanId?
}