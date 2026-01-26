package api.masterplan.app.authModule.infrastructure.exceptions

sealed class MasterPlanPasswordHashException(message: String): Exception(message)  {

    class EmptyPassword: MasterPlanPasswordHashException(
        "Password is empty"
    )
}