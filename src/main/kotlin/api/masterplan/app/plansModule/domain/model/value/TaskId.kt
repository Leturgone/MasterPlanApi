package api.masterplan.app.plansModule.domain.model.value

import com.github.f4b6a3.uuid.UuidCreator
import java.util.*

@JvmInline
value class TaskId(val value: UUID)
{
    companion object {
        fun generate() = TaskId(UuidCreator.getTimeOrderedEpoch())
    }
}