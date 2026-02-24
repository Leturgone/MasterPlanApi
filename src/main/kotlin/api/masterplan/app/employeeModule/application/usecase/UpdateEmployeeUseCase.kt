package api.masterplan.app.employeeModule.application.usecase

import api.masterplan.app.employeeModule.application.command.UpdateEmployeeCommand
import api.masterplan.app.employeeModule.domain.interfaces.EmployeeService
import api.masterplan.app.employeeModule.domain.model.value.EmployeeId
import org.springframework.stereotype.Service

@Service
class UpdateEmployeeUseCase(
    private val employeeService: EmployeeService
) {
    operator fun invoke(command: UpdateEmployeeCommand): Result<EmployeeId> {
        return try {
            val updatedEmployee = employeeService.updateEmployee(
                id = command.id,
                newEmployee = command.newEmployee
            )
            Result.success(updatedEmployee)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}