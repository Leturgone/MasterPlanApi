package api.masterplan.app.plansModule.domain.interfaces

import api.masterplan.app.plansModule.domain.dtos.TaskDetails
import api.masterplan.app.plansModule.domain.model.entity.Task
import api.masterplan.app.plansModule.domain.model.value.*

interface TaskService {

    fun getTaskById(taskId: TaskId): TaskDetails

    fun getTasksByPlanId(planId: PlanId): List<TaskDetails>

    fun createTask(id: TaskId? = null, title: TaskTitle, description: TaskDescription, endDate: TaskDate,
                   planId: PlanId, documentId: TaskDocumentId? = null,
                   executorsId: MutableList<ExecutorId>): TaskId

    fun deleteTask(taskId: TaskId): TaskId

    fun updateTask(taskId: TaskId,updatedTask: Task): TaskId

    fun getAssignedTasks(executorId: ExecutorId): List<TaskDetails>

    fun getAssignedTasksForMultipleExecutors(executorIds: Set<ExecutorId>): List<TaskDetails>

    fun sortPlansTasksByDate(planId: PlanId): List<TaskDetails>

    fun sortAssignedTasksByDate(executorId: ExecutorId): List<TaskDetails>

    fun filterAssignedTasksByStatus(executorId: ExecutorId,taskStatus: TaskStatus): List<TaskDetails>

    fun filterPlanTasksByStatus(planId: PlanId,taskStatus: TaskStatus): List<TaskDetails>

    fun searchAssignedTasksByTitle(executorId: ExecutorId,query: String): List<TaskDetails>

    fun assignTaskDocumentToTask(taskId: TaskId, documentId: TaskDocumentId ): TaskId

    fun updateTaskStatus(taskId: TaskId, taskStatus: TaskStatus): TaskId
}