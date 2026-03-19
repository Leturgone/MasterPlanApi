package api.masterplan.app.reportsModule.presentation.mapper

import api.masterplan.app.reportsModule.application.dto.ReportFile
import api.masterplan.app.reportsModule.domain.exceptions.ReportException
import api.masterplan.app.reportsModule.domain.models.value.ReportDescription
import api.masterplan.app.reportsModule.domain.models.value.ReportEmployeeId
import api.masterplan.app.reportsModule.domain.models.value.ReportId
import api.masterplan.app.reportsModule.domain.models.value.ReportPlanId
import api.masterplan.app.reportsModule.domain.models.value.ReportReferenceId
import api.masterplan.app.reportsModule.domain.models.value.ReportTaskId
import api.masterplan.app.reportsModule.domain.models.value.ReportTitle
import api.masterplan.app.reportsModule.domain.models.value.ReportType
import java.util.*

object ReportRequestToDomainMapper {

    fun toReportId(id: UUID) = ReportId(id)

    fun toReportType(type: String): ReportType {
        return try {
            ReportType.valueOf(type.uppercase())
        }catch (_: IllegalArgumentException){
            throw ReportException.InvalidReportStatus(type.uppercase())
        }
    }

    fun toReportEmployeeId(id: UUID) = ReportEmployeeId(id)

    fun toReportFile(fileName: String,fileData: ByteArray): ReportFile{
        return ReportFile(
            fileName = fileName,
            fileData = fileData,
        )
    }

    fun toReportTitle(title: String) = ReportTitle.validate(title)

    fun toReportDescription(description: String) = ReportDescription.validate(description)

    fun toReportReferenceId(id: UUID,type: ReportType): ReportReferenceId {
        return when(type){
            ReportType.TASK -> ReportReferenceId.ForTask(ReportTaskId(id))
            ReportType.PLAN -> ReportReferenceId.ForPlan(ReportPlanId(id))
        }
    }

}