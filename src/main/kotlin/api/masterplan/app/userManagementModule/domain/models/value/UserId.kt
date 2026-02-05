package api.masterplan.app.userManagementModule.domain.models.value

import java.util.UUID

@JvmInline
value class UserId(val value: UUID){
    companion object {
        fun generate() = UserId(UUID.randomUUID())
    }
}
