package api.masterplan.app.adminRequestsModule.application.command

import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestSenderId

data class GetCreatedAdminRequestsBySenderListCommand(
    val senderId: AdminRequestSenderId
)