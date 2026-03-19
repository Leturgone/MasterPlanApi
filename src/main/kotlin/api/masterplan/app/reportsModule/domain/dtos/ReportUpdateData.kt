package api.masterplan.app.reportsModule.domain.dtos

import api.masterplan.app.reportsModule.domain.models.value.ReportDescription
import api.masterplan.app.reportsModule.domain.models.value.ReportDocumentId
import api.masterplan.app.reportsModule.domain.models.value.ReportTitle

data class ReportUpdateData(
    val title: ReportTitle,
    val description: ReportDescription?,
    val documentId: ReportDocumentId
)
