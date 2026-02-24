package api.masterplan.app.employeeModule.application.usecase

import api.masterplan.app.employeeModule.application.command.GetAllDirectorEmployeesCommand
import api.masterplan.app.employeeModule.domain.dtos.EmployeeDetails
import api.masterplan.app.employeeModule.domain.interfaces.EmployeeService
import org.springframework.stereotype.Service

@Service
class GetAllDirectorEmployeesUseCase(
    private val employeeService: EmployeeService
) {
    operator fun invoke(command: GetAllDirectorEmployeesCommand): Result<List<EmployeeDetails>>{
        return try {
            val employeesList = employeeService.getAllDirectorsEmployee(
                command.directorId
            )
            Result.success(employeesList)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}