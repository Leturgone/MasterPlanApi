package api.masterplan.app.filesModule.application.service

import api.masterplan.app.filesModule.application.mapper.FilesToDetailsMapper
import api.masterplan.app.filesModule.domain.dtos.DocumentFileDetails
import api.masterplan.app.filesModule.domain.exceptions.FilesException
import api.masterplan.app.filesModule.domain.interfaces.DocumentFileRepository
import api.masterplan.app.filesModule.domain.interfaces.DocumentFileService
import api.masterplan.app.filesModule.domain.model.entity.DocumentFile
import api.masterplan.app.filesModule.domain.model.value.DocumentFileBaseName
import api.masterplan.app.filesModule.domain.model.value.DocumentFileData
import api.masterplan.app.filesModule.domain.model.value.DocumentFileId
import api.masterplan.app.logging.annotations.LoggingMethod
import org.springframework.stereotype.Service

@Service
class DocumentFileServiceImpl(
    private val documentFileRepository: DocumentFileRepository
): DocumentFileService {

    @LoggingMethod("filesModule")
    override fun uploadFile(documentFileBaseName: DocumentFileBaseName, documentFileData: DocumentFileData): DocumentFileId {
        if (documentFileRepository.isFileExist(documentFileBaseName)) throw FilesException.FileAlreadyExists(
            documentFileBaseName
        )

        val file = DocumentFile.create(
            baseName = documentFileBaseName,
            fileData = documentFileData
        )

        val fileId = documentFileRepository.saveFile(file)
            ?: throw FilesException.FailedToCreateFile(file.fileName)

        return fileId
    }

    @LoggingMethod("filesModule")
    override fun removeFile(fileId: DocumentFileId): DocumentFileId {
        val file = documentFileRepository.getFile(fileId)?: throw FilesException.FileNotExist(fileId)
        val deletedFileId = documentFileRepository.removeFile(fileId,file.fileName)
            ?: throw FilesException.FailedToDeleteFile(fileId)

        return deletedFileId
    }

    @LoggingMethod("filesModule")
    override fun updateFile(fileId: DocumentFileId,documentFileBaseName: DocumentFileBaseName,
                                    documentFileData: DocumentFileData): DocumentFileId {
        val file = documentFileRepository.getFile(fileId)?: throw FilesException.FileNotExist(fileId)
        val updatedFile = file.update(documentFileBaseName,documentFileData)
        val updatedFileId = documentFileRepository.updateFile(
            fileId = fileId,
            oldFileName = file.fileName,
            updatedDocumentFile = updatedFile
        ) ?: throw FilesException.FailedToUpdateFile(fileId)
        return updatedFileId
    }

    @LoggingMethod("filesModule")
    override fun downloadFile(fileId: DocumentFileId): DocumentFileDetails {
        val file = documentFileRepository.getFile(fileId)?: throw FilesException.FileNotExist(fileId)
        return FilesToDetailsMapper.toDocumentFileDetails(file)
    }
}