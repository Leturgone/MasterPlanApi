package api.masterplan.app.filesModule.domain.model.value

import com.github.f4b6a3.uuid.UuidCreator
import java.util.UUID

@JvmInline
value class DocumentFileId(val value: UUID){
    companion object {
        fun generate() = DocumentFileId(UuidCreator.getTimeOrderedEpoch())
    }
}
