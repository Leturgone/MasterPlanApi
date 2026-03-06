package api.masterplan.app.plansModule.application.mapper

import api.masterplan.app.plansModule.domain.dtos.PlanDetails
import api.masterplan.app.plansModule.domain.dtos.TaskDetails
import api.masterplan.app.plansModule.domain.model.entity.Plan
import api.masterplan.app.plansModule.domain.model.entity.Task

object TasksPlanToEntityMapper {
    
    fun toPlanDetails(entity: Plan): PlanDetails {
        return PlanDetails(
            id = entity.id,
            title = entity.title,
            description = entity.description,
            startDate = entity.startDate,
            endDate = entity.endDate,
            status = entity.status,
            directorId = entity.directorId,
            documentId = entity.documentId,
        )
    }
    
    fun toTaskDetails(entity: Task): TaskDetails {
        return TaskDetails(
            id = entity.id,
            title = entity.title,
            description = entity.description,
            endDate = entity.endDate,
            status = entity.status,
            planId = entity.planId,
            documentId = entity.documentId,
            urgency = entity.urgency,
            executorsIds = entity.executorsIds,
        )
    }
}