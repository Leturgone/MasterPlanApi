package api.masterplan.app.notification.application.usecase

import api.masterplan.app.notification.application.command.SendAdminNotificationCommand
import api.masterplan.app.notification.domain.interfaces.NotificationService
import api.masterplan.app.notification.domain.model.value.CustomerId
import api.masterplan.app.notification.domain.model.value.NotificationChannel
import api.masterplan.app.notification.domain.model.value.NotificationMessage
import api.masterplan.app.notification.domain.model.value.NotificationTitle
import api.masterplan.app.notification.domain.model.value.NotificationType
import org.springframework.stereotype.Service

@Service
class SendAdminNotificationUseCase(
    private val notificationService: NotificationService
){
    operator fun invoke(command: SendAdminNotificationCommand): Result<Unit>{
        return try {
            val result = notificationService.sendAdminNotification(
                channel = command.channel,
                notificationType = command.notificationType,
                title = command.title,
                message = command.message
            )
            Result.success(result)
        }catch (ex: Exception){
            Result.failure(ex)
        }
    }
}