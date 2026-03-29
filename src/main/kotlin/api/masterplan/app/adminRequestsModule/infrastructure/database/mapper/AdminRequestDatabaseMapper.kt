package api.masterplan.app.adminRequestsModule.infrastructure.database.mapper

import api.masterplan.app.adminRequestsModule.domain.model.entity.AdminRequest
import api.masterplan.app.adminRequestsModule.domain.model.value.*
import api.masterplan.app.adminRequestsModule.infrastructure.database.entity.AdminRequestEntity
import api.masterplan.app.adminRequestsModule.infrastructure.database.entity.AdminRequestStatusEntity

object AdminRequestDatabaseMapper {
    fun toDomain(entity: AdminRequestEntity): AdminRequest {
        val domainStatus = AdminRequestStatusDatabaseMapper.toDomain(entity.taskStatus)
        return AdminRequest.create(
            id = AdminRequestId(entity.id),
            title = AdminRequestTitle.validate(entity.title),
            description = AdminRequestDescription.validate(entity.description),
            creationDate = AdminRequestCreationDate(entity.creationDate),
            senderId = AdminRequestSenderId(entity.senderId),
            status = domainStatus
        )
    }

    fun toDomain(entityList: List<AdminRequestEntity>): List<AdminRequest> {
        return entityList.map { toDomain(it) }
    }

    fun toEntity(model: AdminRequest,statusSet: Set<AdminRequestStatusEntity>): AdminRequestEntity {
        val statusEntity = AdminRequestStatusDatabaseMapper.toEntity(statusSet,model.status)
        return AdminRequestEntity(
            id = model.id.value,
            title = model.title.value,
            description = model.description.value,
            creationDate = model.creationDate.value,
            senderId = model.senderId.value,
            taskStatus = statusEntity
        )
    }
}