package api.masterplan.app.employeeModule.application.usecase

import api.masterplan.app.employeeModule.application.command.SortDirEmployeesByWorkloadCommand
import api.masterplan.app.employeeModule.domain.dtos.EmployeeDetails
import api.masterplan.app.employeeModule.domain.interfaces.EmployeeService
import org.springframework.stereotype.Service

@Service
class SortDirEmployeesByWorkloadUseCase(
    private val employeeService: EmployeeService
) {
    suspend operator fun invoke(command: SortDirEmployeesByWorkloadCommand): Result<List<EmployeeDetails>> {
        return try {
            val employeeList = employeeService.getAllDirectorsEmployeeSortByWorkLoad(command.directorId)
            Result.success(employeeList)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}