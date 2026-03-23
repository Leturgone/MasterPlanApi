package api.masterplan.app.filesModule.infrastructure.repository

import api.masterplan.app.filesModule.domain.interfaces.DocumentFileRepository
import api.masterplan.app.filesModule.domain.model.entity.DocumentFile
import api.masterplan.app.filesModule.domain.model.value.DocumentFileBaseName
import api.masterplan.app.filesModule.domain.model.value.DocumentFileId
import api.masterplan.app.filesModule.domain.model.value.DocumentFileName
import api.masterplan.app.filesModule.infrastructure.database.dto.DocumentEntityDto
import api.masterplan.app.filesModule.infrastructure.database.mapper.DocumentDatabaseMapper
import api.masterplan.app.filesModule.infrastructure.database.repository.JpaDocumentRepository
import api.masterplan.app.filesModule.infrastructure.filesStorage.repository.FileStorageRepository
import api.masterplan.app.logging.LoggingDatabaseMethod
import org.springframework.stereotype.Repository

@Repository
class DocumentFileRepositoryImpl(
    private val jpaDocumentRepository: JpaDocumentRepository,
    private val fileStorageRepository: FileStorageRepository
): DocumentFileRepository {

    @LoggingDatabaseMethod(moduleName = "filesModule")
    override fun isFileExist(documentFileBaseName: DocumentFileBaseName): Boolean {
        return jpaDocumentRepository.existsByBaseName(documentFileBaseName.value)
    }


    @LoggingDatabaseMethod(moduleName = "filesModule")
    override fun saveFile(documentFile: DocumentFile): DocumentFileId? {
        val savedPath = fileStorageRepository.writeFile(
            fileName = documentFile.fileName.value,
            fileBytes = documentFile.fileData.value
        )?: return null

        val dto = DocumentEntityDto(
            id = documentFile.fileId,
            name = documentFile.fileName,
            path = savedPath
        )

        val documentEntity = DocumentDatabaseMapper.toEntity(dto)

        val savedId = jpaDocumentRepository.save(documentEntity).id

        return DocumentFileId(savedId)
    }


    @LoggingDatabaseMethod(moduleName = "filesModule")
    override fun removeFile(fileId: DocumentFileId,oldFileName: DocumentFileName): DocumentFileId? {
        jpaDocumentRepository.deleteById(fileId.value)
        fileStorageRepository.delete(oldFileName.value)?: return null
        return fileId
    }


    @LoggingDatabaseMethod(moduleName = "filesModule")
    override fun updateFile(fileId: DocumentFileId, oldFileName: DocumentFileName, updatedDocumentFile: DocumentFile): DocumentFileId? {
        val updatedPath = fileStorageRepository.writeFile(
            fileName = updatedDocumentFile.fileName.value,
            fileBytes = updatedDocumentFile.fileData.value
        )?: return null

        fileStorageRepository.delete(oldFileName.value)?: return null

        val dto = DocumentEntityDto(
            id = updatedDocumentFile.fileId,
            name = updatedDocumentFile.fileName,
            path = updatedPath
        )

        val entity = DocumentDatabaseMapper.toEntity(dto)

        val updatedId = jpaDocumentRepository.save(entity).id

        return DocumentFileId(updatedId)

    }


    @LoggingDatabaseMethod(moduleName = "filesModule")
    override fun getFile(fileId: DocumentFileId): DocumentFile? {
        val fileEntity = jpaDocumentRepository.findById(fileId.value).get()
        val fileBytes = fileStorageRepository.readFile(fileEntity.name) ?: return null
        return DocumentDatabaseMapper.toDomain(fileEntity, fileBytes)
    }
}