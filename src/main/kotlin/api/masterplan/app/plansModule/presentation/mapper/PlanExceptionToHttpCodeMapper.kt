package api.masterplan.app.plansModule.presentation.mapper

import api.masterplan.app.plansModule.domain.exceptions.PlanException
import org.springframework.http.HttpStatus

object PlanExceptionToHttpCodeMapper {
    fun exceptionToHttpCode(ex: Exception): HttpStatus{
        return when (ex) {
            is PlanException.FailedToAssignDocumentToPlan -> HttpStatus.INTERNAL_SERVER_ERROR
            is PlanException.FailedToAssignDocumentToTask -> HttpStatus.INTERNAL_SERVER_ERROR
            is PlanException.FailedToCreatePlan -> HttpStatus.INTERNAL_SERVER_ERROR
            is PlanException.FailedToDeletePlan -> HttpStatus.INTERNAL_SERVER_ERROR
            is PlanException.FailedToDeleteTask -> HttpStatus.INTERNAL_SERVER_ERROR
            is PlanException.FailedToSaveTask -> HttpStatus.INTERNAL_SERVER_ERROR
            is PlanException.FailedToUpdatePlan -> HttpStatus.INTERNAL_SERVER_ERROR
            is PlanException.FailedToUpdatePlanStatus -> HttpStatus.INTERNAL_SERVER_ERROR
            is PlanException.FailedToUpdateTask -> HttpStatus.INTERNAL_SERVER_ERROR
            is PlanException.FailedToUpdateTaskStatus -> HttpStatus.INTERNAL_SERVER_ERROR
            is PlanException.InvalidPlanDesc -> HttpStatus.BAD_REQUEST
            is PlanException.InvalidPlanStatusTitle -> HttpStatus.BAD_REQUEST
            is PlanException.InvalidPlanTitle -> HttpStatus.BAD_REQUEST
            is PlanException.InvalidTaskDesc -> HttpStatus.BAD_REQUEST
            is PlanException.InvalidTaskStatusTitle -> HttpStatus.BAD_REQUEST
            is PlanException.InvalidTaskTitle -> HttpStatus.BAD_REQUEST
            is PlanException.InvalidTaskUrgency -> HttpStatus.BAD_REQUEST
            is PlanException.PlanAlreadyExists -> HttpStatus.CONFLICT
            is PlanException.PlanNotExist -> HttpStatus.NOT_FOUND
            is PlanException.TaskAlreadyExists -> HttpStatus.CONFLICT
            is PlanException.TaskNotExist -> HttpStatus.NOT_FOUND
            else -> HttpStatus.INTERNAL_SERVER_ERROR
        }
    }
}