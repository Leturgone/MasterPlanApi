package api.masterplan.app.adminRequestsModule.domain.exception

sealed class AdminRequestException(message: String):Exception(message) {

    class InvalidAdminRequestDesc(message: String?) : AdminRequestException(
        "Invalid admin request description: ${message?:""}"
    )
}