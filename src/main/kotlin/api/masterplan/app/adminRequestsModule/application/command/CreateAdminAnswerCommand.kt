package api.masterplan.app.adminRequestsModule.application.command

import api.masterplan.app.adminRequestsModule.domain.model.value.AdminAnswerDescription
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminAnswerId
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminAnswerTitle
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestId

data class CreateAdminAnswerCommand(
    val id: AdminAnswerId? = null,
    val title: AdminAnswerTitle,
    val description: AdminAnswerDescription,
    val adminRequestId: AdminRequestId
)