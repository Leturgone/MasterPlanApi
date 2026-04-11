package api.masterplan.app.employeeModule.infrastructure.database.repository

import api.masterplan.app.employeeModule.infrastructure.database.entity.EmployeeEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface JpaEmployeeRepository: CrudRepository<EmployeeEntity, UUID> {
    @Query("SELECT e FROM EmployeeEntity e WHERE " +
            "LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%')) OR " +
            "LOWER(e.surname) LIKE LOWER(CONCAT('%', :surname, '%'))")
    fun searchByNameOrSurname(@Param("name") name: String,
                              @Param("surname") surname: String): List<EmployeeEntity>

    @Query("SELECT e FROM EmployeeEntity e WHERE " +
            "e.directorId = :directorId AND " +
            "(LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%')) OR " +
            "LOWER(e.surname) LIKE LOWER(CONCAT('%', :surname, '%')))")
    fun searchByNameOrSurnameAndDirId(@Param("name") name: String,
                                      @Param("surname") surname: String,
                                      @Param("directorId") directorId: UUID): List<EmployeeEntity>

    @Query("SELECT e FROM EmployeeEntity e WHERE e.directorId = :directorId")
    fun getByDirectorId(@Param("directorId") directorId: UUID): List<EmployeeEntity>

    @Query("SELECT COUNT(e) > 0 FROM EmployeeEntity e WHERE e.appUserId = :userId")
    fun existsByUserId(userId: UUID): Boolean

    fun findByAppUserId(userId: UUID): EmployeeEntity?

}