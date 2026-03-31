package api.masterplan.app.adminRequestsModule.application.port

import api.masterplan.app.adminRequestsModule.application.dto.RequestNotification
import java.util.*

interface RequestsNotificationPort {

    fun sendNewRequestNotification(notification: RequestNotification)

    fun sendRequestChangeStatusNotification(consumerId: UUID, notification: RequestNotification)
}