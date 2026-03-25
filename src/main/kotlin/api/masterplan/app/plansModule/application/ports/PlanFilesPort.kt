package api.masterplan.app.plansModule.application.ports

import api.masterplan.app.plansModule.application.dto.ExecutorDto
import api.masterplan.app.plansModule.application.dto.PlanExportFile
import api.masterplan.app.plansModule.application.dto.PlanFile
import api.masterplan.app.plansModule.application.dto.TaskFile
import api.masterplan.app.plansModule.domain.dtos.PlanDetails
import api.masterplan.app.plansModule.domain.dtos.TaskDetails
import api.masterplan.app.plansModule.domain.model.entity.Task
import api.masterplan.app.plansModule.domain.model.value.PlanDocumentId
import api.masterplan.app.plansModule.domain.model.value.PlanTitle
import api.masterplan.app.plansModule.domain.model.value.TaskDocumentId

interface PlanFilesPort {

    fun uploadPlanFile(planFile: PlanFile): PlanDocumentId

    fun uploadTaskFile(taskFile: TaskFile): TaskDocumentId

    fun exportPlan(planTitle: PlanTitle, planTaskList: List<TaskDetails>, executors: List<ExecutorDto>): PlanExportFile

    fun removePlanFile(planFileId: PlanDocumentId): PlanDocumentId

    fun removeTaskFile(taskFileId: TaskDocumentId): TaskDocumentId

    fun uploadOrUpdatePlanFile(planFileId: PlanDocumentId?, planFile: PlanFile): PlanDocumentId

    fun uploadOrUpdateTaskFile(taskFileId: TaskDocumentId?,taskFile: TaskFile): TaskDocumentId

}