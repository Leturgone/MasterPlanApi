package api.masterplan.app.filesModule.infrastructure.intermodule

import api.masterplan.app.apiContracts.files.FileDataDto
import api.masterplan.app.filesModule.domain.dtos.ExportFileDetails

object InterModuleFileToDtoSuccessMapper {
    fun toDto(file:ExportFileDetails): FileDataDto{
        return FileDataDto(
            fileData = file.fileData.value,
            fileName = file.fileName.value
        )
    }
}