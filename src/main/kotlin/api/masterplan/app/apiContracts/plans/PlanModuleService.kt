package api.masterplan.app.apiContracts.plans

import java.util.UUID

interface PlanModuleService {

    fun getTasksByEmployeeId(employeeId: UUID): Result<List<TaskModelDto>>

    fun getTasksByEmployeeIds(employeeIds: Set<UUID>): Result<List<TaskModelDto>>
}