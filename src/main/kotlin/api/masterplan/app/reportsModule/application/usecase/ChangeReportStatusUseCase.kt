package api.masterplan.app.reportsModule.application.usecase

import api.masterplan.app.reportsModule.application.command.ChangeReportStatusCommand
import api.masterplan.app.reportsModule.application.ports.ReportNotificationPort
import api.masterplan.app.reportsModule.domain.interfaces.ReportService
import api.masterplan.app.reportsModule.domain.models.value.ReportId
import org.springframework.stereotype.Service

@Service
class ChangeReportStatusUseCase(
    private val reportService: ReportService,
    private val reportNotificationPort: ReportNotificationPort
) {
    operator fun invoke(command: ChangeReportStatusCommand): Result<ReportId> {
        return try {
            val report = reportService.changeReportStatus(
                reportId = command.reportId,
                reportType = command.reportType,
                status = command.status
            )
            reportNotificationPort.sendReportChangeStatusNotification(
                employeeId = report.employeeId,
                reportTitle = report.title,
                reportStatus = report.reportStatus,
            )
            Result.success(report.id)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}