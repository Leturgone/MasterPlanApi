package api.masterplan.app.reportsModule.presentation.mapper

import api.masterplan.app.reportsModule.domain.exceptions.ReportException
import api.masterplan.app.reportsModule.domain.models.value.ReportEmployeeId
import api.masterplan.app.reportsModule.domain.models.value.ReportId
import api.masterplan.app.reportsModule.domain.models.value.ReportType
import java.util.*

object ReportToDomainMapper {

    fun toReportId(id: UUID) = ReportId(id)

    fun toReportType(type: String): ReportType {
        return try {
            ReportType.valueOf(type.uppercase())
        }catch (_: IllegalArgumentException){
            throw ReportException.InvalidReportStatus(type.uppercase())
        }
    }

    fun toReportEmployeeId(id: UUID) = ReportEmployeeId(id)

}