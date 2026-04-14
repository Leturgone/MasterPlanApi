package api.masterplan.app.reportsModule.domain.models.entity

import api.masterplan.app.reportsModule.domain.models.value.*
import java.time.LocalDateTime

@ConsistentCopyVisibility
data class Report private constructor (
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
){
    companion object{

        fun create(id: ReportId? = null, title: ReportTitle, description: ReportDescription? = null,
                   employeeId: ReportEmployeeId, referenceId: ReportReferenceId, documentId: ReportDocumentId
        ): Report{
            val type = when(referenceId){
                is ReportReferenceId.ForPlan -> ReportType.PLAN
                is ReportReferenceId.ForTask -> ReportType.TASK
            }
            val reportStatus = when(type){
                ReportType.TASK -> ReportStatus.NOT_CHECKED
                ReportType.PLAN -> ReportStatus.CHECKED
            }
            return Report(
                id = id ?: ReportId.generate(),
                title = title,
                creationDate = ReportDate(LocalDateTime.now()),
                editDate = null,
                description = description,
                reportStatus = reportStatus,
                employeeId = employeeId,
                referenceId = referenceId,
                type = type,
                documentId = documentId,
            )
        }

        fun create(id: ReportId, title: ReportTitle, creationDate: ReportDate, editDate: ReportDate? = null, description: ReportDescription? = null,
                   reportStatus: ReportStatus, employeeId: ReportEmployeeId,referenceId: ReportReferenceId, documentId: ReportDocumentId): Report {
            val type = when(referenceId){
                is ReportReferenceId.ForPlan -> ReportType.PLAN
                is ReportReferenceId.ForTask -> ReportType.TASK
            }
            return Report(
                id = id,
                title = title,
                creationDate = creationDate,
                editDate = editDate,
                description = description,
                reportStatus = reportStatus,
                employeeId = employeeId,
                referenceId = referenceId,
                type = type,
                documentId = documentId,
            )
        }
    }

    fun changeReportStatus(status: ReportStatus): Report{
        return this.copy(reportStatus = status)
    }

    fun update(title: ReportTitle, description: ReportDescription?, documentId: ReportDocumentId):Report{
        return this.copy(
            title = title,
            description = description,
            reportStatus = ReportStatus.NOT_CHECKED,
            editDate = ReportDate(LocalDateTime.now()),
            documentId = documentId, )
    }
}
