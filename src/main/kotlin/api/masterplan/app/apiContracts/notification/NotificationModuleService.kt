package api.masterplan.app.apiContracts.notification

import java.util.*

interface NotificationModuleService {

    fun sendTaskAssignmentNotification(consumerId: UUID, message:String)

    fun sendTaskChangeStatusNotification(consumerId: UUID, message:String)

    fun sendNewRequestNotification(message:String)

    fun sendRequestChangeStatusNotification(consumerId: UUID, message:String)

    fun sendNewReportNotification(consumerId: UUID, message:String)

    fun sendNewReportChangeStatusNotification(consumerId: UUID, message:String)
}