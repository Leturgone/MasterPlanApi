package api.masterplan.app.plansModule.infrastructure.database.entity

import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "executor_has_task")
class TaskHasExecutorEntity(
    @EmbeddedId
    val id: TaskExecutorId
){
    override fun toString() = "TaskHasExecutorEntity(id=$id)"

    override fun hashCode() = id.hashCode()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as TaskHasExecutorEntity
        return id == other.id
    }
}

