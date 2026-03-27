package api.masterplan.app.filesModule.presentation.mapper

import api.masterplan.app.filesModule.domain.dtos.DocumentFileDetails
import api.masterplan.app.filesModule.presentation.dto.response.FileResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

object FilesDomainToResponseMapper {
    fun toFileResponse(file: DocumentFileDetails): FileResponse{
        val contentType = "application/pdf"

        val headers = HttpHeaders()
        headers.contentType = MediaType.parseMediaType(contentType)
        headers.set("Content-ID", file.fileId.value.toString())
        headers.setContentDispositionFormData("file", "application/pdf")
        headers.contentLength = file.fileData.value.size.toLong()

        return FileResponse(
            fileHeaders = headers,
            fileData = file.fileData.value
        )
    }
}