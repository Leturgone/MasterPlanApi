package api.masterplan.app.plansModule.application.ports

import api.masterplan.app.plansModule.domain.model.value.ExecutorId
import api.masterplan.app.plansModule.domain.model.value.PlanDirectorId
import api.masterplan.app.plansModule.domain.model.value.TaskStatus
import api.masterplan.app.plansModule.domain.model.value.TaskTitle

interface PlanNotificationPort {
    fun sendTaskAssignmentNotification(executorsIds: List<ExecutorId>, taskTitle: TaskTitle)

    fun sendTaskChangeStatusNotification(directorId: PlanDirectorId, taskTitle: TaskTitle, taskStatus: TaskStatus)
    
    fun sendTaskUpdatedNotification(executorsIds: List<ExecutorId>, taskTitle: TaskTitle)
}