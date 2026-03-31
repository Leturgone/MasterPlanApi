package api.masterplan.app.notification.application.usecase

import api.masterplan.app.notification.application.command.SendNotificationCommand
import api.masterplan.app.notification.domain.interfaces.NotificationService
import org.springframework.stereotype.Service

@Service
class SendNotificationUseCase(
    private val notificationService: NotificationService
) {
    operator fun invoke(command: SendNotificationCommand): Result<Unit>{
        return try {
            val result = notificationService.sendNotification(
                customerId = command.customerId,
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