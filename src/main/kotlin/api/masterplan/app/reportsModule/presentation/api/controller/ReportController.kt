package api.masterplan.app.reportsModule.presentation.api.controller

import api.masterplan.app.reportsModule.application.command.*
import api.masterplan.app.reportsModule.application.usecase.*
import api.masterplan.app.reportsModule.presentation.api.exceptionHandler.ReportControllerExceptionHandler
import api.masterplan.app.reportsModule.presentation.dto.request.CreateReportRequest
import api.masterplan.app.reportsModule.presentation.dto.request.UpdateReportRequest
import api.masterplan.app.reportsModule.presentation.dto.request.UpdateReportStatusRequest
import api.masterplan.app.reportsModule.presentation.dto.response.ReportErrorResponse
import api.masterplan.app.reportsModule.presentation.dto.response.ReportIdResponse
import api.masterplan.app.reportsModule.presentation.dto.response.ReportResponse
import api.masterplan.app.reportsModule.presentation.mapper.ReportDomainToResponseMapper
import api.masterplan.app.reportsModule.presentation.mapper.ReportRequestToDomainMapper
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

@RestController
@ReportControllerExceptionHandler
@RequestMapping("/api/v1/reports")
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


    @Operation(
        summary = "Получение информации об отчете",
        description = "Получение информации об отчете по id отчета и типу",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Информация об отчете получена",
                content = [Content(schema = Schema(implementation = ReportResponse::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Отчет с указанным id не найден",
                content = [Content(schema = Schema(implementation = ReportErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Некорректные данные запроса",
                content = [Content(schema = Schema(implementation = ReportErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера: сбой при получении отчета",
                content = [Content(schema = Schema(implementation = ReportErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли сотрудника"
            )

        ]
    )
    @GetMapping("/emp/report/{reportId}/type/{reportType}")
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


    @Operation(
        summary = "Получение списка созданных отчетов",
        description = "Получение списка созданных сотрудников отчетов по id сотрудника и типу",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Список отчетов получен",
                content = [Content(
                    array = ArraySchema(schema = Schema(implementation = ReportResponse::class)))
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Некорректные данные запроса",
                content = [Content(schema = Schema(implementation = ReportErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера: сбой при получении списка отчетов",
                content = [Content(schema = Schema(implementation = ReportErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли сотрудника"
            )

        ]
    )
    @GetMapping("/emp/{employeeId}/report/type/{reportType}/all")
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


    @Operation(
        summary = "Получение списка созданных исполнителями отчетов для проверки",
        description = "Получение списка созданных сотрудниками отчетов для проверки руководителем по id руководителем",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Список отчетов получен",
                content = [Content(
                    array = ArraySchema(schema = Schema(implementation = ReportResponse::class)))
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Некорректные данные запроса",
                content = [Content(schema = Schema(implementation = ReportErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера: сбой при получении списка отчетов",
                content = [Content(schema = Schema(implementation = ReportErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли руководителем"
            )

        ]
    )
    @GetMapping("/dir/{directorId}/subordinatesReports/type/TASK")
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


    @Operation(
        summary = "Создание отчета",
        description = "Создание отчета со всеми данными",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Отчет создан",
                content = [Content(schema = Schema(implementation = ReportIdResponse::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Некорректные данные запроса",
                content = [Content(schema = Schema(implementation = ReportErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "409",
                description = "Отчет уже существует",
                content = [Content(schema = Schema(implementation = ReportErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера: сбой при создании отчета",
                content = [Content(schema = Schema(implementation = ReportErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли сотрудника"
            )

        ]
    )
    @PostMapping("/emp/report/type/{reportType}",consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun  createReport(
        @PathVariable(value = "reportType") reportType: String,
        @RequestPart("request") request: CreateReportRequest,
        @RequestPart(value = "file") file: MultipartFile
    ): ResponseEntity<ReportIdResponse> {
        val fileByteArray = file.bytes
        val fileName = file.originalFilename
        val file = ReportRequestToDomainMapper.toReportFile(
            fileName = fileName,
            fileData = fileByteArray
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


    @Operation(
        summary = "Обновление отчета",
        description = "Обновление отчета, включая название, описание и прикрепленный файл",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Отчет обновлен",
                content = [Content(schema = Schema(implementation = ReportIdResponse::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Некорректные данные запроса",
                content = [Content(schema = Schema(implementation = ReportErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Отчет с указанным id и типом не найден",
                content = [Content(schema = Schema(implementation = ReportErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера: сбой при обновлении отчета",
                content = [Content(schema = Schema(implementation = ReportErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли сотрудника"
            )

        ]
    )
    @PatchMapping("/emp/report/{reportId}/type/{reportType}",consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun  updateReport(
        @PathVariable(value = "reportType") reportType: String,
        @PathVariable(value = "reportId") reportId: UUID,
        @RequestPart(value = "file") file: MultipartFile,
        @RequestPart("request") request: UpdateReportRequest
    ): ResponseEntity<ReportIdResponse> {
        val fileByteArray = file.bytes
        val fileName = file.originalFilename
        val file = ReportRequestToDomainMapper.toReportFile(
            fileName = fileName,
            fileData = fileByteArray
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


    @Operation(
        summary = "Получение списка созданных исполнителями отчетов для проверки с фильтром по статусу",
        description = "Получение списка созданных сотрудниками отчетов для проверки руководителем с фильтром по статусу по id руководителя и статусу",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Список отчетов получен",
                content = [Content(
                    array = ArraySchema(schema = Schema(implementation = ReportResponse::class)))
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Некорректные данные запроса",
                content = [Content(schema = Schema(implementation = ReportErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера: сбой при получении списка отчетов",
                content = [Content(schema = Schema(implementation = ReportErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли руководителем"
            )

        ]
    )
    @GetMapping("/dir/{directorId}/subordinatesReports/type/TASK/status/{reportStatus}")
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


    @Operation(
        summary = "Получение списка созданных отчетов с фильтром по статусу",
        description = "Получение списка созданных сотрудником отчетов с фильтром по статусу по id сотрудника и статусу",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Список отчетов получен",
                content = [Content(
                    array = ArraySchema(schema = Schema(implementation = ReportResponse::class)))
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Некорректные данные запроса",
                content = [Content(schema = Schema(implementation = ReportErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера: сбой при получении списка отчетов",
                content = [Content(schema = Schema(implementation = ReportErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли сотрудника"
            )

        ]
    )
    @GetMapping("/emp/{employeeId}/report/type/{reportType}/status/{reportStatus}")
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



    @Operation(
        summary = "Удаление отчета",
        description = "Удаление отчета по id отчета и типу отчета",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Отчет обновлен",
                content = [Content(schema = Schema(implementation = ReportIdResponse::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Некорректные данные запроса",
                content = [Content(schema = Schema(implementation = ReportErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Отчет с указанным id и типом не найден",
                content = [Content(schema = Schema(implementation = ReportErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера: сбой при удалении отчета",
                content = [Content(schema = Schema(implementation = ReportErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли сотрудника"
            )

        ]
    )
    @DeleteMapping(("/emp/report/{reportId}/type/{reportType}"))
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



    @Operation(
        summary = "Изменение статуса отчета",
        description = "Изменение статуса отчета по id отчета и типу отчета",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Статус отчета изменен",
                content = [Content(schema = Schema(implementation = ReportIdResponse::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Некорректные данные запроса",
                content = [Content(schema = Schema(implementation = ReportErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Отчет с указанным id и типом не найден",
                content = [Content(schema = Schema(implementation = ReportErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера: сбой при изменении статуса",
                content = [Content(schema = Schema(implementation = ReportErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли руководителя"
            )

        ]
    )
    @PatchMapping(("/dir/report/{reportId}/type/{reportType}/status"))
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