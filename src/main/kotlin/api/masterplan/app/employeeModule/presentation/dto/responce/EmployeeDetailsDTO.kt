package api.masterplan.app.employeeModule.presentation.dto.responce

import java.util.*

data class EmployeeDetailsDTO(
    val id: UUID,
    val name: String,
    val surname: String,
    val patronymic: String? = null,
    val directorId: UUID? = null,
    val userId: UUID,
)
