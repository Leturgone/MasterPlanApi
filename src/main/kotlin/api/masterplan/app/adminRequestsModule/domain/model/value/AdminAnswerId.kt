package api.masterplan.app.adminRequestsModule.domain.model.value

import com.github.f4b6a3.uuid.UuidCreator
import java.util.UUID

@JvmInline
value class AdminAnswerId(val value: UUID) {
    companion object {
        fun generate() = AdminAnswerId(UuidCreator.getTimeOrderedEpoch())
    }
}