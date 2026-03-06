package api.masterplan.app.plansModule.application.command

import api.masterplan.app.plansModule.domain.model.value.PlanDirectorId

data class GetDirPlansCommand(
    val  directorId: PlanDirectorId
)