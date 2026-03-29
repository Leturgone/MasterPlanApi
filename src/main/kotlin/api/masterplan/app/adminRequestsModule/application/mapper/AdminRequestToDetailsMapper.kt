package api.masterplan.app.adminRequestsModule.application.mapper

import api.masterplan.app.adminRequestsModule.domain.dtos.AdminAnswerDetails
import api.masterplan.app.adminRequestsModule.domain.dtos.AdminRequestDetails
import api.masterplan.app.adminRequestsModule.domain.model.entity.AdminAnswer
import api.masterplan.app.adminRequestsModule.domain.model.entity.AdminRequest

object AdminRequestToDetailsMapper {

    fun toDetails(adminRequest: AdminRequest): AdminRequestDetails{
        return AdminRequestDetails(
            id = adminRequest.id,
            title = adminRequest.title,
            description = adminRequest.description,
            creationDate = adminRequest.creationDate,
            senderId = adminRequest.senderId,
            status = adminRequest.status
        )
    }

    fun toDetails( adminAnswer: AdminAnswer): AdminAnswerDetails {
        return AdminAnswerDetails(
            id = adminAnswer.id,
            title = adminAnswer.title,
            description = adminAnswer.description,
            adminRequestId = adminAnswer.adminRequestId,
        )
    }
}