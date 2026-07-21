package api.masterplan.app.filesModule.infrastructure.database.repository

import api.masterplan.app.filesModule.infrastructure.database.entity.DocumentEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

interface JpaDocumentRepository: CrudRepository<DocumentEntity, UUID> {

}