package api.masterplan.app.notification.domain.interfaces

import api.masterplan.app.notification.domain.model.value.CustomerId
import api.masterplan.app.notification.domain.model.value.NotificationChannel
import api.masterplan.app.notification.domain.model.value.NotificationMessage
import api.masterplan.app.notification.domain.model.value.NotificationType

interface NotificationService {

    fun sendNotification(customerId: CustomerId,
                         channel: NotificationChannel,
                         notificationType: NotificationType,
                         message: NotificationMessage)

    fun sendAdminNotification(channel: NotificationChannel,
                              notificationType: NotificationType,
                              message: NotificationMessage)
}