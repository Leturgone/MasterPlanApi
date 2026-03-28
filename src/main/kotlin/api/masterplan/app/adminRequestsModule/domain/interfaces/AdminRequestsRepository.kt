package api.masterplan.app.adminRequestsModule.domain.interfaces

import api.masterplan.app.adminRequestsModule.domain.dtos.AdminAnswerDetails
import api.masterplan.app.adminRequestsModule.domain.dtos.AdminRequestDetails
import api.masterplan.app.adminRequestsModule.domain.model.entity.AdminRequest
import api.masterplan.app.adminRequestsModule.domain.model.value.*

interface AdminRequestsRepository {

    fun createAdminRequest(id: AdminRequestId? = null, title: AdminRequestTitle,
                           description: AdminRequestDescription, senderId: AdminRequestSenderId): AdminRequestId

    fun createAdminAnswer(id: AdminAnswerId? = null, title: AdminAnswerTitle,
                          description: AdminAnswerDescription, adminRequestId: AdminRequestId): AdminAnswerId

    fun changeAdminRequestStatusById(id: AdminRequestId,status: AdminRequestStatus): AdminRequestId

    fun getAllAdminRequestsList(): List<AdminRequestDetails>

    fun getAdminRequestById(id: AdminAnswerId): AdminRequest

    fun getAdminRequestsListBySenderId(senderId: AdminRequestSenderId ): List<AdminRequestDetails>

    fun getAdminAnswerByRequestId(id: AdminRequestId): AdminAnswerDetails
}