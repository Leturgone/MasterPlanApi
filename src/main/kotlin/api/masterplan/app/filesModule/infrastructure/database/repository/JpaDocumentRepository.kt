package api.masterplan.app.filesModule.infrastructure.database.repository

import api.masterplan.app.filesModule.infrastructure.database.entity.DocumentEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface JpaDocumentRepository: CrudRepository<DocumentEntity, UUID> {

    fun existsByNameStartingWith(baseName: String): Boolean

}