package api.masterplan.app.apiContracts.plans

sealed class PlanModuleErrorDto(message : String) : Exception(message) {
    class InternalServerError(message: String) : PlanModuleErrorDto(message)
}