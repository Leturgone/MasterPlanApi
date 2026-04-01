package api.masterplan.app.reportsModule.domain.interfaces

import api.masterplan.app.reportsModule.domain.models.entity.Report
import api.masterplan.app.reportsModule.domain.models.value.ReportEmployeeId
import api.masterplan.app.reportsModule.domain.models.value.ReportId
import api.masterplan.app.reportsModule.domain.models.value.ReportTitle

interface TaskReportRepository {
    fun getTaskReport(taskReportId: ReportId): Report?

    fun deleteTaskReport(taskReportId: ReportId): ReportId?

    fun saveTaskReport(report: Report): ReportId?

    fun getTaskReportsByEmployeeId(employeeId: ReportEmployeeId): List<Report>

    fun getTaskReportByEmployeeIds(employeeIds: Set<ReportEmployeeId>): List<Report>

    fun isTaskReportExist(employeeId: ReportEmployeeId, taskReportTitle: ReportTitle): Boolean

    fun updateTaskReport(taskReportId: ReportId, updatedReport: Report): Report?
}