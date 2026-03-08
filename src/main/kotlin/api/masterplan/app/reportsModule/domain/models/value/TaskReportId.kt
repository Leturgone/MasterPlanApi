package api.masterplan.app.reportsModule.domain.models.value

import com.github.f4b6a3.uuid.UuidCreator
import java.util.UUID

@JvmInline
value class TaskReportId(val id: UUID){
    companion object {
        fun generate() = TaskReportId(UuidCreator.getTimeOrderedEpoch())
    }
}