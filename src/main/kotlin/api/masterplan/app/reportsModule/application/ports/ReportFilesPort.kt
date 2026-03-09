package api.masterplan.app.reportsModule.application.ports

import api.masterplan.app.reportsModule.application.dto.ReportFile
import api.masterplan.app.reportsModule.domain.models.value.ReportDocumentId

interface ReportFilesPort {

    fun uploadPReportFile(reportFile: ReportFile): ReportDocumentId

    fun removeReportFile(reportFileId: ReportDocumentId): ReportDocumentId

    fun uploadOrUpdateReportFile(reportFileId: ReportDocumentId?, reportFile: ReportFile): ReportDocumentId

}