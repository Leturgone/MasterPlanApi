package api.masterplan.app.adminRequestsModule.application.command

import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestDescription
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestId
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestSenderId
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestTitle

data class CreateAdminRequestCommand(
    val id: AdminRequestId? = null,
    val title: AdminRequestTitle,
    val description: AdminRequestDescription,
    val senderId: AdminRequestSenderId
)