package api.masterplan.app.reportsModule.presentation.api.controller

import api.masterplan.app.reportsModule.application.command.*
import api.masterplan.app.reportsModule.application.usecase.*
import api.masterplan.app.reportsModule.presentation.api.exceptionHandler.ReportControllerExceptionHandler
import api.masterplan.app.reportsModule.presentation.dto.request.CreateReportRequest
import api.masterplan.app.reportsModule.presentation.dto.request.UpdateReportRequest
import api.masterplan.app.reportsModule.presentation.dto.request.UpdateReportStatusRequest
import api.masterplan.app.reportsModule.presentation.dto.response.ReportIdResponse
import api.masterplan.app.reportsModule.presentation.dto.response.ReportResponse
import api.masterplan.app.reportsModule.presentation.mapper.ReportDomainToResponseMapper
import api.masterplan.app.reportsModule.presentation.mapper.ReportRequestToDomainMapper
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@ReportControllerExceptionHandler
@RequestMapping("/api/v1")
@Tag(name = "Reports", description = "Управление отчетами по планам мероприятий и задачам")
class ReportController(
    private val changeReportStatusUseCase: ChangeReportStatusUseCase,
    private val createReportUseCase: CreateReportUseCase,
    private val deleteReportUseCase: DeleteReportUseCase,
    private val filterByStatusCreatedReportsUseCase: FilterByStatusCreatedReportsUseCase,
    private val filterByStatusSubordinatesTaskReportsUseCase: FilterByStatusSubordinatesTaskReportsUseCase,
    private val getCreatedReportsUseCase: GetCreatedReportsUseCase,
    private val getReportInfUserCase: GetReportInfUseCase,
    private val getSubordinatesTaskReportsUseCase: GetSubordinatesTaskReportsUseCase,
    private val updateReportUseCase: UpdateReportUseCase
) {


    @GetMapping("/emp/reports/getReport/{reportType}/{reportId}")
    fun getReportInformation(
        @PathVariable(value = "reportType") reportType: String,
        @PathVariable(value = "reportId") reportId: UUID
    ): ResponseEntity<ReportResponse>{
        val command = GetReportInfCommand(
            reportId = ReportRequestToDomainMapper.toReportId(reportId),
            reportType = ReportRequestToDomainMapper.toReportType(reportType)
        )
        val result = getReportInfUserCase(command).getOrThrow()
        val resp = ReportDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }

    @GetMapping("/emp/{employeeId}/reports/{reportType}/createdReports")
    fun getCreatedReports(
        @PathVariable(value = "employeeId") employeeId: UUID,
        @PathVariable(value = "reportType") reportType: String,
    ): ResponseEntity<List<ReportResponse>> {
        val command = GetCreatedReportsCommand(
            employeeId = ReportRequestToDomainMapper.toReportEmployeeId(employeeId),
            reportType = ReportRequestToDomainMapper.toReportType(reportType)
        )
        val result = getCreatedReportsUseCase(command).getOrThrow()
        val resp = ReportDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }

    @GetMapping("/dir/{directorId}/reports/TASK/subordinatesReports")
    fun getSubordinatesTaskReports(
        @PathVariable(value = "directorId") directorId: UUID
    ):ResponseEntity<List<ReportResponse>> {
        val command = GetSubordinatesTaskReportsCommand(
            directorId = ReportRequestToDomainMapper.toReportEmployeeId(directorId)
        )

        val result = getSubordinatesTaskReportsUseCase(command).getOrThrow()
        val resp = ReportDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }

    @PostMapping("/emp/reports/createReport")
    fun  createReport(
        @PathVariable(value = "reportType") reportType: String,
        @RequestBody request: CreateReportRequest): ResponseEntity<ReportIdResponse> {
        val file = ReportRequestToDomainMapper.toReportFile(
            fileName = request.documentName,
            fileData = request.document
        )
        val reportType = ReportRequestToDomainMapper.toReportType(reportType)
        val reportReferenceId = ReportRequestToDomainMapper.toReportReferenceId(request.referenceId,reportType)

        val command = CreateReportCommand(
            id = request.id?.let {ReportRequestToDomainMapper.toReportId(it)},
            title = ReportRequestToDomainMapper.toReportTitle(request.title),
            description = request.description?.let { ReportRequestToDomainMapper.toReportDescription(it)},
            employeeId = ReportRequestToDomainMapper.toReportEmployeeId(request.employeeId),
            referenceId = reportReferenceId,
            document = file,
            reportType = reportType,
        )

        val result = createReportUseCase(command).getOrThrow()
        val resp = ReportDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }


    @PatchMapping(("/emp/reports/updateReport/{reportType}/{reportId}"))
    fun  updateReport(
        @PathVariable(value = "reportType") reportType: String,
        @PathVariable(value = "reportId") reportId: UUID,
        @RequestBody request: UpdateReportRequest
    ): ResponseEntity<ReportIdResponse> {
        val file = ReportRequestToDomainMapper.toReportFile(
            fileName = request.documentName,
            fileData = request.document
        )
        val updatedReport = ReportRequestToDomainMapper.toUpdateReportData(
            title = request.title,
            description = request.description,
            documentId = request.documentId
        )
        val reportType = ReportRequestToDomainMapper.toReportType(reportType)
        val command = UpdateReportCommand(
            reportId = ReportRequestToDomainMapper.toReportId(reportId),
            updatedData = updatedReport,
            reportType = reportType,
            document = file,
        )
        val result = updateReportUseCase(command).getOrThrow()
        val resp = ReportDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }


    @GetMapping("/dir/{directorId}/reports/TASK/subordinatesReports/filterStatus/{reportStatus}")
    fun getFilterByStatusSubordinatesTaskReports(
        @PathVariable(value = "directorId") directorId: UUID,
        @PathVariable(value = "reportStatus") reportStatus: String
    ): ResponseEntity<List<ReportResponse>> {

        val command = FilterByStatusToSubordinatesTaskReportsCommand(
            directorId = ReportRequestToDomainMapper.toReportEmployeeId(directorId),
            status = ReportRequestToDomainMapper.toReportStatus(reportStatus)
        )

        val result = filterByStatusSubordinatesTaskReportsUseCase(command).getOrThrow()
        val resp = ReportDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }


    @GetMapping("/emp/{employeeId}/reports/{reportType}/createdReports/filterStatus/{reportStatus}")
    fun getFilterByStatusCreatedReports(
        @PathVariable(value = "employeeId") employeeId: UUID,
        @PathVariable(value = "reportType") reportType: String,
        @PathVariable(value = "reportStatus") reportStatus: String
    ): ResponseEntity<List<ReportResponse>>{
        val command = FilterByStatusCreatedReportsCommand(
            employeeId = ReportRequestToDomainMapper.toReportEmployeeId(employeeId),
            status = ReportRequestToDomainMapper.toReportStatus(reportStatus),
            reportType = ReportRequestToDomainMapper.toReportType(reportType)
        )

        val result = filterByStatusCreatedReportsUseCase(command).getOrThrow()
        val resp = ReportDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }


    @DeleteMapping(("/emp/reports/deleteReport/{reportType}/{reportId}"))
    fun deleteReport(
        @PathVariable(value = "reportType") reportType: String,
        @PathVariable(value = "reportId") reportId: UUID,
    ): ResponseEntity<ReportIdResponse> {
        val command = DeleteReportCommand(
            reportId = ReportRequestToDomainMapper.toReportId(reportId),
            reportType = ReportRequestToDomainMapper.toReportType(reportType)
        )

        val result = deleteReportUseCase(command).getOrThrow()
        val resp = ReportDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }


    @PatchMapping(("/emp/reports/updateReportStatus/{reportType}/{reportId}"))
    fun changeReportStatus(
        @PathVariable(value = "reportType") reportType: String,
        @PathVariable(value = "reportId") reportId: UUID,
        @RequestBody request: UpdateReportStatusRequest
    ): ResponseEntity<ReportIdResponse>{
        val command = ChangeReportStatusCommand(
            reportId = ReportRequestToDomainMapper.toReportId(reportId),
            reportType = ReportRequestToDomainMapper.toReportType(reportType),
            status = ReportRequestToDomainMapper.toReportStatus(request.status)
        )

        val result = changeReportStatusUseCase(command).getOrThrow()
        val resp = ReportDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }




}