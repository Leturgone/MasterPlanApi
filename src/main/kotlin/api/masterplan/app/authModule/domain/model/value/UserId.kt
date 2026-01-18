package api.masterplan.app.authModule.domain.model.value

import java.util.UUID

@JvmInline
value class UserId(val value: UUID){
    companion object {
        fun generate() = UserId(UUID.randomUUID())
    }
}
