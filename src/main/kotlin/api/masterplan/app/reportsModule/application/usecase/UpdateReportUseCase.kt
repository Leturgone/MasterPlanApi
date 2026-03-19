package api.masterplan.app.reportsModule.application.usecase

import api.masterplan.app.reportsModule.application.command.UpdateReportCommand
import api.masterplan.app.reportsModule.application.ports.ReportFilesPort
import api.masterplan.app.reportsModule.domain.interfaces.ReportService
import api.masterplan.app.reportsModule.domain.models.value.ReportId
import org.springframework.stereotype.Service

@Service
class UpdateReportUseCase(
    private val reportService: ReportService,
    private val reportFilesPort: ReportFilesPort
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
            Result.success(updatedReport)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}