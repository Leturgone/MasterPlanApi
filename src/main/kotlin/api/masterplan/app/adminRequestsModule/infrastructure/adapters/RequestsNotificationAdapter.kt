package api.masterplan.app.adminRequestsModule.infrastructure.adapters

import api.masterplan.app.adminRequestsModule.application.port.RequestsNotificationPort
import api.masterplan.app.apiContracts.notifications.NotificationModuleService
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class RequestsNotificationAdapter(
    private val notificationService: NotificationModuleService
): RequestsNotificationPort {

    override fun sendNewRequestNotification(message: String) {
        notificationService.sendNewRequestNotification(message)
    }

    override fun sendRequestChangeStatusNotification(consumerId: UUID, message: String) {
        notificationService.sendRequestChangeStatusNotification(consumerId, message)
    }
}