package api.masterplan.app.apiContracts.plans

import java.util.UUID

data class TaskModelDto(
    val employeeIds: List<UUID>,
    val status: String,
    val weight: Double,
)