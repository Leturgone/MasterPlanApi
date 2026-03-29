package api.masterplan.app.adminRequestsModule.domain.model.entity

import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestCreationDate
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestDescription
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestId
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestSenderId
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestStatus
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestTitle
import java.time.LocalDateTime

@ConsistentCopyVisibility
data class AdminRequest private constructor(
    val id: AdminRequestId,
    val title: AdminRequestTitle,
    val description: AdminRequestDescription,
    val creationDate: AdminRequestCreationDate,
    val senderId: AdminRequestSenderId,
    val status: AdminRequestStatus
){
    companion object {
        fun create(id: AdminRequestId? = null, title: AdminRequestTitle,
                   description: AdminRequestDescription, creationDate: AdminRequestCreationDate? = null,
                   senderId: AdminRequestSenderId, status: AdminRequestStatus? = null): AdminRequest{
            return AdminRequest(
                id = id ?: AdminRequestId.generate(),
                title = title,
                description = description,
                creationDate = creationDate?: AdminRequestCreationDate(LocalDateTime.now()),
                senderId = senderId,
                status = status?: AdminRequestStatus.NOT_STARTED
            )
        }
    }

    fun changeStatus(status: AdminRequestStatus): AdminRequest {
        return this.copy(status = status)
    }
}
