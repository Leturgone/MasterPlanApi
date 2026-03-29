package api.masterplan.app.adminRequestsModule.presentation.api.controller

import api.masterplan.app.adminRequestsModule.application.command.*
import api.masterplan.app.adminRequestsModule.application.usecase.*
import api.masterplan.app.adminRequestsModule.presentation.api.exceptionHandler.AdminRequestsControllerExceptionHandler
import api.masterplan.app.adminRequestsModule.presentation.dto.request.CreateAdminAnswerRequest
import api.masterplan.app.adminRequestsModule.presentation.dto.request.CreateAdminRequestRequest
import api.masterplan.app.adminRequestsModule.presentation.dto.request.UpdateRequestStatusRequest
import api.masterplan.app.adminRequestsModule.presentation.dto.response.*
import api.masterplan.app.adminRequestsModule.presentation.mapper.AdminRequestsDomainToResponseMapper
import api.masterplan.app.adminRequestsModule.presentation.mapper.AdminRequestsRequestsToDomainMapper
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@AdminRequestsControllerExceptionHandler
@RequestMapping("/api/v1/requests")
@Tag(name = "Admin Requests", description = "Управление заявками администратору и ответами администратора на них")
class AdminRequestsController(
    private val changeAdminRequestStatusUseCase: ChangeAdminRequestStatusUseCase,
    private val createAdminAnswerUseCase: CreateAdminAnswerUseCase,
    private val createAdminRequestUseCase: CreateAdminRequestUseCase,
    private val getAdminAnswerForRequestUseCase: GetAdminAnswerForRequestUseCase,
    private val getAdminRequestsListUseCase: GetAdminRequestsListUseCase,
    private val getAdminRequestUseCase: GetAdminRequestUseCase,
    private val getCreatedAdminRequestsBySenderListUseCase: GetCreatedAdminRequestsBySenderListUseCase
) {


    @Operation(
        summary = "Изменение статуса заявки",
        description = "Изменение статуса заявки по id заявки",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Статус заявки изменен",
                content = [Content(schema = Schema(implementation = AdminRequestIdResponse::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Некорректные данные запроса",
                content = [Content(schema = Schema(implementation = AdminRequestsErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Заявка с указанным id не найдена",
                content = [Content(schema = Schema(implementation = AdminRequestsErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера: сбой при обновлении статуса",
                content = [Content(schema = Schema(implementation = AdminRequestsErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли админа"
            )

        ]
    )
    @PatchMapping(("/admin/requests/{requestId}/status"))
    fun  changeAdminRequestStatus(
        @PathVariable(value = "requestId") requestId: UUID,
        @RequestBody request: UpdateRequestStatusRequest
    ): ResponseEntity<AdminRequestIdResponse>{
        val command = ChangeAdminRequestStatusCommand(
            id = AdminRequestsRequestsToDomainMapper.toAdminRequestId(requestId),
            status = AdminRequestsRequestsToDomainMapper.toAdminRequestStatus(request.status)
        )

        val result = changeAdminRequestStatusUseCase(command).getOrThrow()

        val resp = AdminRequestsDomainToResponseMapper.toResponse(result)

        return ResponseEntity.ok(resp)
    }



    @Operation(
        summary = "Создание ответа на заявку",
        description = "Создание ответа на заявку с указанием всех данных",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Ответ на заявку создан",
                content = [Content(schema = Schema(implementation = AdminAnswerIdResponse::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Некорректные данные запроса",
                content = [Content(schema = Schema(implementation = AdminRequestsErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Заявка с указанным id не найдена",
                content = [Content(schema = Schema(implementation = AdminRequestsErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера: сбой при создании ответа",
                content = [Content(schema = Schema(implementation = AdminRequestsErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли админа"
            )

        ]
    )
    @PostMapping(("/admin/requests/{requestId}/answer"))
    fun createAdminAnswer(
        @PathVariable(value = "requestId") requestId: UUID,
        @RequestBody request: CreateAdminAnswerRequest
    ): ResponseEntity<AdminAnswerIdResponse>{
        val command = CreateAdminAnswerCommand(
            id = request.id?.let { AdminRequestsRequestsToDomainMapper.toAdminAnswerId(it) },
            title = AdminRequestsRequestsToDomainMapper.toAdminAnswerTitle(request.title),
            description = AdminRequestsRequestsToDomainMapper.toAdminAnswerDescription(request.description),
            adminRequestId = AdminRequestsRequestsToDomainMapper.toAdminRequestId(requestId),
        )

        val result = createAdminAnswerUseCase(command).getOrThrow()

        val resp = AdminRequestsDomainToResponseMapper.toResponse(result)

        return ResponseEntity.ok(resp)
    }


    @Operation(
        summary = "Создание заявки",
        description = "Создание новой заявки с указанием всех данных",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Заявка создана",
                content = [Content(schema = Schema(implementation = AdminAnswerIdResponse::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Некорректные данные запроса",
                content = [Content(schema = Schema(implementation = AdminRequestsErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера: сбой при создании заявки",
                content = [Content(schema = Schema(implementation = AdminRequestsErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли руководителя"
            )

        ]
    )
    @PostMapping(("/dir/requests/sender/{senderId}"))
    fun createAdminRequest(
        @PathVariable(value = "senderId") senderId: UUID,
        @RequestBody request: CreateAdminRequestRequest
    ): ResponseEntity<AdminRequestIdResponse>{
        val command = CreateAdminRequestCommand(
            id = request.id?.let { AdminRequestsRequestsToDomainMapper.toAdminRequestId(it) },
            title = AdminRequestsRequestsToDomainMapper.toAdminRequestTitle(request.title),
            description = AdminRequestsRequestsToDomainMapper.toAdminRequestDescription(request.description),
            senderId = AdminRequestsRequestsToDomainMapper.toAdminRequestSenderId(senderId),
        )

        val result = createAdminRequestUseCase(command).getOrThrow()

        val resp = AdminRequestsDomainToResponseMapper.toResponse(result)

        return ResponseEntity.ok(resp)
    }


    @Operation(
        summary = "Получение ответа на заявку",
        description = "Получение ответа на заявку по id заявки",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Ответ получен",
                content = [Content(schema = Schema(implementation = AdminAnswerResponse::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Некорректные данные запроса",
                content = [Content(schema = Schema(implementation = AdminRequestsErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Ответ на указанную заявку не найден",
                content = [Content(schema = Schema(implementation = AdminRequestsErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера: сбой при получении ответа",
                content = [Content(schema = Schema(implementation = AdminRequestsErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли руководителя или админа"
            )

        ]
    )
    @GetMapping(("/dir/requests/{requestId}/answer"))
    fun getAdminAnswerForRequest(
        @PathVariable(value = "requestId") requestId: UUID
    ): ResponseEntity<AdminAnswerResponse>{
        val command = GetAdminAnswerForRequestCommand(
            id = AdminRequestsRequestsToDomainMapper.toAdminRequestId(requestId)
        )

        val result = getAdminAnswerForRequestUseCase(command).getOrThrow()

        val resp = AdminRequestsDomainToResponseMapper.toResponse(result)

        return ResponseEntity.ok(resp)
    }


    @Operation(
        summary = "Получение списка всех заявок ",
        description = "Получение списка всех заявок",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Список заявок получен",
                content = [Content(
                    array = ArraySchema(schema = Schema(implementation = AdminRequestResponse::class)))
                ]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера: сбой при получении списка заявок",
                content = [Content(schema = Schema(implementation = AdminRequestsErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли руководителя или админа"
            )

        ]
    )
    @GetMapping(("/dir/requests"))
    fun getAdminRequestsList(): ResponseEntity<List<AdminRequestResponse>>{
        val result = getAdminRequestsListUseCase().getOrThrow()
        val resp = AdminRequestsDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }


    @Operation(
        summary = "Получение информации о заявке",
        description = "Получение информациии о заявке по id заявки",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Заявка получена",
                content = [Content(schema = Schema(implementation = AdminAnswerResponse::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Некорректные данные запроса",
                content = [Content(schema = Schema(implementation = AdminRequestsErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Заявка не найдена",
                content = [Content(schema = Schema(implementation = AdminRequestsErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера: сбой при получении заявки",
                content = [Content(schema = Schema(implementation = AdminRequestsErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли руководителя или админа"
            )

        ]
    )
    @GetMapping(("/dir/requests/{requestId}"))
    fun getAdminRequest(
        @PathVariable(value = "requestId") requestId: UUID
    ): ResponseEntity<AdminRequestResponse>{
        val command = GetAdminRequestCommand(
            id = AdminRequestsRequestsToDomainMapper.toAdminRequestId(requestId)
        )

        val result = getAdminRequestUseCase(command).getOrThrow()

        val resp = AdminRequestsDomainToResponseMapper.toResponse(result)

        return ResponseEntity.ok(resp)
    }


    @Operation(
        summary = "Получение списка всех заявок созданных отправителем",
        description = "Получение списка всех заявок созданных отправителем по id отправителя",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Список заявок получен",
                content = [Content(
                    array = ArraySchema(schema = Schema(implementation = AdminRequestResponse::class)))
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Некорректные данные запроса",
                content = [Content(schema = Schema(implementation = AdminRequestsErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера: сбой при получении списка заявок",
                content = [Content(schema = Schema(implementation = AdminRequestsErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли руководителя или админа"
            )

        ]
    )
    @GetMapping(("/dir/requests/sender/{senderId}"))
    fun getCreatedAdminRequestsBySenderList(
        @PathVariable(value = "senderId") senderId: UUID
    ): ResponseEntity<List<AdminRequestResponse>>{

        val command = GetCreatedAdminRequestsBySenderListCommand(
            senderId = AdminRequestsRequestsToDomainMapper.toAdminRequestSenderId(senderId)
        )

        val result = getCreatedAdminRequestsBySenderListUseCase(command).getOrThrow()

        val resp = AdminRequestsDomainToResponseMapper.toResponse(result)

        return ResponseEntity.ok(resp)
    }


}