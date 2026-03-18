package api.masterplan.app.reportsModule.presentation.api.controller

import api.masterplan.app.plansModule.application.command.GetPlanInfCommand
import api.masterplan.app.reportsModule.application.command.GetReportInfCommand
import api.masterplan.app.reportsModule.application.usecase.ChangeReportStatusUseCase
import api.masterplan.app.reportsModule.application.usecase.CreateReportUseCase
import api.masterplan.app.reportsModule.application.usecase.DeleteReportUseCase
import api.masterplan.app.reportsModule.application.usecase.FilterByStatusCreatedReportsUseCase
import api.masterplan.app.reportsModule.application.usecase.FilterByStatusSubordinatesTaskReportsUseCase
import api.masterplan.app.reportsModule.application.usecase.GetCreatedReportsUseCase
import api.masterplan.app.reportsModule.application.usecase.GetReportInfUseCase
import api.masterplan.app.reportsModule.application.usecase.GetSubordinatesTaskReportsUseCase
import api.masterplan.app.reportsModule.application.usecase.UpdateReportUseCase
import api.masterplan.app.reportsModule.domain.models.value.ReportId
import api.masterplan.app.reportsModule.domain.models.value.ReportType
import api.masterplan.app.reportsModule.presentation.api.exceptionHandler.ReportControllerExceptionHandler
import api.masterplan.app.reportsModule.presentation.dto.response.ReportResponse
import api.masterplan.app.reportsModule.presentation.mapper.ReportToDomainMapper
import api.masterplan.app.reportsModule.presentation.mapper.ReportToResponseMapper
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

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


    @GetMapping("/emp/reports/getReport/{reportType}/{reportId}/")
    fun getReportInformation(
        @PathVariable(value = "reportType") reportType: String,
        @PathVariable(value = "reportId") reportId: UUID
    ): ResponseEntity<ReportResponse>{
        val command = GetReportInfCommand(
            reportId = ReportToDomainMapper.toReportId(reportId),
            reportType = ReportToDomainMapper.toReportType(reportType)
        )
        val result = getReportInfUserCase(command).getOrThrow()
        val resp = ReportToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }

    fun getCreatedReports(){
        getCreatedReportsUseCase
    }

    fun getSubordinatesTaskReports(){
        getSubordinatesTaskReportsUseCase
    }


    fun  createReport(){
        createReportUseCase
    }
    fun  updateReport(){
        updateReportUseCase
    }

    fun getFilterByStatusSubordinatesTaskReports(){
        filterByStatusSubordinatesTaskReportsUseCase
    }

    fun getFilterByStatusCreatedReports(){
        filterByStatusCreatedReportsUseCase
    }

    fun deleteReport(){
        deleteReportUseCase
    }

    fun changeReportStatus(){
        changeReportStatusUseCase
    }




}