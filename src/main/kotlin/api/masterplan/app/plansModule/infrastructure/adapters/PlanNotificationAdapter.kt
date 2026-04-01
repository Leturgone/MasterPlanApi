package api.masterplan.app.plansModule.infrastructure.adapters

import api.masterplan.app.apiContracts.notifications.NotificationModuleService
import api.masterplan.app.plansModule.application.ports.PlanNotificationPort
import api.masterplan.app.plansModule.domain.model.value.ExecutorId
import api.masterplan.app.plansModule.domain.model.value.PlanDirectorId
import api.masterplan.app.plansModule.domain.model.value.TaskStatus
import api.masterplan.app.plansModule.domain.model.value.TaskTitle
import org.springframework.stereotype.Component

@Component
class PlanNotificationAdapter(
    private val notificationService: NotificationModuleService
): PlanNotificationPort {

    override fun sendTaskAssignmentNotification(
        executorsIds: List<ExecutorId>,
        taskTitle: TaskTitle
    ) {
        val notificationMessage = "Вам назначена новая задча: ${taskTitle.value}"
        executorsIds.forEach { executorId ->
            notificationService.sendTaskAssignmentNotification(executorId.value,notificationMessage)
        }
    }

    override fun sendTaskChangeStatusNotification(
        directorId: PlanDirectorId,
        taskTitle: TaskTitle,
        taskStatus: TaskStatus
    ) {
        val notificationMessage = "Статус заявки ${taskTitle.value} изменен на ${taskStatus.name}"
        notificationService.sendTaskChangeStatusNotification(directorId.value,notificationMessage)
    }

    override fun sendTaskUpdatedNotification(
        executorsIds: List<ExecutorId>,
        taskTitle: TaskTitle
    ) {
        val notificationMessage = "Задача: ${taskTitle.value} обновлена"
        executorsIds.forEach { executorId ->
            notificationService.sendTaskUpdatedNotification(executorId.value,notificationMessage)
        }

    }
}