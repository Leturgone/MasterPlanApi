package api.masterplan.app.adminRequestsModule.domain.dtos

import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestCreationDate
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestDescription
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestId
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestSenderId
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestStatus
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestTitle

data class AdminRequestDetails(
    val id: AdminRequestId,
    val title: AdminRequestTitle,
    val description: AdminRequestDescription,
    val creationDate: AdminRequestCreationDate,
    val senderId: AdminRequestSenderId,
    val status: AdminRequestStatus
)
