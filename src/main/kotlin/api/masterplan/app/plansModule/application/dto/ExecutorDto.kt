package api.masterplan.app.plansModule.application.dto

data class ExecutorDto(
    val name: String,
    val surname: String,
    val patronymic: String? = null
)
