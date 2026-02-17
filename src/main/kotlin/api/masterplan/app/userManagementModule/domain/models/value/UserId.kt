package api.masterplan.app.userManagementModule.domain.models.value

import com.github.f4b6a3.uuid.UuidCreator
import java.util.UUID

@JvmInline
value class UserId(val value: UUID){
    companion object {
        fun generate() = UserId(UuidCreator.getTimeOrderedEpoch())
    }
}
