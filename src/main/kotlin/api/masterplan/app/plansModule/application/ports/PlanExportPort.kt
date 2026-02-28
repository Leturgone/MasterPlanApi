package api.masterplan.app.plansModule.application.ports

import api.masterplan.app.plansModule.application.dto.PlanExportFile
import api.masterplan.app.plansModule.domain.dtos.PlanDetails

interface PlanExportPort {

    fun exportPlan(plan: PlanDetails): PlanExportFile
}