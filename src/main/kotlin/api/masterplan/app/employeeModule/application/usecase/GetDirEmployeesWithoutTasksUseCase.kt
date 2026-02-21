package api.masterplan.app.employeeModule.application.usecase

import api.masterplan.app.employeeModule.application.command.GetDirEmployeesWithoutTasksCommand
import api.masterplan.app.employeeModule.domain.dtos.EmployeeDetails
import api.masterplan.app.employeeModule.domain.interfaces.EmployeeService
import org.springframework.stereotype.Service

@Service
class GetDirEmployeesWithoutTasksUseCase(
    private val employeeService: EmployeeService
) {
    suspend operator fun invoke(command: GetDirEmployeesWithoutTasksCommand): Result<List<EmployeeDetails>> {
        return try {
            val employeesWithoutTasks = employeeService.getAllDirectorsEmployeesWithoutTasks(command.directorId)
            Result.success(employeesWithoutTasks)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}