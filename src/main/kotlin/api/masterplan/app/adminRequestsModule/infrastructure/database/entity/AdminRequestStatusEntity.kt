package api.masterplan.app.adminRequestsModule.infrastructure.database.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "admin_request_status")
class AdminRequestStatusEntity(
    @Id
    @Column(name = "id")
    val id: Int,

    @Column(name = "status",unique = true,nullable = false, length = 45)
    var status: String,
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as AdminRequestStatusEntity
        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()

    override fun toString(): String = "AdminRequestStatusEntity(id=$id, status='$status')"
}