package api.masterplan.app.employeeModule.application.usecase

import api.masterplan.app.employeeModule.application.command.SortDirEmployeesByRatingCommand
import api.masterplan.app.employeeModule.domain.dtos.EmployeeDetails
import api.masterplan.app.employeeModule.domain.interfaces.EmployeeService
import org.springframework.stereotype.Service

@Service
class SortDirEmployeesByRatingUseCase(
    private val employeeService: EmployeeService
) {
    suspend operator fun invoke(command: SortDirEmployeesByRatingCommand): Result<List<EmployeeDetails>>{
        return try {
            val employeeList = employeeService.getAllDirectorsEmployeeSortByRating(command.directorId)
            Result.success(employeeList)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}