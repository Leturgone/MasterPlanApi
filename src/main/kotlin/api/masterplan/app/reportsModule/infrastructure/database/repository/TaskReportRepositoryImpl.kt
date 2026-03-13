package api.masterplan.app.reportsModule.infrastructure.database.repository

import api.masterplan.app.reportsModule.domain.interfaces.TaskReportRepository
import api.masterplan.app.reportsModule.domain.models.entity.Report
import api.masterplan.app.reportsModule.domain.models.value.ReportEmployeeId
import api.masterplan.app.reportsModule.domain.models.value.ReportId
import api.masterplan.app.reportsModule.domain.models.value.ReportTitle
import org.springframework.stereotype.Repository

@Repository
class TaskReportRepositoryImpl(
    private val jpaTaskReportRepository: JpaTaskReportRepository,
    private val jpaPlanStatusRepository: JpaReportStatusRepository
): TaskReportRepository {
    override fun getTaskReport(taskReportId: ReportId): Report? {
        TODO("Not yet implemented")
    }

    override fun deleteTaskReport(taskReportId: ReportId): ReportId? {
        TODO("Not yet implemented")
    }

    override fun saveTaskReport(report: Report): ReportId? {
        TODO("Not yet implemented")
    }

    override fun getTaskReportsByEmployeeId(employeeId: ReportEmployeeId): List<Report> {
        TODO("Not yet implemented")
    }

    override fun getTaskReportByEmployeeIds(employeeIds: Set<ReportEmployeeId>): List<Report> {
        TODO("Not yet implemented")
    }

    override fun isTaskReportExist(employeeId: ReportEmployeeId, taskReportTitle: ReportTitle): Boolean {
        TODO("Not yet implemented")
    }

    override fun updateTaskReport(taskReportId: ReportId, updatedReport: Report): ReportId? {
        TODO("Not yet implemented")
    }
}