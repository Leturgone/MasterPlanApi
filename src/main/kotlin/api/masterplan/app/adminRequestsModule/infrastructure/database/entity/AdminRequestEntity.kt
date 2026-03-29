package api.masterplan.app.adminRequestsModule.infrastructure.database.entity

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "admin_request")
class AdminRequestEntity(
    @Id
    @Column(name = "id")
    val id: UUID,

    @Column(name = "title", length = 100, nullable = false)
    val title: String,

    @Column(name = "description", length = 255, nullable = false)
    val description : String,

    @Column(name = "c_date", nullable = false)
    val creationDate: LocalDateTime,

    @Column(name = "sender_id",nullable = false)
    val senderId: UUID,

    @ManyToOne(fetch = FetchType.LAZY,cascade = [CascadeType.PERSIST])
    @JoinColumn(
        name = "admin_request_status_id",
        referencedColumnName = "id",
        nullable = false,
    )
    val taskStatus: AdminRequestStatusEntity
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as AdminRequestEntity
        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()

    override fun toString() = "AdminRequestEntity(id=$id, title=$title, description=$description, c_date=$creationDate, senderId=$senderId)"
}
