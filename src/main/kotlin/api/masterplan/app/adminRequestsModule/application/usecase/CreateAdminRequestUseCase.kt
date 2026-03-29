package api.masterplan.app.adminRequestsModule.application.usecase

import api.masterplan.app.adminRequestsModule.application.command.CreateAdminRequestCommand
import api.masterplan.app.adminRequestsModule.domain.interfaces.AdminRequestsService
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestId
import org.springframework.stereotype.Service

@Service
class CreateAdminRequestUseCase(
    private val adminRequestsService: AdminRequestsService
) {
    operator fun invoke(command: CreateAdminRequestCommand): Result<AdminRequestId>{
        return try {
            val requestId = adminRequestsService.createAdminRequest(
                id = command.id,
                title = command.title,
                description = command.description,
                senderId = command.senderId
            )
            Result.success(requestId)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}