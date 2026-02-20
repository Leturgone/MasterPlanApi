package api.masterplan.app.employeeModule.application.usecase

import api.masterplan.app.employeeModule.domain.dtos.EmployeeDetails
import api.masterplan.app.employeeModule.domain.interfaces.EmployeeService
import org.springframework.stereotype.Service

@Service
class GetAllEmployeesUseCase(
    private val employeeService: EmployeeService
) {
    operator fun invoke(): Result<List<EmployeeDetails>> {
        return try {
            val employeeList = employeeService.getAllEmployees()
            Result.success(employeeList)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}