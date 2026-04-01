package api.masterplan.app.adminRequestsModule.application.usecase

import api.masterplan.app.adminRequestsModule.application.command.ChangeAdminRequestStatusCommand
import api.masterplan.app.adminRequestsModule.application.port.RequestsNotificationPort
import api.masterplan.app.adminRequestsModule.domain.interfaces.AdminRequestsService
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestId
import org.springframework.stereotype.Service

@Service
class ChangeAdminRequestStatusUseCase(
    private val adminRequestsService: AdminRequestsService,
    private val requestsNotificationPort : RequestsNotificationPort
) {
    operator fun invoke(command: ChangeAdminRequestStatusCommand): Result<AdminRequestId>{
        return try {
            val request = adminRequestsService.changeAdminRequestStatus(
                id = command.id,
                status = command.status
            )

            requestsNotificationPort.sendRequestChangeStatusNotification(
                consumerId = request.senderId.value,
                requestTitle = request.title,
                requestStatus = request.status,
            )
            Result.success(request.id)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}