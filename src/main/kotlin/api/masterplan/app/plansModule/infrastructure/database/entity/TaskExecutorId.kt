package api.masterplan.app.plansModule.infrastructure.database.entity

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.util.UUID

@Embeddable
data class TaskExecutorId(
    @Column(name = "task_id")
    val taskId: UUID,

    @Column(name = "executor_id")
    val executorId: UUID
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as TaskExecutorId
        return this.executorId == other.executorId && this.taskId == other.taskId
    }

    override fun hashCode() = taskId.hashCode() + executorId.hashCode()

    override fun toString() = "TaskExecutorId(taskId=$taskId, executorId=$executorId)"
}
