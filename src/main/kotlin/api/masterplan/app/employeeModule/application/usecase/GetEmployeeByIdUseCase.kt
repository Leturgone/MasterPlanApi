package api.masterplan.app.employeeModule.application.usecase

import api.masterplan.app.employeeModule.application.command.GetEmployeeByIdCommand
import api.masterplan.app.employeeModule.domain.dtos.EmployeeDetails
import api.masterplan.app.employeeModule.domain.interfaces.EmployeeService
import org.springframework.stereotype.Service

@Service
class GetEmployeeByIdUseCase(
    private val employeeService: EmployeeService
) {
    operator fun invoke(command: GetEmployeeByIdCommand) : Result<EmployeeDetails> {
        return try {
            val employee = employeeService.getEmployeeById(command.id)
            Result.success(employee)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}