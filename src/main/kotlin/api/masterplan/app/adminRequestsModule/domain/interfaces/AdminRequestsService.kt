package api.masterplan.app.adminRequestsModule.domain.interfaces

import api.masterplan.app.adminRequestsModule.domain.dtos.AdminAnswerDetails
import api.masterplan.app.adminRequestsModule.domain.dtos.AdminRequestDetails
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminAnswerDescription
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminAnswerId
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminAnswerTitle
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestDescription
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestId
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestSenderId
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestStatus
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestTitle

interface AdminRequestsService {

    fun createAdminRequest(id: AdminRequestId? = null, title: AdminRequestTitle,
                           description: AdminRequestDescription, senderId: AdminRequestSenderId): AdminRequestId

    fun createAdminAnswer(id: AdminAnswerId? = null, title: AdminAnswerTitle,
                          description: AdminAnswerDescription, adminRequestId: AdminRequestId): AdminAnswerId

    fun changeAdminRequestStatus(id: AdminRequestId,status: AdminRequestStatus): AdminRequestId

    fun getAdminRequestsList(): List<AdminRequestDetails>

    fun getAdminRequest(id: AdminRequestId): AdminRequestDetails

    fun getCreatedAdminRequestsBySenderList(senderId: AdminRequestSenderId ): List<AdminRequestDetails>

    fun getAdminAnswerForRequest(id: AdminRequestId): AdminAnswerDetails
}