package api.masterplan.app.reportsModule.domain.interfaces

import api.masterplan.app.reportsModule.domain.models.entity.Report
import api.masterplan.app.reportsModule.domain.models.value.ReportEmployeeId
import api.masterplan.app.reportsModule.domain.models.value.ReportId
import api.masterplan.app.reportsModule.domain.models.value.ReportTitle

interface PlanReportRepository {

    fun getPlanReport(planReportId: ReportId): Report?

    fun deletePlanReport(planReportId: ReportId): ReportId?

    fun savePlanReport(report: Report): ReportId?

    fun getPlanReportsByEmployeeId(employeeId: ReportEmployeeId): List<Report>

    fun isPlanReportExist(employeeId: ReportEmployeeId,title: ReportTitle): Boolean

    fun updatePlanReport(planReportId: ReportId, updatedReport: Report): Report?
}