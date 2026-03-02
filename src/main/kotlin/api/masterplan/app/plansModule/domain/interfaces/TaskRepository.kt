package api.masterplan.app.plansModule.domain.interfaces

import api.masterplan.app.plansModule.domain.model.entity.Task
import api.masterplan.app.plansModule.domain.model.value.ExecutorId
import api.masterplan.app.plansModule.domain.model.value.PlanId
import api.masterplan.app.plansModule.domain.model.value.TaskId
import api.masterplan.app.plansModule.domain.model.value.TaskTitle

interface TaskRepository {

    fun getTask(taskId: TaskId): Task?

    fun deleteTask(taskId: TaskId): TaskId?

    fun saveTask(task: Task): TaskId?

    fun getTasksByPlanId(planId: PlanId): List<Task>

    fun getTasksByExecutorId(executorId: ExecutorId): List<Task>

    fun getTasksByExecutorIds(executorIds: Set<ExecutorId>): List<Task>

    fun isTaskExist(taskTitle: TaskTitle,planId: PlanId): Boolean

    fun updateTask(taskId: TaskId, task: Task): TaskId?

    fun searchExecutorTasksByTitle(executorId: ExecutorId, query: String): List<Task>
}