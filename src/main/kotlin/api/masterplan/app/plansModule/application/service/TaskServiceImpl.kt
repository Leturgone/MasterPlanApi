package api.masterplan.app.plansModule.application.service

import api.masterplan.app.logging.LoggingMethod
import api.masterplan.app.plansModule.application.mapper.TasksPlanToEntityMapper
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

@Service
class TaskServiceImpl(
    private val taskRepository: TaskRepository
): TaskService {

    @LoggingMethod("planModule")
    override fun getTaskById(taskId: TaskId): TaskDetails {
        val task = taskRepository.getTask(taskId)?: throw PlanException.TaskNotExist(taskId)

        return TasksPlanToEntityMapper.toTaskDetails(task)
    }


    @LoggingMethod("planModule")
    override fun getTasksByPlanId(planId: PlanId): List<TaskDetails> {
        val tasks = taskRepository.getTasksByPlanId(planId)

        return tasks.map { task -> TasksPlanToEntityMapper.toTaskDetails(task) }
    }


    @LoggingMethod("planModule")
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
            executorsId = executorsId,
        )

        val task = taskRepository.saveTask(taskEntity)?: throw PlanException.FailedToSaveTask(title, planId)

        return task
    }


    @LoggingMethod("planModule")
    override fun deleteTask(taskId: TaskId): TaskId {
        val deleteTaskId = taskRepository.deleteTask(taskId)?: throw PlanException.FailedToDeleteTask(taskId)

        return deleteTaskId
    }


    @LoggingMethod("planModule")
    override fun updateTask(taskId: TaskId, updatedTask: Task): TaskId {
        taskRepository.getTask(taskId)?: PlanException.TaskNotExist(taskId)

        val updatedTaskId = taskRepository.updateTask(taskId, updatedTask)?: throw PlanException.FailedToUpdateTask(taskId)
        return updatedTaskId
    }


    @LoggingMethod("planModule")
    override fun getAssignedTasks(executorId: ExecutorId): List<TaskDetails> {
        val tasks = taskRepository.getTasksByExecutorId(executorId)

        return tasks.map { task -> TasksPlanToEntityMapper.toTaskDetails(task) }
    }


    @LoggingMethod("planModule")
    override fun sortPlansTasksByDate(planId: PlanId): List<TaskDetails> {
        val tasks = taskRepository.getTasksByPlanId(planId)

        return tasks.sortedBy { task ->
            task.endDate.value
        }.map { task -> TasksPlanToEntityMapper.toTaskDetails(task) }
    }


    @LoggingMethod("planModule")
    override fun sortAssignedTasksByDate(executorId: ExecutorId): List<TaskDetails> {
        val tasks = taskRepository.getTasksByExecutorId(executorId)

        return tasks.sortedBy { task ->
            task.endDate.value
        }.map { task -> TasksPlanToEntityMapper.toTaskDetails(task) }
    }


    @LoggingMethod("planModule")
    override fun filterAssignedTasksByStatus(executorId: ExecutorId, taskStatus: TaskStatus): List<TaskDetails> {
        val tasks = taskRepository.getTasksByExecutorId(executorId)

        return tasks.filter { task ->
            task.status == taskStatus
        }.map { task -> TasksPlanToEntityMapper.toTaskDetails(task) }
    }


    @LoggingMethod("planModule")
    override fun filterPlanTasksByStatus(planId: PlanId, taskStatus: TaskStatus): List<TaskDetails> {
        val tasks = taskRepository.getTasksByPlanId(planId)

        return tasks.filter { task ->
            task.status == taskStatus
        }.map { task -> TasksPlanToEntityMapper.toTaskDetails(task) }
    }


    @LoggingMethod("planModule")
    override fun searchAssignedTasksByTitle(executorId: ExecutorId, query: String): List<TaskDetails> {
        val searchResult = taskRepository.searchExecutorTasksByTitle(executorId, query)

        return searchResult.map { task -> TasksPlanToEntityMapper.toTaskDetails(task) }
    }


    @LoggingMethod("planModule")
    override fun assignTaskDocumentToTask(taskId: TaskId, documentId: TaskDocumentId): TaskId {
        val task = taskRepository.getTask(taskId)?: throw PlanException.TaskNotExist(taskId)
        val taskWithPlan = task.addDocument(documentId)

        val updatedTaskId = taskRepository.updateTask(taskId, taskWithPlan)?: throw PlanException.FailedToAssignDocumentToTask(
            taskId,documentId
        )

        return updatedTaskId
    }


    @LoggingMethod("planModule")
    override fun updateTaskStatus(taskId: TaskId, taskStatus: TaskStatus): TaskId {
        val task = taskRepository.getTask(taskId)?: throw PlanException.TaskNotExist(taskId)
        val taskWithNewStatus = task.changeTaskStatus(taskStatus)
        val updatedTaskId = taskRepository.updateTask(taskId, taskWithNewStatus)?: throw PlanException.FailedToUpdateTaskStatus(
            taskId,taskStatus
        )

        return updatedTaskId
    }

}