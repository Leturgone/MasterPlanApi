package api.masterplan.app.filesModule.presentation.api.controller

import api.masterplan.app.filesModule.application.command.DownloadFileCommand
import api.masterplan.app.filesModule.application.usecase.DownloadFileUseCase
import api.masterplan.app.filesModule.presentation.api.exceptionHandler.FilesControllerExceptionHandler
import api.masterplan.app.filesModule.presentation.dto.response.FileResponse
import api.masterplan.app.filesModule.presentation.dto.response.FilesErrorResponse
import api.masterplan.app.filesModule.presentation.mapper.FIleRequestToDomainMapper
import api.masterplan.app.filesModule.presentation.mapper.FilesDomainToResponseMapper
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@FilesControllerExceptionHandler
@RequestMapping("/api/v1/files")
@Tag(name = "Files", description = "Скачивание файлов")
class FilesController(
    private val downloadFileUseCase: DownloadFileUseCase
) {

    @Operation(
        summary = "Скачивание файла по id",
        description = "Скачивание файла по id",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Файл успешно получен",
                content = [Content(schema = Schema(implementation = FileResponse::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Файл не найден",
                content = [Content(schema = Schema(implementation = FilesErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера: сбой при скачивании файла",
                content = [Content(schema = Schema(implementation = FilesErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли сотрудника"
            )

        ]
    )
    @GetMapping("/emp/downloadFile/{fileId}/")
    fun downloadFile(@PathVariable(value = "fileId") fileId: UUID): ResponseEntity<ByteArray>{
        val command = DownloadFileCommand(
            fileId = FIleRequestToDomainMapper.toDocumentFileId(fileId)
        )
        val result = downloadFileUseCase(command).getOrThrow()
        val resp = FilesDomainToResponseMapper.toFileResponse(result)
        return ResponseEntity.ok().headers(resp.fileHeaders).body(resp.fileData)
    }
}