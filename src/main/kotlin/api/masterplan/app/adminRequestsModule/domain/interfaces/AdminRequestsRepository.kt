package api.masterplan.app.adminRequestsModule.domain.interfaces

import api.masterplan.app.adminRequestsModule.domain.model.entity.AdminAnswer
import api.masterplan.app.adminRequestsModule.domain.model.entity.AdminRequest
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminAnswerId
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestId
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestSenderId

interface AdminRequestsRepository {

    fun saveAdminRequest(adminRequest: AdminRequest): AdminRequestId

    fun saveAdminAnswer(adminAnswer: AdminAnswer): AdminAnswerId

    fun updateAdminRequest(id: AdminRequestId,updatedAdminRequest: AdminRequest): AdminRequest


    fun getAllAdminRequestsList(): List<AdminRequest>

    fun getAdminRequestById(id: AdminRequestId): AdminRequest?

    fun getAdminRequestsListBySenderId(senderId: AdminRequestSenderId ): List<AdminRequest>

    fun getAdminAnswerByRequestId(id: AdminRequestId): AdminAnswer?
}