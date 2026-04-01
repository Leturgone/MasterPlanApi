package api.masterplan.app.reportsModule.application.usecase

import api.masterplan.app.reportsModule.application.command.UpdateReportCommand
import api.masterplan.app.reportsModule.application.ports.ReportEmployeesPort
import api.masterplan.app.reportsModule.application.ports.ReportFilesPort
import api.masterplan.app.reportsModule.application.ports.ReportNotificationPort
import api.masterplan.app.reportsModule.domain.interfaces.ReportService
import api.masterplan.app.reportsModule.domain.models.value.ReportId
import org.springframework.stereotype.Service

@Service
class UpdateReportUseCase(
    private val reportService: ReportService,
    private val reportFilesPort: ReportFilesPort,
    private val notificationPort: ReportNotificationPort,
    private val reportEmployeePort: ReportEmployeesPort
) {
    operator fun invoke(command: UpdateReportCommand):Result<ReportId>{
        return try {
            val updatedReport = reportService.updateReport(
                reportId = command.reportId,
                reportType = command.reportType,
                updatedData = command.updatedData,
            )

            reportFilesPort.updateReportFile(
                reportFileId = command.updatedData.documentId,
                reportFile = command.document
            )
            val directorId = reportEmployeePort.getDirectorId(updatedReport.employeeId)
            directorId?.let {
                notificationPort.sendUpdateReportNotification(
                    directorId = directorId,
                    reportTitle = updatedReport.title
                )
            }
            Result.success(updatedReport.id)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}