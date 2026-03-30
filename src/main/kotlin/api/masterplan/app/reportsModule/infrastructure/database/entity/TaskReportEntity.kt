package api.masterplan.app.reportsModule.infrastructure.database.entity

import jakarta.persistence.CascadeType
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
@Table(name = "task_report")
class TaskReportEntity(
    @Id
    @Column(name = "id")
    val id: UUID,

    @Column(name = "title", length = 100, nullable = false)
    val title: String,

    @Column(name = "c_date", nullable = false)
    val creationDate: LocalDateTime,

    @Column(name = "edit_date")
    val editDate: LocalDateTime? = null,

    @Column(name = "description", length = 255, nullable = false)
    val description : String? = null,

    @ManyToOne(fetch = FetchType.LAZY,cascade = [CascadeType.PERSIST])
    @JoinColumn(
        name = "report_status_id",
        referencedColumnName = "id",
        nullable = false,
    )
    val reportStatus: ReportStatusEntity,

    @Column(name = "employee_id",nullable = false)
    val employeeId: UUID,

    @Column(name = "task_id ",nullable = false)
    val taskId: UUID,

    @Column(name = "document_id", nullable = false)
    val documentId: UUID,
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as TaskReportEntity
        return id == other.id
    }

    override fun hashCode() = id.hashCode()

    override fun toString() = "TaskReportEntity(id=$id, title='$title', creationDate=$creationDate, employeeId=$employeeId, taskId=$taskId, documentId=$documentId)"
}
