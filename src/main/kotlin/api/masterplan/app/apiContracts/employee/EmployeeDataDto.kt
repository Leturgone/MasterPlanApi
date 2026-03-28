package api.masterplan.app.apiContracts.employee

import java.util.*

data class EmployeeDataDto (
    val name: String,
    val surname: String,
    val patronymic: String? = null,
    val directorId: UUID? =  null,
    val userId: UUID,
)