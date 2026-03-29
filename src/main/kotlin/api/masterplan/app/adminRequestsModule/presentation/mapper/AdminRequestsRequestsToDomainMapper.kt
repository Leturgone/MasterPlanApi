package api.masterplan.app.adminRequestsModule.presentation.mapper

import api.masterplan.app.adminRequestsModule.domain.exception.AdminRequestException
import api.masterplan.app.adminRequestsModule.domain.model.value.*
import java.util.*

object AdminRequestsRequestsToDomainMapper {

    fun toAdminRequestId(id: UUID) = AdminRequestId(id)

    fun toAdminRequestStatus(status: String): AdminRequestStatus {
        return try {
            AdminRequestStatus.valueOf(status.uppercase())
        }catch (_: IllegalArgumentException){
            throw AdminRequestException.InvalidAdminRequestStatus(status)
        }
    }

    fun toAdminAnswerId(id: UUID) = AdminAnswerId(id)

    fun toAdminAnswerTitle(title: String) = AdminAnswerTitle.validate(title)

    fun toAdminAnswerDescription(description: String) = AdminAnswerDescription.validate(description)

    fun toAdminRequestTitle(title: String) = AdminRequestTitle.validate(title)

    fun toAdminRequestDescription(description: String) = AdminRequestDescription.validate(description)

    fun toAdminRequestSenderId(id: UUID) = AdminRequestSenderId(id)
}