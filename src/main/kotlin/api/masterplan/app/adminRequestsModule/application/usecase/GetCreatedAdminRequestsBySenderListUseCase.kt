package api.masterplan.app.adminRequestsModule.application.usecase

import api.masterplan.app.adminRequestsModule.application.command.GetCreatedAdminRequestsBySenderListCommand
import api.masterplan.app.adminRequestsModule.domain.dtos.AdminRequestDetails
import api.masterplan.app.adminRequestsModule.domain.interfaces.AdminRequestsService
import org.springframework.stereotype.Service

@Service
class GetCreatedAdminRequestsBySenderListUseCase(
    private val adminRequestsService: AdminRequestsService
){
    operator fun invoke(command: GetCreatedAdminRequestsBySenderListCommand): Result<List<AdminRequestDetails>>{
        return try {
            val list = adminRequestsService.getCreatedAdminRequestsBySenderList(command.senderId)
            Result.success(list)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}
