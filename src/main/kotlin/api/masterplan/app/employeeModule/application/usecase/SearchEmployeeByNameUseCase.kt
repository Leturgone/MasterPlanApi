package api.masterplan.app.employeeModule.application.usecase

import api.masterplan.app.employeeModule.application.command.SearchEmployeeByNameCommand
import api.masterplan.app.employeeModule.domain.dtos.EmployeeDetails
import api.masterplan.app.employeeModule.domain.interfaces.EmployeeService
import org.springframework.stereotype.Service

@Service
class SearchEmployeeByNameUseCase(
    private val employeeService: EmployeeService
) {
    operator fun invoke(command: SearchEmployeeByNameCommand): Result<List<EmployeeDetails>>{
        return try {
            val employeeList = employeeService.searchEmployee(command.query)
            Result.success(employeeList)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}