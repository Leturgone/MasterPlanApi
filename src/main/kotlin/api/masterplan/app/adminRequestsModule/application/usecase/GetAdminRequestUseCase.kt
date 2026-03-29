package api.masterplan.app.adminRequestsModule.application.usecase

import api.masterplan.app.adminRequestsModule.application.command.GetAdminRequestCommand
import api.masterplan.app.adminRequestsModule.domain.dtos.AdminRequestDetails
import api.masterplan.app.adminRequestsModule.domain.interfaces.AdminRequestsService
import org.springframework.stereotype.Service

@Service
class GetAdminRequestUseCase(
    private val adminRequestsService: AdminRequestsService
) {
    operator fun invoke(command: GetAdminRequestCommand): Result<AdminRequestDetails>{
        return try {
            val adminRequest = adminRequestsService.getAdminRequest(command.id)
            Result.success(adminRequest)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}