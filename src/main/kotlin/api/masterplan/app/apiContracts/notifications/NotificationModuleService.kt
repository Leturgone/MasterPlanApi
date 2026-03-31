package api.masterplan.app.apiContracts.notifications

import java.util.*

interface NotificationModuleService {

    fun sendTaskAssignmentNotification(consumerId: UUID, message:String)

    fun sendTaskChangeStatusNotification(consumerId: UUID, message:String)

    fun sendNewRequestNotification(message:String)

    fun sendRequestChangeStatusNotification(consumerId: UUID, message:String)

    fun sendNewReportNotification(consumerId: UUID, message:String)

    fun sendReportChangeStatusNotification(consumerId: UUID, message:String)
}