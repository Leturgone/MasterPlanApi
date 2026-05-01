package api.masterplan.app.notification.application.service

import api.masterplan.app.logging.annotations.LoggingMethod
import api.masterplan.app.notification.domain.exception.NotificationException
import api.masterplan.app.notification.domain.interfaces.NotificationSender
import api.masterplan.app.notification.domain.interfaces.NotificationService
import api.masterplan.app.notification.domain.model.entity.GenericNotification
import api.masterplan.app.notification.domain.model.value.CustomerId
import api.masterplan.app.notification.domain.model.value.NotificationChannel
import api.masterplan.app.notification.domain.model.value.NotificationMessage
import api.masterplan.app.notification.domain.model.value.NotificationTitle
import api.masterplan.app.notification.domain.model.value.NotificationType
import org.springframework.stereotype.Service

@Service
class NotificationServiceImpl(
    private val notificationSender: NotificationSender
): NotificationService {

    @LoggingMethod("notificationModule")
    override fun sendNotification(
        customerId: CustomerId,
        channel: NotificationChannel,
        notificationType: NotificationType,
        message: NotificationMessage
    ) {
        val notification = GenericNotification.create(
            message = message,
            notificationType = notificationType,
        )

        when(channel){
            NotificationChannel.PUSH -> try {
                notificationSender.sendPushNotification(customerId, notification)
            }catch (e : Exception){
                throw NotificationException.FailedToSendPushNotification(e.message)
            }
        }
    }

    @LoggingMethod("notificationModule")
    override fun sendAdminNotification(
        channel: NotificationChannel,
        notificationType: NotificationType,
        message: NotificationMessage
    ) {
        val notification = GenericNotification.create(
            message = message,
            notificationType = notificationType,
        )

        when(channel){
            NotificationChannel.PUSH -> try {
                notificationSender.sendPushAdminNotification(notification)
            }catch (e : Exception){
                throw NotificationException.FailedToSendPushNotification(e.message)
            }
        }
    }


}