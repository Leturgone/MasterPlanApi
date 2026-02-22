package api.masterplan.app.employeeModule

import java.util.*

interface EmployeeModuleService {

    fun createEmployee(employee: EmployeeDataDto): UUID

}