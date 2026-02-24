package api.masterplan.app.plansModule.application.ports

import api.masterplan.app.plansModule.application.dto.PlanExportFile
import api.masterplan.app.plansModule.application.dto.PlanFile
import api.masterplan.app.plansModule.application.dto.PlanFileId
import api.masterplan.app.plansModule.application.dto.TaskFile
import api.masterplan.app.plansModule.application.dto.TaskFileId
import api.masterplan.app.plansModule.domain.model.value.PlanId
import api.masterplan.app.plansModule.domain.model.value.TaskId

interface PlanFilesPort {

    fun uploadPlanFile(planId: PlanId, planFile: PlanFile): PlanFileId

    fun downloadPlanFile(planId: PlanId): PlanFile

    fun uploadTaskFile(taskId: TaskId, taskFile: TaskFile): TaskFileId

    fun downloadTaskFile(taskId: TaskId): TaskFile

    fun exportPlan(planId: PlanId): PlanExportFile

}