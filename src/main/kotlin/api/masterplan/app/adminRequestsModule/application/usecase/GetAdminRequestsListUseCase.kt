package api.masterplan.app.adminRequestsModule.application.usecase

import api.masterplan.app.adminRequestsModule.domain.dtos.AdminRequestDetails
import api.masterplan.app.adminRequestsModule.domain.interfaces.AdminRequestsService
import org.springframework.stereotype.Service

@Service
class GetAdminRequestsListUseCase(
    private val adminRequestsService: AdminRequestsService
) {
    operator fun invoke(): Result<List<AdminRequestDetails>>{
        return try {
            val list = adminRequestsService.getAdminRequestsList()
            Result.success(list)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}