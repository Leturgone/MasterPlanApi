package api.masterplan.app.reportsModule.domain.models.value

import com.github.f4b6a3.uuid.UuidCreator
import java.util.*

@JvmInline
value class ReportId(val value: UUID){
    companion object {
        fun generate() = ReportId(UuidCreator.getTimeOrderedEpoch())
    }
}