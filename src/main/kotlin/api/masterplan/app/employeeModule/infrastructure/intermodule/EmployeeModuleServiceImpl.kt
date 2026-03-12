package api.masterplan.app.employeeModule.infrastructure.intermodule

import api.masterplan.app.employeeModule.EmployeeDataDto
import api.masterplan.app.employeeModule.EmployeeModuleService
import api.masterplan.app.employeeModule.application.command.CreateEmployeeCommand
import api.masterplan.app.employeeModule.application.command.GetAllDirectorEmployeesCommand
import api.masterplan.app.employeeModule.application.usecase.CreateEmployeeUseCase
import api.masterplan.app.employeeModule.application.usecase.GetAllDirectorEmployeesUseCase
import api.masterplan.app.employeeModule.domain.exceptions.EmployeeException
import api.masterplan.app.employeeModule.domain.model.value.EmployeeId
import api.masterplan.app.employeeModule.domain.model.value.EmployeeName
import api.masterplan.app.employeeModule.domain.model.value.EmployeePatronymic
import api.masterplan.app.employeeModule.domain.model.value.EmployeeSurname
import api.masterplan.app.employeeModule.domain.model.value.EmployeeUserId
import org.springframework.stereotype.Service
import java.util.*

@Service
class EmployeeModuleServiceImpl(
    private val createEmployeeUseCase: CreateEmployeeUseCase,
    private val getAllDirectorEmployeesUseCase: GetAllDirectorEmployeesUseCase,
): EmployeeModuleService {
    override fun createEmployee(employee: EmployeeDataDto): Result<UUID> {
        return try {
            val command = CreateEmployeeCommand(
                name = EmployeeName.validate(employee.name),
                surname = EmployeeSurname.validate(employee.surname),
                patronymic = employee.patronymic?.let { EmployeePatronymic.validate(it) },
                directorId = employee.directorId?.let { EmployeeId(it) },
                userId = EmployeeUserId(employee.userId)
            )
            val result = createEmployeeUseCase(command).getOrThrow()
            Result.success(result.value)
        }catch (e: EmployeeException){
            val exception = InterModuleEmplToDtoErrorMapper.toDto(e)
            Result.failure(exception)
        }
    }

    override fun getSubordinateEmployees(directorId: UUID): Result<Set<UUID>> {
        return try {
            val command = GetAllDirectorEmployeesCommand(
                directorId = EmployeeId(directorId)
            )
            val result = getAllDirectorEmployeesUseCase(command).getOrThrow()
            val idList = result.map { it.id.value }.toSet()
            Result.success(idList)
        }catch (e: EmployeeException){
            val exception = InterModuleEmplToDtoErrorMapper.toDto(e)
            Result.failure(exception)
        }
    }

}