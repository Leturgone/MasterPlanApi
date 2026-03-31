package api.masterplan.app.notification.domain.model.value

import api.masterplan.app.notification.domain.exception.NotificationException

class NotificationTitle(val value:String) {
    companion object {
        fun validate(title: String): NotificationTitle {
            try {
                require(title.isNotBlank()) { "Title cant be blank" }
                require(title.length <= 50) { "Title too long" }
            } catch (e: IllegalArgumentException) {
                throw NotificationException.InvalidNotificationTitle(e.message)
            }
            return NotificationTitle(title)
        }
    }
}