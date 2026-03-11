package api.masterplan.app.reportsModule.application.service

import api.masterplan.app.logging.LoggingMethod
import api.masterplan.app.reportsModule.application.mapper.ReportToEntityMapper
import api.masterplan.app.reportsModule.domain.dtos.TaskReportDetails
import api.masterplan.app.reportsModule.domain.exceptions.ReportException
import api.masterplan.app.reportsModule.domain.interfaces.TaskReportRepository
import api.masterplan.app.reportsModule.domain.interfaces.TaskReportService
import api.masterplan.app.reportsModule.domain.models.entity.TaskReport
import api.masterplan.app.reportsModule.domain.models.value.*
import org.springframework.stereotype.Service

@Service
class TaskReportServiceImpl(
    private val taskReportRepository: TaskReportRepository
): TaskReportService {

    @LoggingMethod("reportModule")
    override fun getTaskReport(reportId: TaskReportId): TaskReportDetails {
        val report = taskReportRepository.getTaskReport(reportId)
            ?: throw ReportException.TaskReportNotExist(reportId)

        return ReportToEntityMapper.toTaskReportDetails(report)
    }

    @LoggingMethod("reportModule")
    override fun updateTaskReport(reportId: TaskReportId, updatedTaskReport: TaskReport): TaskReportId {
        val report = taskReportRepository.getTaskReport(reportId)
            ?: throw ReportException.TaskReportNotExist(reportId)

        val updatedReport = report.update(
            title = updatedTaskReport.title,
            description = updatedTaskReport.description,
            documentId = updatedTaskReport.documentId,
        )

        val updatedReportId = taskReportRepository.updatePlanReport(reportId, updatedReport)
            ?: throw ReportException.FailedToUpdateTaskReport(reportId)

        return updatedReportId
    }

    @LoggingMethod("reportModule")
    override fun deleteTaskReport(reportId: TaskReportId): TaskReportId {
        val deletedReportId = taskReportRepository.deleteTaskReport(reportId)
            ?: throw ReportException.FailedToDeleteTaskReport(reportId)

        return deletedReportId
    }

    @LoggingMethod("reportModule")
    override fun createTaskReport(id: TaskReportId?, title: TaskReportTitle, description: TaskReportDescription?,
        employeeId: ReportEmployeeId, taskId: ReportTaskId, documentId: ReportDocumentId
    ): TaskReportId {
        if (taskReportRepository.isPlanReportExist(employeeId,title)) throw ReportException.TaskReportAlreadyExist(employeeId,title)

        val taskReportEntity = TaskReport.create(
            id = id,
            title = title,
            description = description,
            employeeId = employeeId,
            taskId = taskId,
            documentId = documentId,
        )

        val taskReportId = taskReportRepository.saveTaskReport(taskReportEntity)
            ?: throw ReportException.FailedToSaveTaskReport(employeeId,title)

        return taskReportId
    }

    @LoggingMethod("reportModule")
    override fun getCreatedTaskReports(employeeId: ReportEmployeeId): List<TaskReportDetails> {
        val reportList = taskReportRepository.getTaskReportsByEmployeeId(employeeId)

        return reportList.map { ReportToEntityMapper.toTaskReportDetails(it) }
    }


    @LoggingMethod("reportModule")
    override fun filterCreatedTaskByStatus(employeeId: ReportEmployeeId, status: TaskReportStatus): List<TaskReportDetails> {
        val reportList = taskReportRepository.getTaskReportsByEmployeeId(employeeId)

        return reportList.filter { taskReport ->
            taskReport.reportStatus == status
        }.map {
            ReportToEntityMapper.toTaskReportDetails(it)
        }
    }

    @LoggingMethod("reportModule")
    override fun getSubordinatesTaskReports(subordinatesIds: Set<ReportEmployeeId>): List<TaskReportDetails> {
        val reportList = taskReportRepository.getTaskReportByEmployeeIds(subordinatesIds)

        return reportList.map { ReportToEntityMapper.toTaskReportDetails(it) }
    }

    @LoggingMethod("reportModule")
    override fun filterSubordinatesTaskReportsByStatus(subordinatesIds: Set<ReportEmployeeId>, status: TaskReportStatus): List<TaskReportDetails> {
        val reportList = taskReportRepository.getTaskReportByEmployeeIds(subordinatesIds)

        return reportList.filter { taskReport ->
            taskReport.reportStatus == status
        }.map {
            ReportToEntityMapper.toTaskReportDetails(it)
        }
    }

    @LoggingMethod("reportModule")
    override fun changeTaskReportStatus(reportId: TaskReportId, status: TaskReportStatus): TaskReportId {
        val report = taskReportRepository.getTaskReport(reportId)
            ?: throw ReportException.TaskReportNotExist(reportId)

        val reportWithNewStatus = report.changeTaskReportStatus(status)

        val updatedReportId = taskReportRepository.updatePlanReport(reportId, reportWithNewStatus)
            ?: throw ReportException.FailedToUpdateTaskReport(reportId)

        return updatedReportId
    }
}