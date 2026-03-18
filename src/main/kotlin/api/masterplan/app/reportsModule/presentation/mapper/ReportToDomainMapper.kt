package api.masterplan.app.reportsModule.presentation.mapper

import api.masterplan.app.reportsModule.domain.exceptions.ReportException
import api.masterplan.app.reportsModule.domain.models.value.ReportId
import api.masterplan.app.reportsModule.domain.models.value.ReportType
import java.util.*

object ReportToDomainMapper {

    fun toReportId(value: UUID) = ReportId(value)

    fun toReportType(status: String): ReportType {
        return try {
            ReportType.valueOf(status.uppercase())
        }catch (_: IllegalArgumentException){
            throw ReportException.InvalidReportStatus(status.uppercase())
        }
    }

}