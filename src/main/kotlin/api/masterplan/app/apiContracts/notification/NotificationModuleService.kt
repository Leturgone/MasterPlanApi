package api.masterplan.app.apiContracts.notification

import java.util.*

interface NotificationModuleService {

    fun sendTaskAssignmentNotification(consumerId: UUID, notification: NotificationDto)

    fun sendTaskChangeStatusNotification(consumerId: UUID, notification: NotificationDto)

    fun sendNewRequestNotification(notification: NotificationDto)

    fun sendRequestChangeStatusNotification(consumerId: UUID, notification: NotificationDto)

    fun sendNewReportNotification(consumerId: UUID, notification: NotificationDto)

    fun sendNewReportChangeStatusNotification(consumerId: UUID, notification: NotificationDto)
}