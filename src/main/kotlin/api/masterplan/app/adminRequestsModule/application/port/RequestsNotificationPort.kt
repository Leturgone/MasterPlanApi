package api.masterplan.app.adminRequestsModule.application.port

import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestStatus
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestTitle
import java.util.*

interface RequestsNotificationPort {

    fun sendNewRequestNotification(requestTitle: AdminRequestTitle)

    fun sendRequestChangeStatusNotification(consumerId: UUID,requestTitle: AdminRequestTitle,requestStatus: AdminRequestStatus)
}