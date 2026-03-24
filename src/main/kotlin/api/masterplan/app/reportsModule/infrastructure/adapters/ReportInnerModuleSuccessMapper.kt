package api.masterplan.app.reportsModule.infrastructure.adapters

import api.masterplan.app.reportsModule.domain.models.value.ReportDocumentId
import api.masterplan.app.reportsModule.domain.models.value.ReportEmployeeId
import java.util.UUID

internal object ReportInnerModuleSuccessMapper {

    fun toReportEmpIdSet(ids: Set<UUID>): Set<ReportEmployeeId> {
        return ids.map { ReportEmployeeId(it) }.toSet()
    }

    fun toReportDocumentId(id: UUID) = ReportDocumentId(id)
}