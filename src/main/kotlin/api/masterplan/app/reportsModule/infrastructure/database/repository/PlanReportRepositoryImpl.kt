package api.masterplan.app.reportsModule.infrastructure.database.repository

import api.masterplan.app.logging.LoggingDatabaseMethod
import api.masterplan.app.reportsModule.domain.interfaces.PlanReportRepository
import api.masterplan.app.reportsModule.domain.models.entity.Report
import api.masterplan.app.reportsModule.domain.models.value.ReportEmployeeId
import api.masterplan.app.reportsModule.domain.models.value.ReportId
import api.masterplan.app.reportsModule.domain.models.value.ReportTitle
import api.masterplan.app.reportsModule.infrastructure.database.mapper.PlanReportDatabaseMapper
import org.springframework.stereotype.Repository
import kotlin.jvm.optionals.getOrElse

@Repository
class PlanReportRepositoryImpl(
    private val jpaPlanReportRepository: JpaPlanReportRepository,
    private val jpaPlanStatusRepository: JpaReportStatusRepository
): PlanReportRepository {

    @LoggingDatabaseMethod(moduleName = "reportModule")
    override fun getPlanReport(planReportId: ReportId): Report? {
        val planReport = jpaPlanReportRepository.findById(planReportId.value).getOrElse { return null }
        return PlanReportDatabaseMapper.toDomain(planReport)
    }


    @LoggingDatabaseMethod(moduleName = "reportModule")
    override fun deletePlanReport(planReportId: ReportId): ReportId? {
        jpaPlanReportRepository.deleteById(planReportId.value)
        return planReportId
    }


    @LoggingDatabaseMethod(moduleName = "reportModule")
    override fun savePlanReport(report: Report): ReportId? {
        val statusSet = jpaPlanStatusRepository.findAll().toSet()
        val planReportEntity= PlanReportDatabaseMapper.toEntity(report, statusSet)
        val planReportId = jpaPlanReportRepository.save(planReportEntity).id
        return ReportId(planReportId)
    }


    @LoggingDatabaseMethod(moduleName = "reportModule")
    override fun getPlanReportsByEmployeeId(employeeId: ReportEmployeeId): List<Report> {
        val planReports = jpaPlanReportRepository.findByEmployeeId(employeeId.value)
        return PlanReportDatabaseMapper.toDomain(planReports)

    }


    @LoggingDatabaseMethod(moduleName = "reportModule")
    override fun isPlanReportExist(employeeId: ReportEmployeeId, title: ReportTitle): Boolean {
         return jpaPlanReportRepository.existsByTitleAndeEmployeeId(title.value,employeeId.value)
    }


    @LoggingDatabaseMethod(moduleName = "reportModule")
    override fun updatePlanReport(planReportId: ReportId, updatedReport: Report): ReportId? {
        jpaPlanReportRepository.findById(planReportId.value).getOrElse { return null }
        val statusSet = jpaPlanStatusRepository.findAll().toSet()
        val updatedPlanReportEntity = PlanReportDatabaseMapper.toEntity(updatedReport, statusSet)
        val planReportId = jpaPlanReportRepository.save(updatedPlanReportEntity).id
        return ReportId(planReportId)

    }
}