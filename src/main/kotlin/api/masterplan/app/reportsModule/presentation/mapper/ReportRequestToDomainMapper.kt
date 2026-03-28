package api.masterplan.app.reportsModule.presentation.mapper

import api.masterplan.app.reportsModule.application.dto.ReportFile
import api.masterplan.app.reportsModule.domain.dtos.ReportUpdateData
import api.masterplan.app.reportsModule.domain.exceptions.ReportException
import api.masterplan.app.reportsModule.domain.models.value.*
import java.util.*

object ReportRequestToDomainMapper {

    fun toReportId(id: UUID) = ReportId(id)

    fun toReportType(type: String): ReportType {
        return try {
            ReportType.valueOf(type.uppercase())
        }catch (_: IllegalArgumentException){
            throw ReportException.InvalidReportType(type.uppercase())
        }
    }

    fun toReportStatus(status: String): ReportStatus {
        return try {
            ReportStatus.valueOf(status.uppercase())
        }catch (_: IllegalArgumentException){
            throw ReportException.InvalidReportStatus(status.uppercase())
        }
    }

    fun toReportEmployeeId(id: UUID) = ReportEmployeeId(id)

    fun toReportFile(fileName: String?,fileData: ByteArray): ReportFile{
        return ReportFile(
            fileName = fileName?:"report.pdf",
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

    fun toReportDocumentId(documentId: UUID) = ReportDocumentId(documentId)

    fun toUpdateReportData(title: String, description: String?, documentId: UUID): ReportUpdateData {
        return ReportUpdateData(
            title = toReportTitle(title),
            description = description?.let { toReportDescription(it)},
            documentId = toReportDocumentId(documentId)
        )
    }

}