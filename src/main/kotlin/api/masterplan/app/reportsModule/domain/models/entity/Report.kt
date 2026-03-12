package api.masterplan.app.reportsModule.domain.models.entity

import api.masterplan.app.reportsModule.domain.models.value.ReportDate
import api.masterplan.app.reportsModule.domain.models.value.ReportDescription
import api.masterplan.app.reportsModule.domain.models.value.ReportDocumentId
import api.masterplan.app.reportsModule.domain.models.value.ReportEmployeeId
import api.masterplan.app.reportsModule.domain.models.value.ReportId
import api.masterplan.app.reportsModule.domain.models.value.ReportPlanId
import api.masterplan.app.reportsModule.domain.models.value.ReportReferenceId
import api.masterplan.app.reportsModule.domain.models.value.ReportStatus
import api.masterplan.app.reportsModule.domain.models.value.ReportTaskId
import api.masterplan.app.reportsModule.domain.models.value.ReportTitle
import api.masterplan.app.reportsModule.domain.models.value.ReportType
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

        fun createForTask(id: ReportId? = null, title: ReportTitle, description: ReportDescription? = null,
                   employeeId: ReportEmployeeId, taskId: ReportTaskId, documentId: ReportDocumentId
        ): Report{
            return Report(
                id = id ?: ReportId.generate(),
                title = title,
                creationDate = ReportDate(LocalDateTime.now()),
                editDate = null,
                description = description,
                reportStatus = ReportStatus.NOT_CHECKED,
                employeeId = employeeId,
                referenceId = ReportReferenceId.ForTask(taskId),
                type = ReportType.TASK,
                documentId = documentId,
            )
        }

        fun createForPlan(id: ReportId? = null, title: ReportTitle, description: ReportDescription? = null,
                          employeeId: ReportEmployeeId, planId: ReportPlanId, documentId: ReportDocumentId
        ): Report{
            return Report(
                id = id ?: ReportId.generate(),
                title = title,
                creationDate = ReportDate(LocalDateTime.now()),
                editDate = null,
                description = description,
                reportStatus = ReportStatus.NOT_CHECKED,
                employeeId = employeeId,
                referenceId = ReportReferenceId.ForPlan(planId),
                type = ReportType.PLAN,
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
