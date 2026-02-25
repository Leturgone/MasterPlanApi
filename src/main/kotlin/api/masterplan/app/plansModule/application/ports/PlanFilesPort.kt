package api.masterplan.app.plansModule.application.ports

import api.masterplan.app.plansModule.application.dto.*
import api.masterplan.app.plansModule.domain.model.value.PlanDocumentId
import api.masterplan.app.plansModule.domain.model.value.PlanId
import api.masterplan.app.plansModule.domain.model.value.TaskDocumentId

interface PlanFilesPort {

    fun uploadPlanFile(planFile: PlanFile): PlanDocumentId

    fun downloadPlanFile(planFileId: PlanDocumentId): PlanFile

    fun uploadTaskFile(taskFile: TaskFile): TaskDocumentId

    fun downloadTaskFile(taskFileId: TaskDocumentId): TaskFile

    fun exportPlan(planId: PlanId): PlanExportFile

}