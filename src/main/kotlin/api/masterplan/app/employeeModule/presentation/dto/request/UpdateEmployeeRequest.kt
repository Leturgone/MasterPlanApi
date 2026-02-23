package api.masterplan.app.employeeModule.presentation.dto.request

import java.util.UUID

data class UpdateEmployeeRequest(
    val id: UUID,
    val newName: String,
    val newSurname: String,
    val newPatronymic: String? = null,
    val newDirectorId: UUID? = null,
    val userId: UUID,
)
