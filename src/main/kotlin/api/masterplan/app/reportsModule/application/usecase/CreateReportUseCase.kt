package api.masterplan.app.reportsModule.application.usecase

import api.masterplan.app.reportsModule.application.command.CreateReportCommand
import api.masterplan.app.reportsModule.application.ports.ReportFilesPort
import api.masterplan.app.reportsModule.domain.interfaces.ReportService
import api.masterplan.app.reportsModule.domain.models.value.ReportId
import org.springframework.stereotype.Service

@Service
class CreateReportUseCase(
    private val reportService: ReportService,
    private val reportFilesPort: ReportFilesPort
) {
    operator fun invoke(command: CreateReportCommand): Result<ReportId> {
        return try {
            val planReportFileId = reportFilesPort.uploadReportFile(command.document)
            val planReportId = reportService.createReport(
                id = command.id,
                title = command.title,
                description = command.description,
                employeeId = command.employeeId,
                referenceId = command.referenceId,
                documentId = planReportFileId,
            )
            Result.success(planReportId)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}