package api.masterplan.app.reportsModule.infrastructure.database.repository

import api.masterplan.app.reportsModule.infrastructure.database.entity.TaskReportEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.util.*

interface JpaTaskReportRepository: CrudRepository<TaskReportEntity, UUID>  {

    fun findByEmployeeId(employeeId: UUID): List<TaskReportEntity>

    fun existsByTitleAndEmployeeId(title: String, employeeId: UUID): Boolean

    @Query("SELECT t FROM TasReportkEntity t " +
        "WHERE t.employeeId IN :employeeIds")
    fun findByEmployeeIds(employeeIds: Set<UUID>): List<TaskReportEntity>

}