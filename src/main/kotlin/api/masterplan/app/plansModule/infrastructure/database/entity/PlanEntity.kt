package api.masterplan.app.plansModule.infrastructure.database.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "plan")
data class PlanEntity(
    @Id
    @Column(name = "id")
    val id: UUID,

    @Column(name = "title", length = 100, nullable = false)
    val title: String,

    @Column(name = "description", length = 255, nullable = false)
    val description : String,

    @Column(name = "start_date", nullable = false)
    val startDate: LocalDateTime,

    @Column(name = "end_date", nullable = true)
    val endDate: LocalDateTime,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "plan_status_id",
        referencedColumnName = "id",
        nullable = false,
    )
    val planStatus: PlanStatusEntity,

    @Column(name = "director_id",nullable = false)
    val directorId: UUID? = null,

    @Column(name = "document_id")
    val documentId: UUID? = null,
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as PlanEntity
        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()

    override fun toString(): String  = "PlanEntity(id=$id, title='$title', description='$description', startDate=$startDate, endDate=$endDate, director=$directorId)"
}