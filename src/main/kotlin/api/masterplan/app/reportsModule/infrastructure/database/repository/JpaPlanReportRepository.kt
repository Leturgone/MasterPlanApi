package api.masterplan.app.reportsModule.infrastructure.database.repository

import api.masterplan.app.reportsModule.infrastructure.database.entity.PlanReportEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface JpaPlanReportRepository: CrudRepository<PlanReportEntity, UUID>  {

    fun findByEmployeeId(employeeId: UUID): List<PlanReportEntity>

    fun existsByTitleAndEmployeeId(title: String, employeeId: UUID): Boolean
}