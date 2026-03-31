package api.masterplan.app.notification.domain.model.value

@JvmInline
value class NotificationTitle(val value:String) {
    companion object {
        fun generate(type:NotificationType): NotificationTitle {
            val title = when (type) {
                NotificationType.TASK_ASSIGNMENT -> "Назначена новая задача"
                NotificationType.TASK_CHANGE_STATUS -> "Статус задачи изменился"
                NotificationType.NEW_REQUEST -> "Новая заявка"
                NotificationType.REQUEST_CHANGE_STATUS ->  "Статус заявки изменился"
                NotificationType.NEW_REPORT -> "Новый отчет"
                NotificationType.REPORT_CHANGE_STATUS -> "Статус отчета изменился"
            }
            return NotificationTitle(title)
        }
    }
}