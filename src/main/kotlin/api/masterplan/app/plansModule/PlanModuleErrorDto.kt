package api.masterplan.app.plansModule

sealed class PlanModuleErrorDto(message : String) : Exception(message) {
    class InternalServerError(message: String) : PlanModuleErrorDto(message)
}