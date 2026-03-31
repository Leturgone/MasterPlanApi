package api.masterplan.app.notification.domain.model.entity

import api.masterplan.app.notification.domain.model.value.NotificationMessage
import api.masterplan.app.notification.domain.model.value.NotificationTimestamp
import api.masterplan.app.notification.domain.model.value.NotificationTitle
import api.masterplan.app.notification.domain.model.value.NotificationType
import java.time.LocalDateTime

data class GenericNotification(
    val notificationType: NotificationType,
    val title: NotificationTitle,
    val message: NotificationMessage,
    val timestamp: NotificationTimestamp
){
    companion object{
        fun create(notificationType: NotificationType, message: NotificationMessage): GenericNotification{
            return GenericNotification(
                notificationType = notificationType,
                title = NotificationTitle.generate(notificationType),
                message = message,
                timestamp = NotificationTimestamp(LocalDateTime.now())
            )
        }
    }
}