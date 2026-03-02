package api.masterplan.app.plansModule.infrastructure.database.repository

import api.masterplan.app.plansModule.infrastructure.database.entity.TaskEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface JpaTaskRepository: CrudRepository<TaskEntity, UUID> {

    fun findByPlanId(planId: UUID): List<TaskEntity>

    @Query("SELECT t FROM TaskEntity t JOIN TaskHasExecutorEntity eht ON t.id = eht.id.taskId " +
            "WHERE eht.id.executorId = :executorId")
    fun findByExecutorId(executorId: UUID): List<TaskEntity>


    @Query(" SELECT t FROM TaskEntity t " +
            "JOIN TaskHasExecutorEntity eht ON t.id = eht.id.taskId " +
            "WHERE eht.id.executorId = :executorId AND LOWER(t.title) " +
            "LIKE LOWER(CONCAT('%', :query, '%'))")
    fun searchByExecutorIdAndTitle(executorId: UUID, query: String): List<TaskEntity>


    fun existsByTitleAndPlanId(title: String, planId: UUID): Boolean


    @Query("SELECT t FROM TaskEntity t " +
            "JOIN TaskHasExecutorEntity eht ON t.id = eht.id.taskId " +
            "WHERE eht.id.executorId IN :executorIds")
    fun findByExecutorIds(executorIds: Set<UUID>): List<TaskEntity>

}