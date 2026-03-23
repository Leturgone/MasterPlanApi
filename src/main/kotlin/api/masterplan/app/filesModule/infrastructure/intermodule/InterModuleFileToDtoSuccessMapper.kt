package api.masterplan.app.filesModule.infrastructure.intermodule

import api.masterplan.app.filesModule.ExportFileDto
import api.masterplan.app.filesModule.FileDataDto
import api.masterplan.app.filesModule.domain.dtos.DocumentFileDetails
import api.masterplan.app.filesModule.domain.dtos.ExportFileDetails

object InterModuleFileToDtoSuccessMapper {
    fun toDto(file:ExportFileDetails): ExportFileDto{
        return ExportFileDto(
            fileData = file.fileData.value,
            fileName = file.fileName.value
        )
    }

    fun toDto(file: DocumentFileDetails): FileDataDto {
        return FileDataDto(
            fileId = file.fileId.value,
            fileData = file.fileData.value,
            fileName = file.fileName.value
        )
    }
}