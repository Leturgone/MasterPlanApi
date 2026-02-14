package api.masterplan.app.userManagementModule.application.dto

import java.util.*

data class EmployeeInfo(
    val name: String,
    val surname: String,
    val patronymic: String? = null,
    val directorId: UUID? =  null
)
