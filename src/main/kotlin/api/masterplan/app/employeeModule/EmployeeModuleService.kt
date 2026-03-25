package api.masterplan.app.employeeModule

import java.util.*

interface EmployeeModuleService {

    fun createEmployee(employee: EmployeeDataDto): Result<UUID>

    fun getSubordinateEmployees(directorId: UUID): Result<Set<UUID>>

    fun getEmployeeById(employeeId: UUID): Result<EmployeeDataDto>

}