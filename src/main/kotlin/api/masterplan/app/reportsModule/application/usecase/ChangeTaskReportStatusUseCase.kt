package api.masterplan.app.reportsModule.application.usecase

import api.masterplan.app.reportsModule.application.command.ChangeTaskReportStatusCommand
import api.masterplan.app.reportsModule.domain.interfaces.TaskReportService
import api.masterplan.app.reportsModule.domain.models.value.TaskReportId
import org.springframework.stereotype.Service

@Service
class ChangeTaskReportStatusUseCase(
    private val taskReportService: TaskReportService
) {
    operator fun invoke(command: ChangeTaskReportStatusCommand): Result<TaskReportId> {
        return try {
            val planReportId = taskReportService.changeTaskReportStatus(
                command.reportId,command.status
            )
            Result.success(planReportId)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}