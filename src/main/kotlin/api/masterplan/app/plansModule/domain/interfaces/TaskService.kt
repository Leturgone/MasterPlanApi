package api.masterplan.app.plansModule.domain.interfaces

import api.masterplan.app.plansModule.domain.dtos.TaskDetails
import api.masterplan.app.plansModule.domain.model.entity.Task
import api.masterplan.app.plansModule.domain.model.value.ExecutorId
import api.masterplan.app.plansModule.domain.model.value.PlanId
import api.masterplan.app.plansModule.domain.model.value.TaskDate
import api.masterplan.app.plansModule.domain.model.value.TaskDescription
import api.masterplan.app.plansModule.domain.model.value.TaskDocumentId
import api.masterplan.app.plansModule.domain.model.value.TaskId
import api.masterplan.app.plansModule.domain.model.value.TaskStatus
import api.masterplan.app.plansModule.domain.model.value.TaskTitle

interface TaskService {

    fun getTaskById(id: TaskId): TaskDetails

    fun getTasksByPlanId(planId: PlanId): List<TaskDetails>

    fun createTask(id: TaskId? = null, title: TaskTitle, description: TaskDescription, endDate: TaskDate,
                   planId: PlanId, documentId: TaskDocumentId? = null,
                   executorsId: MutableList<ExecutorId>): TaskId

    fun deleteTask(taskId: TaskId): TaskId

    fun updateTask(taskId: TaskId,updatedTask: Task): TaskId

    fun getAssignedTasks(executorId: ExecutorId): List<TaskDetails>

    fun filterPlansTasksByDate(planId: PlanId): List<TaskDetails>

    fun filterAssignedTasksByStatus(executorId: ExecutorId,taskStatus: TaskStatus): List<TaskDetails>

}