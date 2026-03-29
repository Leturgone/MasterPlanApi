package api.masterplan.app.adminRequestsModule.application.usecase

import api.masterplan.app.adminRequestsModule.application.command.GetAdminAnswerForRequestCommand
import api.masterplan.app.adminRequestsModule.domain.dtos.AdminAnswerDetails
import api.masterplan.app.adminRequestsModule.domain.interfaces.AdminRequestsService
import org.springframework.stereotype.Service

@Service
class GetAdminAnswerForRequestUseCase(
    private val adminRequestsService: AdminRequestsService
) {
    operator fun invoke(command: GetAdminAnswerForRequestCommand): Result<AdminAnswerDetails>{
        return try {
            val answer = adminRequestsService.getAdminAnswerForRequest(command.id)
            Result.success(answer)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}