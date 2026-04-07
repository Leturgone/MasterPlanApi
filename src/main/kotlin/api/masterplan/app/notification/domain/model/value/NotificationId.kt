package api.masterplan.app.notification.domain.model.value

@JvmInline
value class NotificationId(val value: Int){
    companion object {
        fun generate() = NotificationId(System.currentTimeMillis().toInt())
    }
}
