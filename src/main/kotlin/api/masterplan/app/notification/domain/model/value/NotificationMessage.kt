package api.masterplan.app.notification.domain.model.value

import api.masterplan.app.notification.domain.exception.NotificationException

@JvmInline
value class NotificationMessage(val message:String) {

    companion object{
        fun validate(message: String): NotificationMessage {
            try {
                require(message.isNotBlank()) { "Message cant be blank" }
                require(message.length <= 100) { "Message too long" }
            } catch (e: IllegalArgumentException) {
                throw NotificationException.InvalidNotificationMessage(e.message)
            }
            return NotificationMessage(message)
        }
    }
}