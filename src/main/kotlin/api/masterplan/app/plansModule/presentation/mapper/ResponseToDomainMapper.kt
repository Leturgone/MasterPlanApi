package api.masterplan.app.plansModule.presentation.mapper

import api.masterplan.app.plansModule.domain.exceptions.PlanException
import api.masterplan.app.plansModule.domain.model.value.PlanId
import api.masterplan.app.plansModule.domain.model.value.PlanStatus
import api.masterplan.app.plansModule.domain.model.value.TaskId
import api.masterplan.app.plansModule.domain.model.value.TaskStatus
import java.util.UUID

object ResponseToDomainMapper {
    fun toTaskStatus(status: String): TaskStatus{
        return try {
            TaskStatus.valueOf(status.uppercase())
        }catch (_: IllegalArgumentException){
            throw PlanException.InvalidPlanStatusTitle(status.uppercase())
        }
    }

    fun toPlanStatus(status: String): PlanStatus {
        return try {
            PlanStatus.valueOf(status.uppercase())
        }catch (_: IllegalArgumentException){
            throw PlanException.InvalidPlanStatusTitle(status.uppercase())
        }
    }

    fun toPlanId(planId: UUID) = PlanId(planId)

    fun toTaskId(taskId: UUID) = TaskId(taskId)
}