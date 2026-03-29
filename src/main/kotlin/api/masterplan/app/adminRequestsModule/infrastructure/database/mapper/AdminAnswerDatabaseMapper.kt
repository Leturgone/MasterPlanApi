package api.masterplan.app.adminRequestsModule.infrastructure.database.mapper

import api.masterplan.app.adminRequestsModule.domain.model.entity.AdminAnswer
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminAnswerDescription
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminAnswerId
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminAnswerTitle
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestId
import api.masterplan.app.adminRequestsModule.infrastructure.database.entity.AdminAnswerEntity

object AdminAnswerDatabaseMapper {
    fun toDomain(entity: AdminAnswerEntity): AdminAnswer {
        return AdminAnswer.create(
            id = AdminAnswerId(entity.id),
            title = AdminAnswerTitle.validate(entity.title),
            description = AdminAnswerDescription.validate(entity.description),
            adminRequestId = AdminRequestId(entity.adminRequestId)
        )
    }

    fun toDomain(entityList: List<AdminAnswerEntity>): List<AdminAnswer> {
        return entityList.map { toDomain(it) }
    }

    fun toEntity(model: AdminAnswer): AdminAnswerEntity {
        return AdminAnswerEntity(
            id = model.id.value,
            title = model.title.value,
            description = model.description.value,
            adminRequestId = model.adminRequestId.value,
        )
    }

}