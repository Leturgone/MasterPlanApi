package api.masterplan.app.reportsModule.infrastructure.database.repository

import api.masterplan.app.logging.annotations.LoggingDatabaseMethod
import api.masterplan.app.reportsModule.domain.interfaces.TaskReportRepository
import api.masterplan.app.reportsModule.domain.models.entity.Report
import api.masterplan.app.reportsModule.domain.models.value.ReportEmployeeId
import api.masterplan.app.reportsModule.domain.models.value.ReportId
import api.masterplan.app.reportsModule.domain.models.value.ReportTitle
import api.masterplan.app.reportsModule.infrastructure.database.mapper.TaskReportDatabaseMapper
import org.springframework.stereotype.Repository
import kotlin.jvm.optionals.getOrElse

@Repository
class TaskReportRepositoryImpl(
    private val jpaTaskReportRepository: JpaTaskReportRepository,
    private val jpaPlanStatusRepository: JpaReportStatusRepository
): TaskReportRepository {

    @LoggingDatabaseMethod(moduleName = "reportModule")
    override fun getTaskReport(taskReportId: ReportId): Report? {
        val taskReport = jpaTaskReportRepository.findById(taskReportId.value).getOrElse { return null }
        return TaskReportDatabaseMapper.toDomain(taskReport)
    }


    @LoggingDatabaseMethod(moduleName = "reportModule")
    override fun deleteTaskReport(taskReportId: ReportId): ReportId? {
        jpaTaskReportRepository.deleteById(taskReportId.value)
        return taskReportId
    }


    @LoggingDatabaseMethod(moduleName = "reportModule")
    override fun saveTaskReport(report: Report): ReportId? {
        val statusSet = jpaPlanStatusRepository.findAll().toSet()
        val taskReportEntity = TaskReportDatabaseMapper.toEntity(report,statusSet)
        val taskReportId = jpaTaskReportRepository.save(taskReportEntity).id
        return ReportId(taskReportId)
    }


    @LoggingDatabaseMethod(moduleName = "reportModule")
    override fun getTaskReportsByEmployeeId(employeeId: ReportEmployeeId): List<Report> {
        val tasksReports = jpaTaskReportRepository.findByEmployeeId(employeeId.value)
        return TaskReportDatabaseMapper.toDomain(tasksReports)
    }


    @LoggingDatabaseMethod(moduleName = "reportModule")
    override fun getTaskReportByEmployeeIds(employeeIds: Set<ReportEmployeeId>): List<Report> {
        val empUuids = employeeIds.map { it.value }.toSet()
        val taskReports = jpaTaskReportRepository.findByEmployeeIds(empUuids)
        return TaskReportDatabaseMapper.toDomain(taskReports)
    }


    @LoggingDatabaseMethod(moduleName = "reportModule")
    override fun isTaskReportExist(employeeId: ReportEmployeeId, taskReportTitle: ReportTitle): Boolean {
        return jpaTaskReportRepository.existsByTitleAndEmployeeId(taskReportTitle.value, employeeId.value)
    }


    @LoggingDatabaseMethod(moduleName = "reportModule")
    override fun updateTaskReport(taskReportId: ReportId, updatedReport: Report): ReportId? {
        jpaTaskReportRepository.findById(taskReportId.value).getOrElse { return null }
        val statusSet = jpaPlanStatusRepository.findAll().toSet()
        val updatedTaskReportEntity = TaskReportDatabaseMapper.toEntity(updatedReport,statusSet)
        val taskReportId = jpaTaskReportRepository.save(updatedTaskReportEntity).id
        return ReportId(taskReportId)
    }
}