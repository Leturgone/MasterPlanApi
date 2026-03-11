package api.masterplan.app.reportsModule.domain.interfaces

import api.masterplan.app.reportsModule.domain.models.entity.PlanReport
import api.masterplan.app.reportsModule.domain.models.value.PlanReportId
import api.masterplan.app.reportsModule.domain.models.value.PlanReportTitle
import api.masterplan.app.reportsModule.domain.models.value.ReportEmployeeId

interface PlanReportRepository {

    fun getPlanReport(planReportId: PlanReportId): PlanReport?

    fun deletePlanReport(planReportId: PlanReportId): PlanReportId?

    fun savePlanReport(report: PlanReport): PlanReportId?

    fun getPlanReportsByEmployeeId(employeeId: ReportEmployeeId): List<PlanReport>

    fun isPlanReportExist(employeeId: ReportEmployeeId,title: PlanReportTitle): Boolean

    fun updatePlanReport(planReportId: PlanReportId, updatedReport: PlanReport): PlanReportId?
}