package api.masterplan.app.adminRequestsModule.presentation.mapper

import api.masterplan.app.adminRequestsModule.domain.dtos.AdminAnswerDetails
import api.masterplan.app.adminRequestsModule.domain.dtos.AdminRequestDetails
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminAnswerId
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestId
import api.masterplan.app.adminRequestsModule.presentation.dto.response.AdminAnswerIdResponse
import api.masterplan.app.adminRequestsModule.presentation.dto.response.AdminAnswerResponse
import api.masterplan.app.adminRequestsModule.presentation.dto.response.AdminRequestIdResponse
import api.masterplan.app.adminRequestsModule.presentation.dto.response.AdminRequestResponse

object AdminRequestsDomainToResponseMapper {

    fun toResponse(id: AdminRequestId)  = AdminRequestIdResponse(id.value)

    fun toResponse(id: AdminAnswerId) = AdminAnswerIdResponse(id.value)

    fun toResponse(entity: AdminRequestDetails): AdminRequestResponse{
        return AdminRequestResponse(
            id = entity.id.value,
            title = entity.title.value,
            description = entity.description.value,
            creationDate = entity.creationDate.value,
            senderId = entity.senderId.value,
            status = entity.status.name
        )
    }

    fun toResponse(entity: AdminAnswerDetails): AdminAnswerResponse{
        return AdminAnswerResponse(
            id = entity.id.value,
            title = entity.title.value,
            description = entity.description.value,
            adminRequestId = entity.adminRequestId.value
        )
    }

    fun toResponse(entityList: List<AdminRequestDetails>): List<AdminRequestResponse>{
        return entityList.map { toResponse(it) }
    }
}