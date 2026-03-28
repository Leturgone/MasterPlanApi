package api.masterplan.app.adminRequestsModule.domain.exception

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
}