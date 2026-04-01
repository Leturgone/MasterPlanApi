package api.masterplan.app.reportsModule.application.usecase

import api.masterplan.app.reportsModule.application.command.CreateReportCommand
import api.masterplan.app.reportsModule.application.ports.ReportEmployeesPort
import api.masterplan.app.reportsModule.application.ports.ReportFilesPort
import api.masterplan.app.reportsModule.application.ports.ReportNotificationPort
import api.masterplan.app.reportsModule.domain.interfaces.ReportService
import api.masterplan.app.reportsModule.domain.models.value.ReportId
import org.springframework.stereotype.Service

@Service
class CreateReportUseCase(
    private val reportService: ReportService,
    private val reportEmployeePort: ReportEmployeesPort,
    private val reportFilesPort: ReportFilesPort,
    private val notificationPort: ReportNotificationPort
) {
    operator fun invoke(command: CreateReportCommand): Result<ReportId> {
        return try {
            val reportFileId = reportFilesPort.uploadReportFile(command.document)
            val reportId = reportService.createReport(
                id = command.id,
                title = command.title,
                description = command.description,
                employeeId = command.employeeId,
                referenceId = command.referenceId,
                documentId = reportFileId,
            )

            val directorId = reportEmployeePort.getDirectorId(command.employeeId)
            directorId?.let {
                notificationPort.sendNewReportNotification(
                    directorId = directorId,
                    reportTitle = command.title,
                )
            }
            Result.success(reportId)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}