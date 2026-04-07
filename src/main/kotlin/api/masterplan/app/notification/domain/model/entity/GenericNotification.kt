package api.masterplan.app.notification.domain.model.entity

import api.masterplan.app.notification.domain.model.value.*
import java.time.LocalDateTime

data class GenericNotification(
    val notificationId: NotificationId,
    val notificationType: NotificationType,
    val title: NotificationTitle,
    val message: NotificationMessage,
    val timestamp: NotificationTimestamp
){
    companion object{
        fun create(notificationType: NotificationType, message: NotificationMessage): GenericNotification{
            return GenericNotification(
                notificationId = NotificationId.generate(),
                notificationType = notificationType,
                title = NotificationTitle.generate(notificationType),
                message = message,
                timestamp = NotificationTimestamp(LocalDateTime.now())
            )
        }
    }
}