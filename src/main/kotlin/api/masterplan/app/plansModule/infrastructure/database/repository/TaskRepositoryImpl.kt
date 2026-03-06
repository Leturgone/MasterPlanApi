package api.masterplan.app.plansModule.infrastructure.database.repository

import api.masterplan.app.logging.LoggingDatabaseMethod
import api.masterplan.app.plansModule.domain.interfaces.TaskRepository
import api.masterplan.app.plansModule.domain.model.entity.Task
import api.masterplan.app.plansModule.domain.model.value.ExecutorId
import api.masterplan.app.plansModule.domain.model.value.PlanId
import api.masterplan.app.plansModule.domain.model.value.TaskId
import api.masterplan.app.plansModule.domain.model.value.TaskTitle
import api.masterplan.app.plansModule.infrastructure.database.mapper.TaskDatabaseMapper
import org.springframework.stereotype.Repository
import kotlin.jvm.optionals.getOrElse

@Repository
class TaskRepositoryImpl(
    private val jpaTaskRepository: JpaTaskRepository,
    private val jpaTaskStatusRepository: JpaTaskStatusRepository
): TaskRepository {

    @LoggingDatabaseMethod(moduleName = "planModule")
    override fun getTask(taskId: TaskId): Task? {
        val task = jpaTaskRepository.findById(taskId.value).getOrElse { return null }
        return TaskDatabaseMapper.toDomain(task)
    }


    @LoggingDatabaseMethod(moduleName = "planModule")
    override fun deleteTask(taskId: TaskId): TaskId? {
        jpaTaskRepository.deleteById(taskId.value)
        return taskId
    }


    @LoggingDatabaseMethod(moduleName = "planModule")
    override fun saveTask(task: Task): TaskId? {
        val statusList = jpaTaskStatusRepository.findAll().toSet()
        val taskEntity = TaskDatabaseMapper.toEntity(task, statusList)
        val taskId = jpaTaskRepository.save(taskEntity).id
        return TaskId(taskId)
    }


    @LoggingDatabaseMethod(moduleName = "planModule")
    override fun getTasksByPlanId(planId: PlanId): List<Task> {
        val tasks = jpaTaskRepository.findByPlanId(planId.value)
        return TaskDatabaseMapper.toDomain(tasks)
    }


    @LoggingDatabaseMethod(moduleName = "planModule")
    override fun getTasksByExecutorId(executorId: ExecutorId): List<Task> {
        val tasks = jpaTaskRepository.findByExecutorId(executorId.value)
        return TaskDatabaseMapper.toDomain(tasks)
    }

    override fun getTasksByExecutorIds(executorIds: Set<ExecutorId>): List<Task> {
        val executorIdsFields = executorIds.map { it.value }.toSet()
        val tasks = jpaTaskRepository.findByExecutorIds(executorIdsFields)
        return TaskDatabaseMapper.toDomain(tasks)
    }


    @LoggingDatabaseMethod(moduleName = "planModule")
    override fun isTaskExist(taskTitle: TaskTitle, planId: PlanId): Boolean {
        return jpaTaskRepository.existsByTitleAndPlanId(taskTitle.value, planId.value)
    }


    @LoggingDatabaseMethod(moduleName = "planModule")
    override fun updateTask(taskId: TaskId, task: Task): TaskId? {
        val statusList = jpaTaskStatusRepository.findAll().toSet()
        val taskEntity = TaskDatabaseMapper.toEntity(task, statusList)
        val taskId = jpaTaskRepository.save(taskEntity).id
        return TaskId(taskId)
    }


    @LoggingDatabaseMethod(moduleName = "planModule")
    override fun searchExecutorTasksByTitle(executorId: ExecutorId, query: String): List<Task> {
        val tasks = jpaTaskRepository.searchByExecutorIdAndTitle(executorId.value, query)
        return TaskDatabaseMapper.toDomain(tasks)
    }
}