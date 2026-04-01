package api.masterplan.app.reportsModule.infrastructure.adapters

import api.masterplan.app.apiContracts.notifications.NotificationModuleService
import api.masterplan.app.reportsModule.application.ports.ReportNotificationPort
import api.masterplan.app.reportsModule.domain.models.value.ReportEmployeeId
import api.masterplan.app.reportsModule.domain.models.value.ReportStatus
import api.masterplan.app.reportsModule.domain.models.value.ReportTitle
import org.springframework.stereotype.Component

@Component
class ReportNotificationAdapter(
    private val notificationService: NotificationModuleService
): ReportNotificationPort {

    override fun sendNewReportNotification(directorId: ReportEmployeeId, reportTitle: ReportTitle) {
        val notificationMessage = "Новый отчет на проверку: ${reportTitle.value}"
        notificationService.sendNewReportNotification(directorId.value, notificationMessage)
    }

    override fun sendReportChangeStatusNotification(
        employeeId: ReportEmployeeId,
        reportTitle: ReportTitle,
        reportStatus: ReportStatus
    ) {
        val notificationMessage = "Cтатус отчета ${reportTitle.value} изменен на ${reportStatus.name}"
        notificationService.sendReportChangeStatusNotification(employeeId.value, notificationMessage)
    }

    override fun sendUpdateReportNotification(
        directorId: ReportEmployeeId,
        reportTitle: ReportTitle
    ) {
        val notificationMessage = "Отчет ${reportTitle.value} обновлен"
        notificationService.sendReportUpdatedNotification(directorId.value, notificationMessage)
    }
}