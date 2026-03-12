package api.masterplan.app.reportsModule.infrastructure.adapters

import api.masterplan.app.reportsModule.application.dto.ReportFile
import api.masterplan.app.reportsModule.application.ports.ReportFilesPort
import api.masterplan.app.reportsModule.domain.models.value.ReportDocumentId
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Component
class ReportFilesAdapter(): ReportFilesPort {
    override fun uploadReportFile(reportFile: ReportFile): ReportDocumentId {
        println("Uploading report")
        TODO("Not yet implemented")
    }

    override fun removeReportFile(reportFileId: ReportDocumentId): ReportDocumentId {
        println("Removing report")
        TODO("Not yet implemented")
    }

    override fun updateReportFile(
        reportFileId: ReportDocumentId,
        reportFile: ReportFile
    ): ReportDocumentId {
        println("Updating report")
        TODO("Not yet implemented")
    }
}