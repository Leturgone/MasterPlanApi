package api.masterplan.app.adminRequestsModule.infrastructure.adapters

import api.masterplan.app.adminRequestsModule.application.port.RequestsNotificationPort
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestStatus
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestTitle
import api.masterplan.app.apiContracts.notifications.NotificationModuleService
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class RequestsNotificationAdapter(
    private val notificationService: NotificationModuleService
): RequestsNotificationPort {

    override fun sendNewRequestNotification(requestTitle: AdminRequestTitle) {
        val notificationMessage = "Новая заявка: ${requestTitle.value}"
        notificationService.sendNewRequestNotification(notificationMessage)
    }

    override fun sendRequestChangeStatusNotification(consumerId: UUID,requestTitle: AdminRequestTitle,requestStatus: AdminRequestStatus) {
        val notificationMessage = "Статус заявки ${requestTitle.value} изменен на ${requestStatus.name}"
        notificationService.sendRequestChangeStatusNotification(consumerId, notificationMessage)
    }
}