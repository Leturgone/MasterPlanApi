package api.masterplan.app.plansModule

import java.util.UUID

interface PlanModuleService {

    fun getTasksByEmployeeId(employeeId: UUID): Result<List<TaskModelDto>>

    fun getTasksByEmployeeIds(employeeIds: Set<UUID>): Result<List<TaskModelDto>>
}