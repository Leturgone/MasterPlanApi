package api.masterplan.app.employeeModule.domain.model.value

import com.github.f4b6a3.uuid.UuidCreator
import java.util.UUID

@JvmInline
value class EmployeeId(val value: UUID){
    companion object {
        fun generate() = EmployeeId(UuidCreator.getTimeOrderedEpoch())
    }
}
