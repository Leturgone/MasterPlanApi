package api.masterplan.app.adminRequestsModule.domain.exception

import api.masterplan.app.adminRequestsModule.domain.model.value.AdminAnswerTitle
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestId
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestStatus
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestTitle

sealed class AdminRequestException(message: String):Exception(message) {

    class InvalidAdminRequestDesc(message: String?) : AdminRequestException(
        "Invalid admin request description: ${message?:""}"
    )

    class InvalidAdminAnswerDesc(message: String?) : AdminRequestException(
        "Invalid admin answer description: ${message?:""}"
    )

    class InvalidAdminAnswerTitle(title: String?) : AdminRequestException(
        "Invalid admin answer title: ${title?:""}"
    )

    class InvalidAdminRequestTitle(title: String?) : AdminRequestException(
        "Invalid admin request title: ${title?:""}"
    )

    class FailedToCreateAdminRequest(title: AdminRequestTitle) : AdminRequestException(
        "Failed to create admin request with title: ${title.value}"
    )

    class FailedToCreateAdminAnswer(title: AdminAnswerTitle) : AdminRequestException(
        "Failed to create admin answer with title: ${title.value}"
    )

    class AdminRequestNotExist(id: AdminRequestId) : AdminRequestException(
        "Admin request with id = ${id.value} not found"
    )

    class FailedToChangeAdminRequestStatus(id: AdminRequestId, status: AdminRequestStatus) : AdminRequestException(
        "Failed to change status to {${status.name} for request with id = ${id.value}"
    )

    class AdminAnswerNotExistForRequest(id: AdminRequestId) : AdminRequestException(
        "Admin answer for admin request with id = ${id.value} not found"
    )

    class InvalidAdminRequestStatus(status: String) : AdminRequestException(
        "Invalid admin request status: $status"
    )
}