package api.masterplan.app.filesModule.infrastructure.intermodule

import api.masterplan.app.apiContracts.files.FileDataDto
import api.masterplan.app.apiContracts.files.FilesModuleService
import api.masterplan.app.filesModule.application.command.ExportListToExelCommand
import api.masterplan.app.filesModule.application.command.RemoveFileCommand
import api.masterplan.app.filesModule.application.command.UpdateFileCommand
import api.masterplan.app.filesModule.application.command.UploadFileCommand
import api.masterplan.app.filesModule.application.usecase.ExportListToExcelUseCase
import api.masterplan.app.filesModule.application.usecase.RemoveFileUseCase
import api.masterplan.app.filesModule.application.usecase.UpdateFileUseCase
import api.masterplan.app.filesModule.application.usecase.UploadFileUseCase
import api.masterplan.app.filesModule.domain.model.value.DocumentFileBaseName
import api.masterplan.app.filesModule.domain.model.value.DocumentFileData
import api.masterplan.app.filesModule.domain.model.value.DocumentFileId
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class FilesModuleServiceImpl(
    private val uploadFileUseCase: UploadFileUseCase,
    private val removeFileUseCase: RemoveFileUseCase,
    private val updateFileUseCase: UpdateFileUseCase,
    private val exportListToExcelUseCase: ExportListToExcelUseCase
): FilesModuleService {
    override fun <T : Any> exportListToExcel(fileName: String, data: List<T>): Result<FileDataDto> {
        return try {
            val command = ExportListToExelCommand(
                fileName = DocumentFileBaseName.validate(fileName),
                data = data
            )
            val result = exportListToExcelUseCase(command).getOrThrow()
            val mappedResult = InterModuleFileToDtoSuccessMapper.toDto(result)
            Result.success(mappedResult)
        }catch (e: Exception){
            val exception = InterModuleFileToDtoErrorMapper.toDto(e)
            Result.failure(exception)
        }
    }

    override fun uploadFile(documentFileBaseName: String, documentFileData: ByteArray): Result<UUID> {
        return try {
            val command = UploadFileCommand(
                documentFileBaseName = DocumentFileBaseName.validate(documentFileBaseName),
                documentFileData = DocumentFileData(documentFileData)
            )
            val result = uploadFileUseCase(command).getOrThrow()
            val mappedResult = result.value
            Result.success(mappedResult)
        }catch (e: Exception){
            val exception = InterModuleFileToDtoErrorMapper.toDto(e)
            Result.failure(exception)
        }
    }

    override fun removeFile(fileId: UUID): Result<UUID> {
        return try {
            val command = RemoveFileCommand(
                fileId = DocumentFileId(fileId)
            )
            val result = removeFileUseCase(command).getOrThrow()
            val mappedResult = result.value
            Result.success(mappedResult)
        }catch (e: Exception){
            val exception = InterModuleFileToDtoErrorMapper.toDto(e)
            Result.failure(exception)
        }
    }

    override fun updateFile(fileId: UUID, documentFileBaseName: String, documentFileData: ByteArray): Result<UUID> {
        return try {
            val command = UpdateFileCommand(
                fileId = DocumentFileId(fileId),
                documentFileBaseName = DocumentFileBaseName.validate(documentFileBaseName),
                documentFileData = DocumentFileData(documentFileData)
            )
            val result = updateFileUseCase(command).getOrThrow()
            val mappedResult = result.value
            Result.success(mappedResult)
        }catch (e: Exception){
            val exception = InterModuleFileToDtoErrorMapper.toDto(e)
            Result.failure(exception)
        }
    }
}