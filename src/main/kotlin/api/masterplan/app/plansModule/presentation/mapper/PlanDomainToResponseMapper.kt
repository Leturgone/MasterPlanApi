package api.masterplan.app.plansModule.presentation.mapper

import api.masterplan.app.plansModule.application.dto.PlanExportFile
import api.masterplan.app.plansModule.domain.dtos.PlanDetails
import api.masterplan.app.plansModule.domain.dtos.TaskDetails
import api.masterplan.app.plansModule.domain.model.value.PlanId
import api.masterplan.app.plansModule.domain.model.value.TaskId
import api.masterplan.app.plansModule.presentation.dto.response.*

object PlanDomainToResponseMapper {

    fun toResponse(plan: PlanDetails): PlanInformationResponse {
        return PlanInformationResponse(
            id = plan.id.value,
            title = plan.title.value,
            description = plan.description.value,
            startDate = plan.startDate.value,
            endDate = plan.endDate.value,
            status = plan.status.name,
            directorId = plan.directorId?.value,
            documentId = plan.documentId?.value,
        )
    }


    @JvmName("toPlanResponse")
    fun toResponse(planList: List<PlanDetails>): List<PlanInformationResponse> {
        return planList.map { toResponse(it) }
    }

    fun toResponse(task: TaskDetails): TaskInformationResponse {
        return TaskInformationResponse(
            id = task.id.value,
            title = task.title.value,
            description = task.description.value,
            endDate = task.endDate.value,
            status = task.status.name,
            planId = task.planId.value,
            documentId = task.documentId?.value,
            urgency = task.urgency.value,
            executorsIds = task.executorsIds.map { it.value },
        )
    }


    @JvmName("toTaskResponse")
    fun toResponse(taskList: List<TaskDetails>): List<TaskInformationResponse> {
        return taskList.map { toResponse(it) }
    }

    fun toResponse(taskId: TaskId) = TaskIdResponse(taskId.value)

    fun toResponse(planId: PlanId) = PlanIdResponse(planId.value)

    fun toResponse(exportFile: PlanExportFile): ExportPlanResponse {
        return ExportPlanResponse(
            fileData = exportFile.fileData,
            fileName = exportFile.fileName,
        )
    }

}