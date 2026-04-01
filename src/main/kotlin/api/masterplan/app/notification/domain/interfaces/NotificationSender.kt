package api.masterplan.app.notification.domain.interfaces

import api.masterplan.app.notification.domain.model.entity.GenericNotification
import api.masterplan.app.notification.domain.model.value.CustomerId


interface NotificationSender {
    fun sendPushNotification(customerId: CustomerId, notification: GenericNotification)

    fun sendPushAdminNotification(notification: GenericNotification)
}