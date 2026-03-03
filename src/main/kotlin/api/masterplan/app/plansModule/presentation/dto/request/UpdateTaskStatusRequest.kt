package api.masterplan.app.plansModule.presentation.dto.request

import java.util.UUID

data class UpdateTaskStatusRequest(
    val taskId: UUID,
    val status: String
)