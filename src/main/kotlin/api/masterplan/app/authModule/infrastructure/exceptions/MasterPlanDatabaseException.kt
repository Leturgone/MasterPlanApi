package api.masterplan.app.authModule.infrastructure.exceptions

sealed class MasterPlanDatabaseException(message: String): Exception(message) {

    data class InvalidRoleTitle(val rawTitle: String?) : MasterPlanDatabaseException(
        "Invalid role: '$rawTitle'"
    )

}