package api.masterplan.app.adminRequestsModule.application.usecase

import api.masterplan.app.adminRequestsModule.application.command.CreateAdminAnswerCommand
import api.masterplan.app.adminRequestsModule.domain.interfaces.AdminRequestsService
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminAnswerId
import org.springframework.stereotype.Service

@Service
class CreateAdminAnswerUseCase(
    private val adminRequestsService: AdminRequestsService
) {
    operator fun invoke(command: CreateAdminAnswerCommand): Result<AdminAnswerId>{
        return try {
            val answerId = adminRequestsService.createAdminAnswer(
                id = command.id,
                title = command.title,
                description = command.description,
                adminRequestId = command.adminRequestId,
            )
            Result.success(answerId)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}