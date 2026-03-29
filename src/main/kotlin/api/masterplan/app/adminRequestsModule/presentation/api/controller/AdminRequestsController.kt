package api.masterplan.app.adminRequestsModule.presentation.api.controller

import api.masterplan.app.adminRequestsModule.application.command.ChangeAdminRequestStatusCommand
import api.masterplan.app.adminRequestsModule.application.command.CreateAdminAnswerCommand
import api.masterplan.app.adminRequestsModule.application.command.CreateAdminRequestCommand
import api.masterplan.app.adminRequestsModule.application.command.GetAdminAnswerForRequestCommand
import api.masterplan.app.adminRequestsModule.application.command.GetAdminRequestCommand
import api.masterplan.app.adminRequestsModule.application.command.GetCreatedAdminRequestsBySenderListCommand
import api.masterplan.app.adminRequestsModule.application.usecase.ChangeAdminRequestStatusUseCase
import api.masterplan.app.adminRequestsModule.application.usecase.CreateAdminAnswerUseCase
import api.masterplan.app.adminRequestsModule.application.usecase.CreateAdminRequestUseCase
import api.masterplan.app.adminRequestsModule.application.usecase.GetAdminAnswerForRequestUseCase
import api.masterplan.app.adminRequestsModule.application.usecase.GetAdminRequestUseCase
import api.masterplan.app.adminRequestsModule.application.usecase.GetAdminRequestsListUseCase
import api.masterplan.app.adminRequestsModule.application.usecase.GetCreatedAdminRequestsBySenderListUseCase
import api.masterplan.app.adminRequestsModule.presentation.api.exceptionHandler.AdminRequestsControllerExceptionHandler
import api.masterplan.app.adminRequestsModule.presentation.dto.request.CreateAdminAnswerRequest
import api.masterplan.app.adminRequestsModule.presentation.dto.request.CreateAdminRequestRequest
import api.masterplan.app.adminRequestsModule.presentation.dto.request.UpdateRequestStatusRequest
import api.masterplan.app.adminRequestsModule.presentation.dto.response.AdminAnswerIdResponse
import api.masterplan.app.adminRequestsModule.presentation.dto.response.AdminAnswerResponse
import api.masterplan.app.adminRequestsModule.presentation.dto.response.AdminRequestIdResponse
import api.masterplan.app.adminRequestsModule.presentation.dto.response.AdminRequestResponse
import api.masterplan.app.adminRequestsModule.presentation.mapper.AdminRequestsDomainToResponseMapper
import api.masterplan.app.adminRequestsModule.presentation.mapper.AdminRequestsRequestsToDomainMapper
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@AdminRequestsControllerExceptionHandler
@RequestMapping("/api/v1/requests")
@Tag(name = "Reports", description = "Управление заявками администратору и ответами администратора на них")
class AdminRequestsController(
    private val changeAdminRequestStatusUseCase: ChangeAdminRequestStatusUseCase,
    private val createAdminAnswerUseCase: CreateAdminAnswerUseCase,
    private val createAdminRequestUseCase: CreateAdminRequestUseCase,
    private val getAdminAnswerForRequestUseCase: GetAdminAnswerForRequestUseCase,
    private val getAdminRequestsListUseCase: GetAdminRequestsListUseCase,
    private val getAdminRequestUseCase: GetAdminRequestUseCase,
    private val getCreatedAdminRequestsBySenderListUseCase: GetCreatedAdminRequestsBySenderListUseCase
) {

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

    @PostMapping(("/dir/requests/sender/{senderId}"))
    fun createAdminRequest(
        @PathVariable(value = "senderId}") senderId: UUID,
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

    @GetMapping(("/dir/requests"))
    fun getAdminRequestsList(): ResponseEntity<List<AdminRequestResponse>>{
        val result = getAdminRequestsListUseCase().getOrThrow()
        val resp = AdminRequestsDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }

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