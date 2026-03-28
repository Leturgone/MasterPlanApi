package api.masterplan.app.adminRequestsModule.domain.dto

import api.masterplan.app.adminRequestsModule.domain.model.value.AdminAnswerDescription
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminAnswerId
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminAnswerTitle
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestId

data class AdminAnswerDetails(
    val id: AdminAnswerId,
    val title: AdminAnswerTitle,
    val description: AdminAnswerDescription,
    val adminRequestId: AdminRequestId
)
