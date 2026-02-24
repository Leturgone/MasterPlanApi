package api.masterplan.app.employeeModule.presentation.dto.responce

import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema(description = "Ответ ID Сотрудника")
data class EmployeeIdResponse (

    @Schema(description = "ID сотрудника UUIDv7",
        example = "06115aa098-9277-0087-49a8-cb901fc2f7"
    )
    val id: UUID

)



