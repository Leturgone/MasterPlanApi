package api.masterplan.app.reportsModule.application.usecase

import api.masterplan.app.reportsModule.application.command.DeleteReportCommand
import api.masterplan.app.reportsModule.application.ports.ReportFilesPort
import api.masterplan.app.reportsModule.domain.interfaces.ReportService
import api.masterplan.app.reportsModule.domain.models.value.ReportId
import org.springframework.stereotype.Service

@Service
class DeleteReportUseCase(
    private val reportService: ReportService,
    private val reportFilesPort: ReportFilesPort
) {
    operator fun invoke(command: DeleteReportCommand): Result<ReportId> {
        return try {
            val deletedReport = reportService.getReport(command.reportId,command.reportType)
            val reportFileId = deletedReport.documentId

            val deletedReportId = reportService.deleteReport(command.reportId,command.reportType)
            reportFilesPort.removeReportFile(reportFileId)

            Result.success(deletedReportId)
        }catch (e: Exception){
            Result.failure(e)
        }

    }
}