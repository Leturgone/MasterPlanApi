package api.masterplan.app.reportsModule.application.mapper

import api.masterplan.app.reportsModule.domain.dtos.ReportDetails
import api.masterplan.app.reportsModule.domain.models.entity.Report

object ReportToDetailsMapper {

    fun toReportDetails(report: Report): ReportDetails {
        return ReportDetails(
            id = report.id,
            title = report.title,
            creationDate = report.creationDate,
            editDate = report.editDate,
            description = report.description,
            reportStatus = report.reportStatus,
            employeeId = report.employeeId,
            referenceId = report.referenceId,
            type = report.type,
            documentId = report.documentId,
        )
    }
}