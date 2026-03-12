package api.masterplan.app.reportsModule.infrastructure.adapters

import api.masterplan.app.reportsModule.domain.models.value.ReportEmployeeId
import java.util.UUID

object ReportInnerModuleSuccessMapper {
    fun toReportEmpIdSet(ids:Set<UUID>):Set<ReportEmployeeId>  {
        return ids.map{ReportEmployeeId(it)}.toSet()
    }
}