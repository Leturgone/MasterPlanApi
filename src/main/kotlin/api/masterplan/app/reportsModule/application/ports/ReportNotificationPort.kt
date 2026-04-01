package api.masterplan.app.reportsModule.application.ports

import api.masterplan.app.reportsModule.domain.models.value.ReportEmployeeId
import api.masterplan.app.reportsModule.domain.models.value.ReportStatus
import api.masterplan.app.reportsModule.domain.models.value.ReportTitle

interface ReportNotificationPort {
    fun sendNewReportNotification(directorId: ReportEmployeeId, reportTitle: ReportTitle)

    fun sendReportChangeStatusNotification(employeeId: ReportEmployeeId, reportTitle: ReportTitle, reportStatus: ReportStatus)

    fun sendUpdateReportNotification(directorId: ReportEmployeeId, reportTitle: ReportTitle)
}