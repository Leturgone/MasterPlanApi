package api.masterplan.app.plansModule

import java.util.UUID

data class TaskModelDto(
    val employeeIds: List<UUID>,
    val status: String,
    val weight: Double,
)