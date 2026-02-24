package api.masterplan.app.plansModule.domain.model.value

import com.github.f4b6a3.uuid.UuidCreator
import java.util.*

@JvmInline
value class TaskDocumentId(val value: UUID){
    companion object {
        fun generate() = TaskDocumentId(UuidCreator.getTimeOrderedEpoch())
    }
}