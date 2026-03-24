package api.masterplan.app.reportsModule.infrastructure.adapters

import api.masterplan.app.filesModule.FilesModuleService
import api.masterplan.app.reportsModule.application.dto.ReportFile
import api.masterplan.app.reportsModule.application.ports.ReportFilesPort
import api.masterplan.app.reportsModule.domain.models.value.ReportDocumentId
import org.springframework.stereotype.Component

@Component
class ReportFilesAdapter(
    private val filesModuleService: FilesModuleService
): ReportFilesPort {
    override fun uploadReportFile(reportFile: ReportFile): ReportDocumentId {
        val result = filesModuleService.uploadFile(
            documentFileBaseName = reportFile.fileName,
            documentFileData = reportFile.fileData
        ).getOrElse {
            throw ReportInnerModuleErrorMapper.exceptionToModuleException(it)
        }
        return ReportInnerModuleSuccessMapper.toReportDocumentId(result)

    }

    override fun removeReportFile(reportFileId: ReportDocumentId): ReportDocumentId {
        val result = filesModuleService.removeFile(reportFileId.value).getOrElse {
            throw ReportInnerModuleErrorMapper.exceptionToModuleException(it)
        }
        return ReportInnerModuleSuccessMapper.toReportDocumentId(result)
    }

    override fun updateReportFile(reportFileId: ReportDocumentId, reportFile: ReportFile): ReportDocumentId {
        val result = filesModuleService.updateFile(
            fileId = reportFileId.value,
            documentFileBaseName = reportFile.fileName,
            documentFileData = reportFile.fileData
        ).getOrElse {
            throw ReportInnerModuleErrorMapper.exceptionToModuleException(it)
        }
        return ReportInnerModuleSuccessMapper.toReportDocumentId(result)
    }
}