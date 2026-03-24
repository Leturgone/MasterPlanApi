package api.masterplan.app.employeeModule.infrastructure.adapters

import api.masterplan.app.employeeModule.application.dto.FileModel
import api.masterplan.app.employeeModule.application.ports.EmployeeFilesCreator
import api.masterplan.app.employeeModule.domain.dtos.EmployeeWithMetricsDetails
import api.masterplan.app.employeeModule.infrastructure.adapters.dto.EmployeeWithMetricsDetailsDto
import api.masterplan.app.filesModule.FilesModuleService
import org.springframework.stereotype.Component

@Component
class EmployeeFilesCreatorImpl(
    private val filesModuleService: FilesModuleService
): EmployeeFilesCreator {
    override fun createDirEmployeesExcelTable(employeeData: List<EmployeeWithMetricsDetails>): FileModel {
        val data = employeeData.map {convertToPrimitiveFields(it)}

        val result = filesModuleService.exportListToExcel("employees", data).getOrElse{
            throw EmployeeInnerModuleErrorMapper.exceptionToModuleException(it)
        }

        return EmployeeInnerModuleSuccessMapper.toExportFileModel(result)
    }

    private fun convertToPrimitiveFields(model: EmployeeWithMetricsDetails): EmployeeWithMetricsDetailsDto{
        return EmployeeWithMetricsDetailsDto(
            id = model.id.value.toString(),
            name = model.name.value,
            surname = model.surname.value,
            patronymic = model.patronymic?.value,
            directorName = model.director?.name?.value,
            directorSurname = model.director?.surname?.value,
            directorPatronymic = model.director?.patronymic?.value,
            rating = model.metrics.rating,
            workload = model.metrics.workload,
            assignedTasksCount = model.metrics.assignedTasksCount
        )
    }
}