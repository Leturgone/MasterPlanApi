package api.masterplan.app.adminRequestsModule.application.command

import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestId
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestStatus

data class ChangeAdminRequestStatusCommand(
    val id: AdminRequestId,
    val status: AdminRequestStatus
)