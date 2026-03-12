package api.masterplan.app.reportsModule.domain.models.entity

import api.masterplan.app.reportsModule.domain.models.value.*
import java.time.LocalDateTime

@ConsistentCopyVisibility
data class PlanReport private constructor(
    val id: PlanReportId,
    val title: PlanReportTitle,
    val creationDate: PlanReportDate,
    val editDate: PlanReportDate? = null,
    val description: PlanReportDescription? = null,
    val reportStatus: ReportStatus,
    val employeeId: ReportEmployeeId,
    val taskId: ReportTaskId,
    val documentId: ReportDocumentId
){
    companion object{
        fun create(id: PlanReportId? = null,title: PlanReportTitle, description: PlanReportDescription? = null,
                   employeeId: ReportEmployeeId, taskId: ReportTaskId, documentId: ReportDocumentId
        ): PlanReport{
            return PlanReport(
                id = id?: PlanReportId.generate(),
                title = title,
                creationDate = PlanReportDate(LocalDateTime.now()),
                editDate = null,
                description = description,
                reportStatus = ReportStatus.NOT_CHECKED,
                employeeId = employeeId,
                taskId = taskId,
                documentId = documentId
            )
        }
    }

    fun changePlanReportStatus(taskReportStatus: ReportStatus): PlanReport{
        return this.copy(reportStatus = taskReportStatus)
    }

    fun update(title: PlanReportTitle,description: PlanReportDescription?,documentId: ReportDocumentId):PlanReport{
        return this.copy(
            title = title,
            description = description,
            reportStatus = ReportStatus.NOT_CHECKED,
            editDate = PlanReportDate(LocalDateTime.now()),
            documentId = documentId, )
    }
}