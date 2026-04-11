package api.masterplan.app.employeeModule.application.usecase

import api.masterplan.app.employeeModule.application.command.GetEmployeeByUserIdCommand
import api.masterplan.app.employeeModule.domain.dtos.EmployeeDetails
import api.masterplan.app.employeeModule.domain.interfaces.EmployeeService
import org.springframework.stereotype.Service

@Service
class GetEmployeeByUserIdUseCase(
    private val employeeService: EmployeeService
) {
    operator fun invoke(command: GetEmployeeByUserIdCommand) : Result<EmployeeDetails> {
        return try {
            val employee = employeeService.getEmployeeByUserId(command.id)
            Result.success(employee)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}