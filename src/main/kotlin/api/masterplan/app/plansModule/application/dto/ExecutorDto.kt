package api.masterplan.app.plansModule.application.dto

import api.masterplan.app.plansModule.domain.model.value.ExecutorId

data class ExecutorDto(
    val executorId: ExecutorId,
    val name: String,
    val surname: String,
    val patronymic: String? = null
)
