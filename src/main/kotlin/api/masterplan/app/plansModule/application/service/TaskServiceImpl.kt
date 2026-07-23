package api.masterplan.app.plansModule.application.service

import api.masterplan.app.logging.annotations.LoggingMethod
import api.masterplan.app.plansModule.application.mapper.TasksPlanToDetailsMapper
import api.masterplan.app.plansModule.domain.dtos.TaskDetails
import api.masterplan.app.plansModule.domain.exceptions.PlanException
import api.masterplan.app.plansModule.domain.interfaces.TaskRepository
import api.masterplan.app.plansModule.domain.interfaces.TaskService
import api.masterplan.app.plansModule.domain.model.entity.Task
import api.masterplan.app.plansModule.domain.model.value.ExecutorId
import api.masterplan.app.plansModule.domain.model.value.PlanId
import api.masterplan.app.plansModule.domain.model.value.TaskDate
import api.masterplan.app.plansModule.domain.model.value.TaskDescription
import api.masterplan.app.plansModule.domain.model.value.TaskDocumentId
import api.masterplan.app.plansModule.domain.model.value.TaskId
import api.masterplan.app.plansModule.domain.model.value.TaskStatus
import api.masterplan.app.plansModule.domain.model.value.TaskTitle
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TaskServiceImpl(
    private val taskRepository: TaskRepository
): TaskService {

    @LoggingMethod("planModule")
    override fun getTaskById(taskId: TaskId): TaskDetails {
        val task = taskRepository.getTask(taskId)?: throw PlanException.TaskNotExist(taskId)

        return TasksPlanToDetailsMapper.toTaskDetails(task)
    }


    @LoggingMethod("planModule")
    override fun getTasksByPlanId(planId: PlanId): List<TaskDetails> {
        val tasks = taskRepository.getTasksByPlanId(planId)

        return tasks.map { task -> TasksPlanToDetailsMapper.toTaskDetails(task) }
    }


    @LoggingMethod("planModule")
    @Transactional(rollbackFor = [Exception::class])
    override fun createTask(id: TaskId?, title: TaskTitle, description: TaskDescription,
        endDate: TaskDate, planId: PlanId, documentId: TaskDocumentId?, executorsId: MutableList<ExecutorId>): TaskId {

        if (taskRepository.isTaskExist(title,planId)) throw PlanException.TaskAlreadyExists(title)

        val taskEntity = Task.create(
            id = id,
            title = title,
            description = description,
            endDate = endDate,
            planId = planId,
            documentId = documentId,
            urgency = null,
            executorsId = executorsId,
        )

        val task = taskRepository.saveTask(taskEntity)?: throw PlanException.FailedToSaveTask(title, planId)

        return task
    }


    @LoggingMethod("planModule")
    @Transactional(rollbackFor = [Exception::class])
    override fun deleteTask(taskId: TaskId): TaskId {
        val deleteTaskId = taskRepository.deleteTask(taskId)?: throw PlanException.FailedToDeleteTask(taskId)

        return deleteTaskId
    }


    @LoggingMethod("planModule")
    @Transactional(rollbackFor = [Exception::class])
    override fun updateTask(taskId: TaskId, updatedTask: Task): TaskDetails {
        val oldTask = taskRepository.getTask(taskId)?: throw PlanException.TaskNotExist(taskId)
        val updatedTaskWithUrgency = if (oldTask.endDate != updatedTask.endDate) {
            updatedTask.recalculateUrgency()
        } else {
            updatedTask
        }
        val updatedTask = taskRepository.updateTask(taskId, updatedTaskWithUrgency)?: throw PlanException.FailedToUpdateTask(taskId)
        return TasksPlanToDetailsMapper.toTaskDetails(updatedTask)
    }


    @LoggingMethod("planModule")
    override fun getAssignedTasks(executorId: ExecutorId): List<TaskDetails> {
        val tasks = taskRepository.getTasksByExecutorId(executorId)

        return tasks.map { task -> TasksPlanToDetailsMapper.toTaskDetails(task) }
    }

    override fun getAssignedTasksForMultipleExecutors(executorIds: Set<ExecutorId>): List<TaskDetails> {
        val tasks = taskRepository.getTasksByExecutorIds(executorIds)

        return tasks.map { task -> TasksPlanToDetailsMapper.toTaskDetails(task) }
    }


    @LoggingMethod("planModule")
    override fun sortPlansTasksByDate(planId: PlanId): List<TaskDetails> {
        val tasks = taskRepository.getTasksByPlanId(planId)

        return tasks.sortedBy { task ->
            task.endDate.value
        }.map { task -> TasksPlanToDetailsMapper.toTaskDetails(task) }
    }


    @LoggingMethod("planModule")
    override fun sortAssignedTasksByDate(executorId: ExecutorId): List<TaskDetails> {
        val tasks = taskRepository.getTasksByExecutorId(executorId)

        return tasks.sortedBy { task ->
            task.endDate.value
        }.map { task -> TasksPlanToDetailsMapper.toTaskDetails(task) }
    }


    @LoggingMethod("planModule")
    override fun filterAssignedTasksByStatus(executorId: ExecutorId, taskStatus: TaskStatus): List<TaskDetails> {
        val tasks = taskRepository.getTasksByExecutorId(executorId)

        return tasks.filter { task ->
            task.status == taskStatus
        }.map { task -> TasksPlanToDetailsMapper.toTaskDetails(task) }
    }


    @LoggingMethod("planModule")
    override fun filterPlanTasksByStatus(planId: PlanId, taskStatus: TaskStatus): List<TaskDetails> {
        val tasks = taskRepository.getTasksByPlanId(planId)

        return tasks.filter { task ->
            task.status == taskStatus
        }.map { task -> TasksPlanToDetailsMapper.toTaskDetails(task) }
    }


    @LoggingMethod("planModule")
    override fun searchAssignedTasksByTitle(executorId: ExecutorId, query: String): List<TaskDetails> {
        val searchResult = taskRepository.searchExecutorTasksByTitle(executorId, query)

        return searchResult.map { task -> TasksPlanToDetailsMapper.toTaskDetails(task) }
    }


    @LoggingMethod("planModule")
    @Transactional(rollbackFor = [Exception::class])
    override fun assignTaskDocumentToTask(taskId: TaskId, documentId: TaskDocumentId): TaskId {
        val task = taskRepository.getTask(taskId)?: throw PlanException.TaskNotExist(taskId)
        val taskWithPlan = task.addDocument(documentId)

        val updatedTask = taskRepository.updateTask(taskId, taskWithPlan)?: throw PlanException.FailedToAssignDocumentToTask(
            taskId,documentId
        )

        return updatedTask.id
    }


    @LoggingMethod("planModule")
    @Transactional(rollbackFor = [Exception::class])
    override fun updateTaskStatus(taskId: TaskId, taskStatus: TaskStatus): TaskDetails {
        val task = taskRepository.getTask(taskId)?: throw PlanException.TaskNotExist(taskId)
        val taskWithNewStatus = task.changeTaskStatus(taskStatus)
        val updatedTask = taskRepository.updateTask(taskId, taskWithNewStatus)?: throw PlanException.FailedToUpdateTaskStatus(
            taskId,taskStatus
        )

        return TasksPlanToDetailsMapper.toTaskDetails(updatedTask)
    }

}