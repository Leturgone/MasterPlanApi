package api.masterplan.app.reportsModule.infrastructure.adapters

import api.masterplan.app.apiContracts.employee.EmployeeModuleErrorDto
import api.masterplan.app.apiContracts.files.FilesModuleErrorDto
import api.masterplan.app.reportsModule.domain.exceptions.ReportException
import api.masterplan.app.reportsModule.domain.exceptions.ReportException.*

internal object ReportInnerModuleErrorMapper {
    fun exceptionToModuleException(ex: Throwable): ReportException{
        return when(ex){
            is EmployeeModuleErrorDto.InternalServerError -> InternalServerError(ex.message)
            is EmployeeModuleErrorDto.EmployeeNotExist -> ExecutorNotExist(ex.employeeId)
            is FilesModuleErrorDto.FailedToCreateFile -> FailedToCreateReportFile(ex.fileName)
            is FilesModuleErrorDto.FailedToDeleteFile -> FailedToDeleteReportFile(ex.fileId)
            is FilesModuleErrorDto.FailedToUpdateFile -> FailedToUpdateReportFile(ex.fileId)
            is FilesModuleErrorDto.FileAlreadyExists -> ReportFileAlreadyExists(ex.fileName)
            is FilesModuleErrorDto.FileNotExist -> ReportFileNotExist(ex.fileId)
            is FilesModuleErrorDto.InternalServerError -> InternalServerError(ex.message)
            is FilesModuleErrorDto.InvalidFileName -> InvalidReportFileName(ex.message)
            else -> InternalServerError()
        }
    }
}