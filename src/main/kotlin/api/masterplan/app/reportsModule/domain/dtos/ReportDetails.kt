package api.masterplan.app.reportsModule.domain.dtos

import api.masterplan.app.reportsModule.domain.models.value.*

data class ReportDetails(
    val id: ReportId,
    val title: ReportTitle,
    val creationDate: ReportDate,
    val editDate: ReportDate? = null,
    val description: ReportDescription? = null,
    val reportStatus: ReportStatus,
    val employeeId: ReportEmployeeId,
    val referenceId: ReportReferenceId,
    val type: ReportType,
    val documentId: ReportDocumentId
)