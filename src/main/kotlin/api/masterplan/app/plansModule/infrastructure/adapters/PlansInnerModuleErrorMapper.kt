package api.masterplan.app.plansModule.infrastructure.adapters

import api.masterplan.app.apiContracts.employee.EmployeeModuleErrorDto
import api.masterplan.app.apiContracts.files.FilesModuleErrorDto
import api.masterplan.app.plansModule.domain.exceptions.PlanException

object PlansInnerModuleErrorMapper {
    fun exceptionToModuleException(ex: Throwable): PlanException{
        return when(ex){
            is EmployeeModuleErrorDto.EmployeeNotExist -> PlanException.ExecutorNotExist(ex.employeeId)
            is EmployeeModuleErrorDto.InternalServerError -> PlanException.InternalServerError(ex.message)
            is FilesModuleErrorDto.FailedToCreateFile -> PlanException.FailedToCreatePlanFile(ex.fileName)
            is FilesModuleErrorDto.FailedToDeleteFile -> PlanException.FailedToDeletePlanFile(ex.fileId)
            is FilesModuleErrorDto.FailedToUpdateFile -> PlanException.FailedToDeletePlanFile(ex.fileId)
            is FilesModuleErrorDto.FileAlreadyExists -> PlanException.PlanFileAlreadyExists(ex.fileName)
            is FilesModuleErrorDto.FileNotExist -> PlanException.PlanFileNotExist(ex.fileId)
            is FilesModuleErrorDto.InternalServerError -> PlanException.InternalServerError(ex.message)
            is FilesModuleErrorDto.InvalidFileName -> PlanException.InvalidPlanFileName(ex.errorMessage)
            else -> PlanException.InternalServerError(ex.message)
        }
    }

}