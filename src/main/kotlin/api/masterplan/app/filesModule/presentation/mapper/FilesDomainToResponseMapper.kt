package api.masterplan.app.filesModule.presentation.mapper

import api.masterplan.app.filesModule.domain.dtos.DocumentFileDetails
import api.masterplan.app.filesModule.presentation.dto.response.FileResponse

object FilesDomainToResponseMapper {
    fun toFileResponse(file: DocumentFileDetails): FileResponse{
        return FileResponse(
            fileId = file.fileId.value,
            fileName = file.fileName.value,
            fileData = file.fileData.value
        )
    }
}