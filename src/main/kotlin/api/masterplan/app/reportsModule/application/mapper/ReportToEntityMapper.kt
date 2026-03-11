package api.masterplan.app.reportsModule.application.mapper

import api.masterplan.app.reportsModule.domain.dtos.PlanReportDetails
import api.masterplan.app.reportsModule.domain.dtos.TaskReportDetails
import api.masterplan.app.reportsModule.domain.models.entity.PlanReport
import api.masterplan.app.reportsModule.domain.models.entity.TaskReport

object ReportToEntityMapper {

    fun toPlanReportDetails(report: PlanReport): PlanReportDetails {
        return PlanReportDetails(
            id = report.id,
            title = report.title,
            creationDate = report.creationDate,
            editDate = report.editDate,
            description = report.description,
            reportStatus = report.reportStatus,
            employeeId = report.employeeId,
            taskId = report.taskId,
            documentId = report.documentId,
        )
    }

    fun toTaskReportDetails(report: TaskReport): TaskReportDetails {
        return TaskReportDetails(
            id = report.id,
            title = report.title,
            creationDate = report.creationDate,
            editDate = report.editDate,
            description = report.description,
            reportStatus = report.reportStatus,
            employeeId = report.employeeId,
            taskId = report.taskId,
            documentId = report.documentId
        )
    }
}