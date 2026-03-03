package api.masterplan.app.plansModule.presentation.mapper

import api.masterplan.app.plansModule.application.dto.PlanFile
import api.masterplan.app.plansModule.application.dto.TaskFile
import api.masterplan.app.plansModule.domain.exceptions.PlanException
import api.masterplan.app.plansModule.domain.model.value.ExecutorId
import api.masterplan.app.plansModule.domain.model.value.PlanDate
import api.masterplan.app.plansModule.domain.model.value.PlanDescription
import api.masterplan.app.plansModule.domain.model.value.PlanDirectorId
import api.masterplan.app.plansModule.domain.model.value.PlanDocumentId
import api.masterplan.app.plansModule.domain.model.value.PlanId
import api.masterplan.app.plansModule.domain.model.value.PlanStatus
import api.masterplan.app.plansModule.domain.model.value.PlanTitle
import api.masterplan.app.plansModule.domain.model.value.TaskDate
import api.masterplan.app.plansModule.domain.model.value.TaskDescription
import api.masterplan.app.plansModule.domain.model.value.TaskId
import api.masterplan.app.plansModule.domain.model.value.TaskStatus
import api.masterplan.app.plansModule.domain.model.value.TaskTitle
import java.time.LocalDate
import java.util.UUID

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