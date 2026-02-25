package api.masterplan.app.plansModule.domain.exceptions

import api.masterplan.app.plansModule.domain.model.value.PlanDirectorId
import api.masterplan.app.plansModule.domain.model.value.PlanId
import api.masterplan.app.plansModule.domain.model.value.PlanTitle
import api.masterplan.app.plansModule.domain.model.value.TaskId
import api.masterplan.app.plansModule.domain.model.value.TaskTitle

sealed class PlanException(message: String): Exception(message){

    class InvalidTaskTitle(message: String?) : PlanException(
        "Invalid task title: ${message?.let {": $it"  }}"
    )

    class InvalidPlanTitle(message: String?) : PlanException(
        "Invalid plan title: ${message?.let {": $it"  }}"
    )

    class InvalidTaskDesc(message: String?) : PlanException(
        "Invalid task description: ${message?.let {": $it"  }}"
    )

    class InvalidPlanDesc(message: String?) : PlanException(
        "Invalid plan description: ${message?.let {": $it"  }}"
    )

    class PlanNotExist(planId: PlanId) : PlanException(
        "Plan with id = ${planId.value} not found"
    )

    class PlanAlreadyExists(planTitle: PlanTitle) : PlanException(
        "Plan with title = ${planTitle.value} already exists"
    )

    class FailedToCreatePlan(planTitle: PlanTitle, directorId: PlanDirectorId) : PlanException(
        "Failed to create plan with id =  ${planTitle.value} with director id = ${directorId.value}"
    )

    class FailedToUpdatePlan(planId: PlanId) : PlanException(
        "Failed to update plan with id = ${planId.value} "
    )

    class FailedToDeletePlan(planId: PlanId) : PlanException(
        "Failed to delete plan with id = ${planId.value} "
    )

    class FailedToAddTask(planId: PlanId,taskId: TaskId) : PlanException(
        "Failed to add task with id = ${taskId.value} to plan with id = ${planId.value}"
    )

    class FailedToRemoveTask(planId: PlanId,taskId: TaskId) : PlanException(
        "Failed to remove task with id = ${taskId.value} from plan with id = ${taskId.value}"
    )

    class TaskNotExist(taskId: TaskId) : PlanException(
        "Task with id = ${taskId.value} not found"
    )

    class TaskAlreadyExists(taskTitle: TaskTitle) : PlanException(
        "Task with title = ${taskTitle.value} already exists"
    )

    class FailedToSaveTask(taskTitle: TaskTitle,planId: PlanId) : PlanException(
        "Failed to save task with title = ${taskTitle.value} to plan with id = ${planId.value}"
    )

    class FailedToDeleteTask(taskId: TaskId) : PlanException(
        "Failed to delete task with id = ${taskId.value}"
    )

    class FailedToUpdateTask(taskId: TaskId) : PlanException(
        "Failed to update task with id = ${taskId.value}"
    )



}