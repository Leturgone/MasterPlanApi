package api.masterplan.app.adminRequestsModule.application.port

import java.util.*

interface RequestsNotificationPort {

    fun sendNewRequestNotification(message: String)

    fun sendRequestChangeStatusNotification(consumerId: UUID, message: String)
}