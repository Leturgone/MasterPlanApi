package api.masterplan.app.notification.application.usecase

import api.masterplan.app.notification.application.command.SendAdminNotificationCommand
import api.masterplan.app.notification.domain.interfaces.NotificationService
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
                message = command.message
            )
            Result.success(result)
        }catch (ex: Exception){
            Result.failure(ex)
        }
    }
}