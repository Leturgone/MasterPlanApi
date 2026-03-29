package api.masterplan.app.adminRequestsModule.infrastructure.database.mapper

import api.masterplan.app.adminRequestsModule.domain.exception.AdminRequestException
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestStatus
import api.masterplan.app.adminRequestsModule.infrastructure.database.entity.AdminRequestStatusEntity

internal object AdminRequestStatusDatabaseMapper {
    fun toDomain(entity: AdminRequestStatusEntity): AdminRequestStatus {
        return try {
            AdminRequestStatus.valueOf(entity.status.uppercase())
        }catch (_: IllegalArgumentException){
            throw AdminRequestException.InvalidAdminRequestStatus(entity.status.uppercase())
        }
    }

    fun toEntity(statusSet: Set<AdminRequestStatusEntity>, status: AdminRequestStatus): AdminRequestStatusEntity {
        val statusByTitle = statusSet.associateBy { it.status.uppercase() }
        val domainStatus = statusByTitle[status.name] ?: throw AdminRequestException.InvalidAdminRequestStatus(status.name)
        return domainStatus
    }
}