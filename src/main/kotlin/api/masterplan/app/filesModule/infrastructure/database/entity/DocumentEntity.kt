package api.masterplan.app.filesModule.infrastructure.database.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "document")
class DocumentEntity(
    @Id
    @Column(name = "id")
    val id: UUID,

    @Column(name = "name", nullable = false, length = 200)
    val name: String,

    @Column(name = "path", nullable = false, length = 255)
    val path: String
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as DocumentEntity
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "DocumentEntity(id=$id, name='$name', path='$path')"
    }
}
