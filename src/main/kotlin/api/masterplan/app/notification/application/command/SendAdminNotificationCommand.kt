package api.masterplan.app.notification.application.command

import api.masterplan.app.notification.domain.model.value.CustomerId
import api.masterplan.app.notification.domain.model.value.NotificationChannel
import api.masterplan.app.notification.domain.model.value.NotificationMessage
import api.masterplan.app.notification.domain.model.value.NotificationTitle
import api.masterplan.app.notification.domain.model.value.NotificationType

data class SendAdminNotificationCommand(
    val channel: NotificationChannel,
    val notificationType: NotificationType,
    val title: NotificationTitle,
    val message: NotificationMessage
)