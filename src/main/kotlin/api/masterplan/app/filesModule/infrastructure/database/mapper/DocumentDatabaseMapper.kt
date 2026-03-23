package api.masterplan.app.filesModule.infrastructure.database.mapper

import api.masterplan.app.filesModule.domain.model.entity.DocumentFile
import api.masterplan.app.filesModule.domain.model.value.DocumentFileData
import api.masterplan.app.filesModule.domain.model.value.DocumentFileId
import api.masterplan.app.filesModule.domain.model.value.DocumentFileName
import api.masterplan.app.filesModule.infrastructure.database.dto.DocumentEntityDto
import api.masterplan.app.filesModule.infrastructure.database.entity.DocumentEntity

object DocumentDatabaseMapper {
    fun toDto(entity: DocumentEntity): DocumentEntityDto {
        return DocumentEntityDto(
            id = DocumentFileId(entity.id),
            name = DocumentFileName(entity.name),
            path = entity.path
        )
    }

    fun toEntity(dto: DocumentEntityDto): DocumentEntity {
        return DocumentEntity(
            id = dto.id.value,
            name = dto.name.value,
            path = dto.path
        )
    }

    fun toDomain(entity: DocumentEntity, bytes: ByteArray): DocumentFile{
        return DocumentFile.create(
            id = DocumentFileId(entity.id),
            fileName = DocumentFileName(entity.name),
            fileData = DocumentFileData(bytes)
        )
    }
}