package api.masterplan.app.unitTests

import api.masterplan.app.reportsModule.application.mapper.ReportToDetailsMapper
import api.masterplan.app.reportsModule.application.service.ReportServiceImpl
import api.masterplan.app.reportsModule.domain.dtos.ReportDetails
import api.masterplan.app.reportsModule.domain.dtos.ReportUpdateData
import api.masterplan.app.reportsModule.domain.exceptions.ReportException
import api.masterplan.app.reportsModule.domain.interfaces.PlanReportRepository
import api.masterplan.app.reportsModule.domain.interfaces.TaskReportRepository
import api.masterplan.app.reportsModule.domain.models.entity.Report
import api.masterplan.app.reportsModule.domain.models.value.*
import api.masterplan.app.reportsModule.domain.models.value.ReportEmployeeId
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class ReportServiceUnitTest {

    // Данные для моков
    private val reportId = ReportId.generate()
    private val employeeId = ReportEmployeeId(UUID.randomUUID())
    private val title = ReportTitle.validate("Test Report")
    private val description = ReportDescription.validate("Test Description")
    private val documentId = ReportDocumentId(UUID.randomUUID())
    private val referenceIdForPlan = ReportReferenceId.ForPlan(ReportPlanId(UUID.randomUUID()))
    private val referenceIdForTask = ReportReferenceId.ForTask(ReportTaskId(UUID.randomUUID()))
    private val status = ReportStatus.CHECKED
    private val subordinatesIds = setOf(ReportEmployeeId(UUID.randomUUID()), ReportEmployeeId(UUID.randomUUID()))

    private val updatedData = ReportUpdateData(
        title = ReportTitle.validate("Updated Title"),
        description = ReportDescription.validate("Updated Description"),
        documentId = documentId
    )


    private val planReportRepository = mockk<PlanReportRepository>()
    private val taskReportRepository = mockk<TaskReportRepository>()
    private val reportService = ReportServiceImpl(planReportRepository, taskReportRepository)

    @Test
    fun `getReport return report details for task type when report exists`() {
        val taskReport = Report.create(
            id = reportId,
            title = title,
            description = description,
            employeeId = employeeId,
            referenceId = referenceIdForTask,
            documentId = documentId
        )
        val expectedDetails = ReportToDetailsMapper.toReportDetails(taskReport)

        every { taskReportRepository.getTaskReport(reportId) } returns taskReport
        val result = reportService.getReport(reportId, ReportType.TASK)
        assertEquals(expectedDetails, result)
    }

    @Test
    fun `getReport throw ReportNotExist when task report does not exist`() {
        every { taskReportRepository.getTaskReport(reportId) } returns null
        assertThrows<ReportException.ReportNotExist> {
            reportService.getReport(reportId, ReportType.TASK)
        }
    }

    @Test
    fun `getReport return report details for plan type when report exists`() {
        val planReport = Report.create(
            id = reportId,
            title = title,
            description = description,
            employeeId = employeeId,
            referenceId = referenceIdForPlan,
            documentId = documentId
        )
        val expectedDetails = ReportToDetailsMapper.toReportDetails(planReport)

        every { planReportRepository.getPlanReport(reportId) } returns planReport
        val result = reportService.getReport(reportId, ReportType.PLAN)
        assertEquals(expectedDetails, result)
    }

    @Test
    fun `updateReport update task report successfully`() {

        val existingReport = Report.create(
            id = reportId,
            title = title,
            description = description,
            employeeId = employeeId,
            referenceId = referenceIdForTask,
            documentId = documentId
        )
        val updatedReport = existingReport.update(
            title = updatedData.title,
            description = updatedData.description,
            documentId = updatedData.documentId
        )
        val expectedDetails = ReportToDetailsMapper.toReportDetails(updatedReport)

        every { taskReportRepository.getTaskReport(reportId) } returns existingReport
        every { taskReportRepository.updateTaskReport(reportId, any()) } returns updatedReport
        val result = reportService.updateReport(reportId, ReportType.TASK, updatedData)
        assertEquals(expectedDetails, result)
    }

    @Test
    fun `updateReport throw FailedToUpdateReport when failed to update report`() {
        val existingReport = Report.create(
            id = reportId,
            title = title,
            description = description,
            employeeId = employeeId,
            referenceId = referenceIdForPlan,
            documentId = documentId
        )

        every { planReportRepository.getPlanReport(reportId) } returns existingReport
        every { planReportRepository.updatePlanReport(reportId, any()) } returns null
        assertThrows<ReportException.FailedToUpdateReport> {
            reportService.updateReport(reportId, ReportType.PLAN, updatedData)
        }
    }

    @Test
    fun `deleteReport delete task report successfully`() {
        every { taskReportRepository.deleteTaskReport(reportId) } returns reportId
        val result = reportService.deleteReport(reportId, ReportType.TASK)
        assertEquals(reportId, result)
    }

    @Test
    fun `deleteReport throw FailedToDeleteReport when failed to delete report`() {
        every { planReportRepository.deletePlanReport(reportId) } returns null
        assertThrows<ReportException.FailedToDeleteReport> {
            reportService.deleteReport(reportId, ReportType.PLAN)
        }
    }

    @Test
    fun `createReport create task report successfully`() {
        every { taskReportRepository.isTaskReportExist(employeeId, title) } returns false
        every { taskReportRepository.saveTaskReport(any()) } returns reportId

        val result = reportService.createReport(
            reportId, title, description, employeeId, referenceIdForTask, documentId
        )

        assertEquals(reportId, result)
    }

    @Test
    fun `createReport throw ReportAlreadyExist when report already exists`() {
        every { planReportRepository.isPlanReportExist(employeeId, title) } returns true

        assertThrows<ReportException.ReportAlreadyExist> {
            reportService.createReport(
                null, title, description, employeeId, referenceIdForPlan, documentId
            )
        }
    }

    @Test
    fun `getCreatedReports return list of task reports`() {
        val report1 = Report.create(
            id = ReportId.generate(),
            title = ReportTitle.validate("Report 1"),
            description = description,
            employeeId = employeeId,
            referenceId = referenceIdForTask,
            documentId = documentId
        )
        val report2 = Report.create(
            id = ReportId.generate(),
            title = ReportTitle.validate("Report 2"),
            description = description,
            employeeId = employeeId,
            referenceId = referenceIdForTask,
            documentId = documentId
        )
        val reports = listOf(report1, report2)
        val expectedDetailsList = reports.map { ReportToDetailsMapper.toReportDetails(it) }

        every { taskReportRepository.getTaskReportsByEmployeeId(employeeId) } returns reports

        val result = reportService.getCreatedReports(employeeId, ReportType.TASK)

        assertEquals(2, result.size)
        assertEquals(expectedDetailsList, result)
    }

    @Test
    fun `filterCreatedReportsByStatus filter plan reports by status successfully`() {
        val report1 = Report.create(
            id = ReportId.generate(),
            title = ReportTitle.validate("Checked Report"),
            description = description,
            employeeId = employeeId,
            referenceId = referenceIdForPlan,
            documentId = documentId
        ).changeReportStatus(ReportStatus.CHECKED)
        val report2 = Report.create(
            id = ReportId.generate(),
            title = ReportTitle.validate("Not Checked Report"),
            description = description,
            employeeId = employeeId,
            referenceId = referenceIdForPlan,
            documentId = documentId
        ).changeReportStatus(ReportStatus.NOT_CHECKED)

        val reports = listOf(report1, report2)
        val filteredReports = listOf(report1)
        val expectedDetailsList = filteredReports.map { ReportToDetailsMapper.toReportDetails(it) }


        every { planReportRepository.getPlanReportsByEmployeeId(employeeId) } returns reports
        val result = reportService.filterCreatedReportsByStatus(employeeId, ReportType.PLAN, ReportStatus.CHECKED)

        assertEquals(1, result.size)
        assertEquals(expectedDetailsList, result)
    }

    @Test
    fun `changeReportStatus change task report status successfully`() {
        val existingReport = Report.create(
            id = reportId,
            title = title,
            description = description,
            employeeId = employeeId,
            referenceId = referenceIdForTask,
            documentId = documentId
        )
        val reportWithNewStatus = existingReport.changeReportStatus(status)
        val expectedDetails = ReportToDetailsMapper.toReportDetails(reportWithNewStatus)

        every { taskReportRepository.getTaskReport(reportId) } returns existingReport
        every { taskReportRepository.updateTaskReport(reportId, reportWithNewStatus) } returns reportWithNewStatus

        val result = reportService.changeReportStatus(reportId, ReportType.TASK, status)
        assertEquals(expectedDetails, result)
    }

    @Test
    fun `changeReportStatus throw FailedToUpdateReportStatus when failed to update report status`() {
        val existingReport = Report.create(
            id = reportId,
            title = title,
            description = description,
            employeeId = employeeId,
            referenceId = referenceIdForPlan,
            documentId = documentId
        )

        every { planReportRepository.getPlanReport(reportId) } returns existingReport
        every { planReportRepository.updatePlanReport(reportId, any()) } returns null
        assertThrows<ReportException.FailedToUpdateReportStatus> {
            reportService.changeReportStatus(reportId, ReportType.PLAN, status)
        }
    }

    @Test
    fun `getSubordinatesTaskReports return list of subordinates task reports`() {
        val report1 = Report.create(
            id = ReportId.generate(),
            title = ReportTitle.validate("Subordinate Report 1"),
            description = description,
            employeeId = subordinatesIds.first(),
            referenceId = referenceIdForTask,
            documentId = documentId
        )
        val report2 = Report.create(
            id = ReportId.generate(),
            title = ReportTitle.validate("Subordinate Report 2"),
            description = description,
            employeeId = subordinatesIds.last(),
            referenceId = referenceIdForTask,
            documentId = documentId
        )
        val reports = listOf(report1, report2)
        val expectedDetailsList = reports.map { ReportToDetailsMapper.toReportDetails(it) }

        every { taskReportRepository.getTaskReportByEmployeeIds(subordinatesIds) } returns reports
        val result = reportService.getSubordinatesTaskReports(subordinatesIds)

        assertEquals(2, result.size)
        assertEquals(expectedDetailsList, result)
    }

    @Test
    fun `filterSubordinatesTaskReportsByStatus filter reports by status successfully`() {
        val report1 = Report.create(
            id = ReportId.generate(),
            title = ReportTitle.validate("Checked Subordinate Report"),
            description = description,
            employeeId = subordinatesIds.first(),
            referenceId = referenceIdForTask,
            documentId = documentId
        ).changeReportStatus(ReportStatus.CHECKED)
        val report2 = Report.create(
            id = ReportId.generate(),
            title = ReportTitle.validate("Not Checked Subordinate Report"),
            description = description,
            employeeId = subordinatesIds.last(),
            referenceId = referenceIdForTask,
            documentId = documentId
        ).changeReportStatus(ReportStatus.NOT_CHECKED)

        val reports = listOf(report1, report2)
        val filteredReports = listOf(report1)
        val expectedDetailsList = filteredReports.map { ReportToDetailsMapper.toReportDetails(it) }

        every { taskReportRepository.getTaskReportByEmployeeIds(subordinatesIds) } returns reports

        val result = reportService.filterSubordinatesTaskReportsByStatus(subordinatesIds, ReportStatus.CHECKED)

        assertEquals(1, result.size)
        assertEquals(expectedDetailsList, result)
    }

    @Test
    fun `filterSubordinatesTaskReportsByStatus return empty list when no reports match status`() {
        val report = Report.create(
            id = ReportId.generate(),
            title = ReportTitle.validate("Not Checked Report"),
            description = description,
            employeeId = subordinatesIds.first(),
            referenceId = referenceIdForTask,
            documentId = documentId
        ).changeReportStatus(ReportStatus.NOT_CHECKED)

        val reports = listOf(report)
        val emptyList = emptyList<ReportDetails>()

        every { taskReportRepository.getTaskReportByEmployeeIds(subordinatesIds) } returns reports
        val result = reportService.filterSubordinatesTaskReportsByStatus(subordinatesIds, ReportStatus.CHECKED)

        assertEquals(0, result.size)
        assertEquals(emptyList, result)
    }
}
