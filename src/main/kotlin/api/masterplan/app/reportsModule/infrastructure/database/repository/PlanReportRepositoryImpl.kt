package api.masterplan.app.reportsModule.infrastructure.database.repository

import api.masterplan.app.reportsModule.domain.interfaces.PlanReportRepository
import api.masterplan.app.reportsModule.domain.models.entity.Report
import api.masterplan.app.reportsModule.domain.models.value.ReportEmployeeId
import api.masterplan.app.reportsModule.domain.models.value.ReportId
import api.masterplan.app.reportsModule.domain.models.value.ReportTitle
import org.springframework.stereotype.Repository

@Repository
class PlanReportRepositoryImpl(
    private val jpaPlanReportRepository: JpaPlanReportRepository,
    private val jpaPlanStatusRepository: JpaReportStatusRepository
): PlanReportRepository {
    override fun getPlanReport(planReportId: ReportId): Report? {
        TODO("Not yet implemented")
    }

    override fun deletePlanReport(planReportId: ReportId): ReportId? {
        TODO("Not yet implemented")
    }

    override fun savePlanReport(report: Report): ReportId? {
        TODO("Not yet implemented")
    }

    override fun getPlanReportsByEmployeeId(employeeId: ReportEmployeeId): List<Report> {
        TODO("Not yet implemented")
    }

    override fun isPlanReportExist(employeeId: ReportEmployeeId, title: ReportTitle): Boolean {
        TODO("Not yet implemented")
    }

    override fun updatePlanReport(planReportId: ReportId, updatedReport: Report): ReportId? {
        TODO("Not yet implemented")
    }
}