package api.masterplan.app.employeeModule.application.usecase

import api.masterplan.app.employeeModule.application.command.GetProfileInformationCommand
import api.masterplan.app.employeeModule.domain.dtos.EmployeeWithMetricsDetails
import api.masterplan.app.employeeModule.domain.interfaces.EmployeeService
import org.springframework.stereotype.Service

@Service
class GetProfileInformationUseCase(
    private val employeeService: EmployeeService
) {
    operator fun invoke(command: GetProfileInformationCommand): Result<EmployeeWithMetricsDetails>{
        return try {
            val profile = employeeService.getEmployeeWithMetrics(command.currentEmployeeId)
            Result.success(profile)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}