package api.masterplan.app.adminRequestsModule.domain.model.value

import com.github.f4b6a3.uuid.UuidCreator
import java.util.UUID

class AdminRequestId(val value: UUID){
    companion object {
        fun generate() = AdminRequestId(UuidCreator.getTimeOrderedEpoch())
    }
}