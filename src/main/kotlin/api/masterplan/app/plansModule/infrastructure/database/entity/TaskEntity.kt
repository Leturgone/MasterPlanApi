package api.masterplan.app.plansModule.infrastructure.database.entity

import jakarta.persistence.*
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "task")
class TaskEntity(
    @Id
    @Column(name = "id")
    val id: UUID,

    @Column(name = "title", length = 100, nullable = false)
    val title: String,

    @Column(name = "description", length = 255, nullable = false)
    val description : String,

    @Column(name = "end_date", nullable = false)
    val endDate: LocalDate,

    @ManyToOne(fetch = FetchType.LAZY,cascade = [CascadeType.PERSIST])
    @JoinColumn(
        name = "task_status_id",
        referencedColumnName = "id",
        nullable = false,
    )
    val taskStatus: TaskStatusEntity,

    @Column(name = "plan_id",nullable = false)
    val planId: UUID,

    @Column(name = "document_id")
    val documentId: UUID? = null,

    @Column(name = "urgency", nullable = false)
    val urgency: Double,

    @OneToMany(mappedBy = "id.taskId", cascade = [CascadeType.ALL], orphanRemoval = true)
    val executorLinks: MutableSet<TaskHasExecutorEntity> = mutableSetOf()
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as TaskEntity
        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()

    override fun toString(): String = "TaskEntity(id=$id, title='$title', description='$description',planId=$planId)"
}