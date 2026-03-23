package api.masterplan.app.filesModule.infrastructure.database.repository

import api.masterplan.app.filesModule.infrastructure.database.entity.DocumentEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface JpaDocumentRepository: CrudRepository<DocumentEntity, UUID> {

    @Query("SELECT p FROM Document p WHERE p.name LIKE CONCAT(:baseName, '%')")
    fun existsByBaseName(baseName: String): Boolean

}