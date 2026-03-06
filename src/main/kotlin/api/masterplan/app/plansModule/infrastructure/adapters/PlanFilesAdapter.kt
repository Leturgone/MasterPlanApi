package api.masterplan.app.plansModule.infrastructure.adapters

import api.masterplan.app.plansModule.application.dto.PlanExportFile
import api.masterplan.app.plansModule.application.dto.PlanFile
import api.masterplan.app.plansModule.application.dto.TaskFile
import api.masterplan.app.plansModule.application.ports.PlanFilesPort
import api.masterplan.app.plansModule.domain.dtos.PlanDetails
import api.masterplan.app.plansModule.domain.model.value.PlanDocumentId
import api.masterplan.app.plansModule.domain.model.value.TaskDocumentId
import org.springframework.stereotype.Component

@Component
class PlanFilesAdapter(): PlanFilesPort {
    override fun uploadPlanFile(planFile: PlanFile): PlanDocumentId {
        println("Uploading plan file")
        TODO("Not yet implemented")
    }

    override fun uploadTaskFile(taskFile: TaskFile): TaskDocumentId {
        println("Uploading task file")
        TODO("Not yet implemented")
    }

    override fun exportPlan(plan: PlanDetails): PlanExportFile {
        println("Exporting plan")
        TODO("Not yet implemented")
    }

    override fun removePlanFile(planFileId: PlanDocumentId): PlanDocumentId {
        println("Removing plan file")
        TODO("Not yet implemented")
    }

    override fun removeTaskFile(taskFileId: TaskDocumentId): TaskDocumentId {
        println("Removing task file")
        TODO("Not yet implemented")
    }

    override fun uploadOrUpdatePlanFile(planFileId: PlanDocumentId?, planFile: PlanFile): PlanDocumentId {
        println("Uploading or updating plan file")
        TODO("Not yet implemented")
    }

    override fun uploadOrUpdateTaskFile(taskFileId: TaskDocumentId?, taskFile: TaskFile): TaskDocumentId {
        println("Uploading task file")
        TODO("Not yet implemented")
    }
}