package api.masterplan.app.employeeModule.application.usecase

import api.masterplan.app.employeeModule.application.command.SearchDirEmployeeByNameCommand
import api.masterplan.app.employeeModule.domain.dtos.EmployeeDetails
import api.masterplan.app.employeeModule.domain.interfaces.EmployeeService
import org.springframework.stereotype.Service

@Service
class SearchDirEmployeeByNameUseCase(
    private val employeeService: EmployeeService
) {
    operator fun invoke(command: SearchDirEmployeeByNameCommand): Result<List<EmployeeDetails>>{
        return try {
            val employeeList = employeeService.searchDirEmployee(command.query,command.directorId)
            Result.success(employeeList)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}