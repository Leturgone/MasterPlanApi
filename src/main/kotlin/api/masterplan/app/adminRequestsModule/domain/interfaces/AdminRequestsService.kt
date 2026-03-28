package api.masterplan.app.adminRequestsModule.domain.interfaces

import api.masterplan.app.adminRequestsModule.domain.dtos.AdminAnswerDetails
import api.masterplan.app.adminRequestsModule.domain.dtos.AdminRequestDetails
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminAnswerId
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestId
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestSenderId
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestStatus

interface AdminRequestsService {

    fun createAdminRequest(): AdminRequestId

    fun createAdminAnswer(): AdminAnswerId

    fun changeAdminRequestStatus(id: AdminRequestId,status: AdminRequestStatus): AdminRequestId

    fun getAdminRequestsList(): List<AdminRequestDetails>

    fun getAdminRequest(id: AdminAnswerId): AdminRequestDetails

    fun getCreatedAdminRequestsBySenderList(senderId: AdminRequestSenderId ): List<AdminRequestDetails>

    fun getAdminAnswerForRequest(id: AdminRequestId): AdminAnswerDetails
}