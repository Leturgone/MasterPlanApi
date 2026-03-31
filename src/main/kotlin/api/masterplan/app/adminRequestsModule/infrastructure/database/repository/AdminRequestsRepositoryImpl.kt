package api.masterplan.app.adminRequestsModule.infrastructure.database.repository

import api.masterplan.app.adminRequestsModule.domain.interfaces.AdminRequestsRepository
import api.masterplan.app.adminRequestsModule.domain.model.entity.AdminAnswer
import api.masterplan.app.adminRequestsModule.domain.model.entity.AdminRequest
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminAnswerId
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestId
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestSenderId
import api.masterplan.app.adminRequestsModule.infrastructure.database.mapper.AdminAnswerDatabaseMapper
import api.masterplan.app.adminRequestsModule.infrastructure.database.mapper.AdminRequestDatabaseMapper
import api.masterplan.app.logging.annotations.LoggingDatabaseMethod
import org.springframework.stereotype.Repository
import kotlin.jvm.optionals.getOrElse

@Repository
class AdminRequestsRepositoryImpl(
    private val jpaAdminRequestRepository: JpaAdminRequestRepository,
    private val jpaAdminAnswerRepository: JpaAdminAnswerRepository,
    private val jpaAdminRequestStatusRepository: JpaAdminRequestStatusRepository
): AdminRequestsRepository {

    @LoggingDatabaseMethod(moduleName = "adminRequestsModule")
    override fun saveAdminRequest(adminRequest: AdminRequest): AdminRequestId {
        val statusSet = jpaAdminRequestStatusRepository.findAll().toSet()
        val adminRequestEntity = AdminRequestDatabaseMapper.toEntity(adminRequest, statusSet)
        val requestId = jpaAdminRequestRepository.save(adminRequestEntity).id
        return AdminRequestId(requestId)
    }

    @LoggingDatabaseMethod(moduleName = "adminRequestsModule")
    override fun saveAdminAnswer(adminAnswer: AdminAnswer): AdminAnswerId {
        val adminAnswerEntity = AdminAnswerDatabaseMapper.toEntity(adminAnswer)
        val answerId = jpaAdminAnswerRepository.save(adminAnswerEntity).id
        return AdminAnswerId(answerId)
    }

    @LoggingDatabaseMethod(moduleName = "adminRequestsModule")
    override fun updateAdminRequest(id: AdminRequestId, updatedAdminRequest: AdminRequest): AdminRequest {
        val statusSet = jpaAdminRequestStatusRepository.findAll().toSet()
        val updatedAdminRequestEntity = AdminRequestDatabaseMapper.toEntity(updatedAdminRequest, statusSet)
        val request = jpaAdminRequestRepository.save(updatedAdminRequestEntity)
        val model = AdminRequestDatabaseMapper.toDomain(request)
        return model
    }

    @LoggingDatabaseMethod(moduleName = "adminRequestsModule")
    override fun getAllAdminRequestsList(): List<AdminRequest> {
        val list = jpaAdminRequestRepository.findAll().toList()
        val modelsList = AdminRequestDatabaseMapper.toDomain(list)
        return modelsList
    }

    @LoggingDatabaseMethod(moduleName = "adminRequestsModule")
    override fun getAdminRequestById(id: AdminRequestId): AdminRequest? {
        val entity = jpaAdminRequestRepository.findById(id.value).getOrElse { return null }
        val model = AdminRequestDatabaseMapper.toDomain(entity)
        return model
    }

    @LoggingDatabaseMethod(moduleName = "adminRequestsModule")
    override fun getAdminRequestsListBySenderId(senderId: AdminRequestSenderId): List<AdminRequest> {
        val list = jpaAdminRequestRepository.findBySenderId(senderId.value)
        val modelsList = AdminRequestDatabaseMapper.toDomain(list)
        return modelsList
    }

    @LoggingDatabaseMethod(moduleName = "adminRequestsModule")
    override fun getAdminAnswerByRequestId(id: AdminRequestId): AdminAnswer? {
        val answer = jpaAdminAnswerRepository.findByAdminRequestId(id.value) ?: return null
        val model = AdminAnswerDatabaseMapper.toDomain(answer)
        return model
    }

}