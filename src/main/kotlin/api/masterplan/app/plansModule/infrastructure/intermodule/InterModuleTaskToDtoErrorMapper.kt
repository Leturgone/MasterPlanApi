package api.masterplan.app.plansModule.infrastructure.intermodule

import api.masterplan.app.apiContracts.plans.PlanModuleErrorDto

internal object InterModuleTaskToDtoErrorMapper {
    fun toDto(exception: Throwable): PlanModuleErrorDto {
        return when (exception) {
            else -> PlanModuleErrorDto.InternalServerError("Internal plan module error")
        }
    } 
}