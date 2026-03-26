package api.masterplan.app.plansModule.infrastructure.adapters

import api.masterplan.app.apiContracts.employee.EmployeeDataDto
import api.masterplan.app.apiContracts.files.FileDataDto
import api.masterplan.app.plansModule.application.dto.ExecutorDto
import api.masterplan.app.plansModule.application.dto.PlanExportFile
import api.masterplan.app.plansModule.domain.model.value.ExecutorId
import java.util.*

object PlansInnerModuleSuccessMapper {
    fun toExecutorDto(executorId: UUID, executor: EmployeeDataDto): ExecutorDto {
        return ExecutorDto(
            executorId = ExecutorId(executorId),
            name = executor.name,
            surname = executor.surname,
            patronymic = executor.patronymic
        )
    }

    fun toPlanExportFile(planExportFile: FileDataDto): PlanExportFile {
        return PlanExportFile(
            fileData = planExportFile.fileData,
            fileName = planExportFile.fileName,
        )
    }
}