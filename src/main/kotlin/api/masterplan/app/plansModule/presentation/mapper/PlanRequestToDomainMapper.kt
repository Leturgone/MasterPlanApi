package api.masterplan.app.plansModule.presentation.mapper

import api.masterplan.app.plansModule.application.dto.PlanFile
import api.masterplan.app.plansModule.application.dto.TaskFile
import api.masterplan.app.plansModule.domain.exceptions.PlanException
import api.masterplan.app.plansModule.domain.model.entity.Plan
import api.masterplan.app.plansModule.domain.model.entity.Task
import api.masterplan.app.plansModule.domain.model.value.*
import java.time.LocalDate
import java.util.*

object PlanRequestToDomainMapper {
    fun toTaskStatus(status: String): TaskStatus{
        return try {
            TaskStatus.valueOf(status.uppercase())
        }catch (_: IllegalArgumentException){
            throw PlanException.InvalidPlanStatusTitle(status.uppercase())
        }
    }

    fun toPlanStatus(status: String): PlanStatus {
        return try {
            PlanStatus.valueOf(status.uppercase())
        }catch (_: IllegalArgumentException){
            throw PlanException.InvalidPlanStatusTitle(status.uppercase())
        }
    }

    fun toTask(id: UUID, title: String, description: String, urgency: Double, endDate: LocalDate,
               status: String, planId: UUID, documentId: UUID? = null, executorsIds: List<UUID>): Task{
        return Task.create(
            id = toTaskId(id),
            title = TaskTitle.validate(title),
            description = TaskDescription.validate(description),
            endDate = TaskDate(endDate),
            urgency = TaskUrgency.validate(urgency),
            planId = toPlanId(planId),
            documentId = documentId?.let { TaskDocumentId(documentId) },
            executorsId = toExecutorList(executorsIds),
        ).changeTaskStatus(
            toTaskStatus(status)
        )
    }

    fun toPlan(id: UUID, title: String, description: String,
               startDate: LocalDate? = null, endDate: LocalDate,
               status: String, directorId: UUID, documentId: UUID? = null,
    ): Plan{
        return Plan.create(
            id = PlanId(id),
            title = PlanTitle.validate(title),
            description = PlanDescription.validate(description),
            startDate = startDate?.let { PlanDate(startDate)},
            endDate = PlanDate(endDate),
            directorId = PlanDirectorId(directorId),
            documentId = documentId?.let { PlanDocumentId(it) },
        ).changePlanStatus(
            toPlanStatus(status)
        )
    }

    fun toPlanId(planId: UUID) = PlanId(planId)

    fun toTaskId(taskId: UUID) = TaskId(taskId)

    fun toExecutorId(executorId: UUID) = ExecutorId(executorId)

    fun toTaskTitle(taskTitle: String) = TaskTitle.validate(taskTitle)

    fun toTaskDescription(taskDescription: String) = TaskDescription.validate(taskDescription)

    fun toTaskDate(date: LocalDate) = TaskDate(date)

    fun toDirectorId(directorId: UUID) = PlanDirectorId(directorId)

    fun toPlanDocumentId(documentId: UUID) = PlanDocumentId(documentId)

    fun toPlanTitle(planTitle: String) = PlanTitle.validate(planTitle)

    fun toPlanDescription(planDescription: String) = PlanDescription.validate(planDescription)

    fun toPlanFile(fileName: String?,fileData: ByteArray?): PlanFile?{
        return if (fileName.isNullOrEmpty() || fileData == null) {
            null
        }else{
            PlanFile(fileData, fileName)
        }
    }

    fun toTaskFile(fileName: String?,fileData: ByteArray?): TaskFile?{
        return if (fileName.isNullOrEmpty() || fileData == null) {
            null
        }else{
            TaskFile(fileData, fileName)
        }
    }

    fun toExecutorList(executors: List<UUID>): MutableList<ExecutorId>{
        return executors.map { toExecutorId(it) }.toMutableList()
    }

    fun toPlanDate(date: LocalDate) = PlanDate(date)
}