package api.masterplan.app.reportsModule.application.ports

import api.masterplan.app.reportsModule.application.dto.ReportFile
import api.masterplan.app.reportsModule.domain.models.value.ReportDocumentId

interface ReportFilesPort {

    fun uploadReportFile(reportFile: ReportFile): ReportDocumentId

    fun removeReportFile(reportFileId: ReportDocumentId): ReportDocumentId

    fun updateReportFile(reportFileId: ReportDocumentId?, reportFile: ReportFile): ReportDocumentId

}