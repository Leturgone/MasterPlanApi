package api.masterplan.app.adminRequestsModule.application.usecase

import api.masterplan.app.adminRequestsModule.application.command.ChangeAdminRequestStatusCommand
import api.masterplan.app.adminRequestsModule.domain.interfaces.AdminRequestsService
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestId
import org.springframework.stereotype.Service

@Service
class ChangeAdminRequestStatusUseCase(
    private val adminRequestsService: AdminRequestsService
) {
    operator fun invoke(command: ChangeAdminRequestStatusCommand): Result<AdminRequestId>{
        return try {
            val requestId = adminRequestsService.changeAdminRequestStatus(
                id = command.id,
                status = command.status
            )
            Result.success(requestId)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}