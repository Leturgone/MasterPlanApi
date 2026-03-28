package api.masterplan.app.adminRequestsModule.domain.model.entity

import api.masterplan.app.adminRequestsModule.domain.model.value.AdminAnswerDescription
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminAnswerId
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminAnswerTitle
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestId

@ConsistentCopyVisibility
data class AdminAnswer private constructor(
    val id: AdminAnswerId,
    val title: AdminAnswerTitle,
    val description: AdminAnswerDescription,
    val adminRequestId: AdminRequestId
) {
    companion object {
        fun create(id: AdminAnswerId? = null, title: AdminAnswerTitle,
                   description: AdminAnswerDescription, adminRequestId: AdminRequestId
        ): AdminAnswer{
            return AdminAnswer(
                id = id?: AdminAnswerId.generate(),
                title = title,
                description = description,
                adminRequestId = adminRequestId
            )
        }
    }
}