package api.masterplan.app.employeeModule.application.usecase

import api.masterplan.app.employeeModule.application.command.CreateEmployeeCommand
import api.masterplan.app.employeeModule.domain.interfaces.EmployeeService
import api.masterplan.app.employeeModule.domain.model.value.EmployeeId
import org.springframework.stereotype.Service

@Service
class CreateEmployeeUseCase(
    private val employeeService: EmployeeService
) {
    operator fun invoke(command: CreateEmployeeCommand): Result<EmployeeId> {
        return try {
            val employeeId = employeeService.createEmployee(
                id = command.id,
                name = command.name,
                surname = command.surname,
                patronymic = command.patronymic,
                directorId = command.directorId,
                userId = command.userId,
            )
            Result.success(employeeId)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}