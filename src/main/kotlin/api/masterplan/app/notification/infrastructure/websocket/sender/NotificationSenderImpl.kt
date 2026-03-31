package api.masterplan.app.notification.infrastructure.websocket.sender

import api.masterplan.app.logging.annotations.LoggingNotificationMethod
import api.masterplan.app.notification.domain.interfaces.NotificationSender
import api.masterplan.app.notification.domain.model.entity.GenericNotification
import api.masterplan.app.notification.domain.model.value.CustomerId
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Component

@Component
class NotificationSenderImpl(
    private val messagingTemplate: SimpMessagingTemplate
): NotificationSender {

    @LoggingNotificationMethod
    override fun sendPushNotification(customerId: CustomerId, notification: GenericNotification) {
        val destination = "/topic/notifications/user/${customerId.value}"
        messagingTemplate.convertAndSend(destination, notification)
    }

    @LoggingNotificationMethod
    override fun sendPushAdminNotification(notification: GenericNotification) {
        val destination = "/topic/notifications/admin"
        messagingTemplate.convertAndSend(destination, notification)
    }
}