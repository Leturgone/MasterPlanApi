package api.masterplan.app.notification.infrastructure.intermodule

import api.masterplan.app.apiContracts.notification.NotificationDto
import api.masterplan.app.apiContracts.notification.NotificationModuleService
import api.masterplan.app.notification.application.command.SendAdminNotificationCommand
import api.masterplan.app.notification.application.command.SendNotificationCommand
import api.masterplan.app.notification.application.usecase.SendAdminNotificationUseCase
import api.masterplan.app.notification.application.usecase.SendNotificationUseCase
import api.masterplan.app.notification.domain.model.value.*
import org.springframework.stereotype.Service
import java.util.*

@Service
class NotificationModuleServiceImpl(
    private val sendNotificationUseCase: SendNotificationUseCase,
    private val sendAdminNotificationUseCase: SendAdminNotificationUseCase
): NotificationModuleService {

    override fun sendTaskAssignmentNotification(consumerId: UUID, notification: NotificationDto) {
        val command = SendNotificationCommand(
            customerId = CustomerId(consumerId),
            channel = NotificationChannel.PUSH,
            notificationType = NotificationType.TASK_ASSIGNMENT,
            title = NotificationTitle.validate(notification.title),
            message = NotificationMessage.validate(notification.message)
        )
        sendNotificationUseCase(command)
    }

    override fun sendTaskChangeStatusNotification(
        consumerId: UUID,
        notification: NotificationDto
    ) {
        val command = SendNotificationCommand(
            customerId = CustomerId(consumerId),
            channel = NotificationChannel.PUSH,
            notificationType = NotificationType.TASK_CHANGE_STATUS,
            title = NotificationTitle.validate(notification.title),
            message = NotificationMessage.validate(notification.message)
        )
        sendNotificationUseCase(command)
    }

    override fun sendNewRequestNotification(notification: NotificationDto) {
        val command = SendAdminNotificationCommand(
            channel = NotificationChannel.PUSH,
            notificationType = NotificationType.NEW_REQUEST,
            title = NotificationTitle.validate(notification.title),
            message = NotificationMessage.validate(notification.message)
        )
        sendAdminNotificationUseCase(command)
    }

    override fun sendRequestChangeStatusNotification(
        consumerId: UUID,
        notification: NotificationDto
    ) {
        val command = SendNotificationCommand(
            customerId = CustomerId(consumerId),
            channel = NotificationChannel.PUSH,
            notificationType = NotificationType.REQUEST_CHANGE_STATUS,
            title = NotificationTitle.validate(notification.title),
            message = NotificationMessage.validate(notification.message)
        )
        sendNotificationUseCase(command)
    }

    override fun sendNewReportNotification(consumerId: UUID, notification: NotificationDto) {
        val command = SendNotificationCommand(
            customerId = CustomerId(consumerId),
            channel = NotificationChannel.PUSH,
            notificationType = NotificationType.NEW_REPORT,
            title = NotificationTitle.validate(notification.title),
            message = NotificationMessage.validate(notification.message)
        )
        sendNotificationUseCase(command)
    }

    override fun sendNewReportChangeStatusNotification(consumerId: UUID, notification: NotificationDto) {
        val command = SendNotificationCommand(
            customerId = CustomerId(consumerId),
            channel = NotificationChannel.PUSH,
            notificationType = NotificationType.REPORT_CHANGE_STATUS,
            title = NotificationTitle.validate(notification.title),
            message = NotificationMessage.validate(notification.message)
        )
        sendNotificationUseCase(command)
    }
}