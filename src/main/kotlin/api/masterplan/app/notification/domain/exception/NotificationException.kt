package api.masterplan.app.notification.domain.exception

sealed class NotificationException(message: String) : Exception(message) {
    class FailedToSendPushNotification(message: String) : NotificationException(message)

    class InvalidNotificationTitle(message: String?) : NotificationException(
        "Invalid notification title: ${message?.let { ": $it" } ?: ""}"
    )

    class InvalidNotificationMessage(message: String?) : NotificationException(
        "Invalid notification message: ${message?.let { ": $it" } ?: ""}"
    )
}