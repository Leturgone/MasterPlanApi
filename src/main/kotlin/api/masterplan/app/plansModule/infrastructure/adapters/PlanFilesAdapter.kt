package api.masterplan.app.plansModule.infrastructure.adapters

import api.masterplan.app.apiContracts.files.FilesModuleService
import api.masterplan.app.plansModule.application.dto.ExecutorDto
import api.masterplan.app.plansModule.application.dto.PlanExportFile
import api.masterplan.app.plansModule.application.dto.PlanFile
import api.masterplan.app.plansModule.application.dto.TaskFile
import api.masterplan.app.plansModule.application.ports.PlanFilesPort
import api.masterplan.app.plansModule.domain.dtos.TaskDetails
import api.masterplan.app.plansModule.domain.model.value.PlanDocumentId
import api.masterplan.app.plansModule.domain.model.value.PlanTitle
import api.masterplan.app.plansModule.domain.model.value.TaskDocumentId
import api.masterplan.app.plansModule.infrastructure.adapters.dto.ExecutorExportData
import api.masterplan.app.plansModule.infrastructure.adapters.dto.TaskWithExecutorsDto
import org.springframework.stereotype.Component

@Component
class PlanFilesAdapter(
    val filesModuleService: FilesModuleService
): PlanFilesPort {

    override fun uploadPlanFile(planFile: PlanFile): PlanDocumentId {
        val result = filesModuleService.uploadFile(
            documentFileBaseName = planFile.fileName,
            documentFileData = planFile.fileData,
        ).getOrElse {
            throw PlansInnerModuleErrorMapper.exceptionToModuleException(it)
        }
        return PlanDocumentId(result)
    }

    override fun uploadTaskFile(taskFile: TaskFile): TaskDocumentId {
        val result = filesModuleService.uploadFile(
            documentFileBaseName = taskFile.fileName,
            documentFileData = taskFile.fileData,
        ).getOrElse {
            throw PlansInnerModuleErrorMapper.exceptionToModuleException(it)
        }
        return TaskDocumentId(result)
    }

    override fun exportPlan(planTitle: PlanTitle, planTaskList: List<TaskDetails>, executors: List<ExecutorDto>): PlanExportFile {

        val executorsMap = executors.associate { it.executorId to toExecutorExportData(it) }
        val tasksWithExecutors = planTaskList.map {task ->
            val taskExecutors = task.executorsIds.mapNotNull { executorId -> executorsMap[executorId] }
            toTaskWithExecutorsDto(task, taskExecutors)
        }
        val result = filesModuleService.exportListToExcel(planTitle.toString(),tasksWithExecutors).getOrElse {
            throw PlansInnerModuleErrorMapper.exceptionToModuleException(it)
        }
        return PlansInnerModuleSuccessMapper.toPlanExportFile(result)
    }

    private fun toExecutorExportData(executor: ExecutorDto): ExecutorExportData {
        return ExecutorExportData(
            name = executor.name,
            surname = executor.surname,
            patronymic = executor.patronymic
        )
    }

    private fun toTaskWithExecutorsDto(task: TaskDetails,taskExecutors: List<ExecutorExportData>): TaskWithExecutorsDto {
        return TaskWithExecutorsDto(
            id = task.id.value.toString(),
            title = task.title.value,
            description = task.description.value,
            endDate = task.endDate.value,
            status = task.status.name,
            executorsIds = taskExecutors,
        )
    }


    override fun removePlanFile(planFileId: PlanDocumentId): PlanDocumentId {
        val result = filesModuleService.removeFile(planFileId.value).getOrElse {
            throw PlansInnerModuleErrorMapper.exceptionToModuleException(it)
        }
        return PlanDocumentId(result)
    }

    override fun removeTaskFile(taskFileId: TaskDocumentId): TaskDocumentId {
        val result = filesModuleService.removeFile(taskFileId.value).getOrElse {
            throw PlansInnerModuleErrorMapper.exceptionToModuleException(it)
        }
        return TaskDocumentId(result)
    }

    override fun uploadOrUpdatePlanFile(planFileId: PlanDocumentId?, planFile: PlanFile): PlanDocumentId {
        return if (planFileId == null) {
            uploadPlanFile(planFile)
        }else{
            val result = filesModuleService.updateFile(
                fileId = planFileId.value,
                documentFileBaseName = planFile.fileName,
                documentFileData = planFile.fileData,
            ).getOrElse {
                throw PlansInnerModuleErrorMapper.exceptionToModuleException(it)
            }
            PlanDocumentId(result)
        }
    }

    override fun uploadOrUpdateTaskFile(taskFileId: TaskDocumentId?, taskFile: TaskFile): TaskDocumentId {
        return if (taskFileId == null) {
            uploadTaskFile(taskFile)
        }else{
            val result = filesModuleService.updateFile(
                fileId = taskFileId.value,
                documentFileBaseName = taskFile.fileName,
                documentFileData = taskFile.fileData,
            ).getOrElse {
                throw PlansInnerModuleErrorMapper.exceptionToModuleException(it)
            }
            TaskDocumentId(result)
        }
    }
}