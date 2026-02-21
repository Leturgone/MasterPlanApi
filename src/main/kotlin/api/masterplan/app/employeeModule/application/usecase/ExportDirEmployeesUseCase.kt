package api.masterplan.app.employeeModule.application.usecase

import api.masterplan.app.employeeModule.application.command.ExportDirEmployeesCommand
import api.masterplan.app.employeeModule.application.dto.FileModel
import api.masterplan.app.employeeModule.application.ports.EmployeeFilesCreator
import api.masterplan.app.employeeModule.domain.interfaces.EmployeeService
import org.springframework.stereotype.Service

@Service
class ExportDirEmployeesUseCase(
    private val employeeService: EmployeeService,
    private val employeeFilesCreator: EmployeeFilesCreator
) {
    suspend operator fun invoke(command: ExportDirEmployeesCommand): Result<FileModel> {
        return try {
            val dirEmployeesList = employeeService.getAllDirectorsEmployeesWithMetrics(command.directorId)
            val excelFile = employeeFilesCreator.createDirEmployeesExcelTable(dirEmployeesList)
            Result.success(excelFile)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}