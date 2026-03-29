package api.masterplan.app.adminRequestsModule.application.command

import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestId

data class GetAdminRequestCommand(
    val id: AdminRequestId
)