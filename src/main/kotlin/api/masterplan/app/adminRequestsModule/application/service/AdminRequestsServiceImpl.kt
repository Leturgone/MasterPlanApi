package api.masterplan.app.adminRequestsModule.application.service

import api.masterplan.app.adminRequestsModule.application.mapper.AdminRequestToDetailsMapper
import api.masterplan.app.adminRequestsModule.domain.dtos.AdminAnswerDetails
import api.masterplan.app.adminRequestsModule.domain.dtos.AdminRequestDetails
import api.masterplan.app.adminRequestsModule.domain.exception.AdminRequestException
import api.masterplan.app.adminRequestsModule.domain.interfaces.AdminRequestsRepository
import api.masterplan.app.adminRequestsModule.domain.interfaces.AdminRequestsService
import api.masterplan.app.adminRequestsModule.domain.model.entity.AdminAnswer
import api.masterplan.app.adminRequestsModule.domain.model.entity.AdminRequest
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminAnswerDescription
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminAnswerId
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminAnswerTitle
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestDescription
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestId
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestSenderId
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestStatus
import api.masterplan.app.adminRequestsModule.domain.model.value.AdminRequestTitle
import api.masterplan.app.logging.annotations.LoggingMethod
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminRequestsServiceImpl(
    private val adminRequestRepository: AdminRequestsRepository
): AdminRequestsService {

    @LoggingMethod("adminRequestsModule")
    @Transactional(rollbackFor = [Exception::class])
    override fun createAdminRequest(id: AdminRequestId?, title: AdminRequestTitle,
                                    description: AdminRequestDescription, senderId: AdminRequestSenderId): AdminRequestId {

        val adminRequestEntity = AdminRequest.create(
            id = id,
            title = title,
            description = description,
            senderId = senderId,
        )

        val adminRequestId = try {
            adminRequestRepository.saveAdminRequest(adminRequestEntity)
        }catch (_: Exception){
            throw AdminRequestException.FailedToCreateAdminRequest(title)
        }

        return adminRequestId
    }

    @LoggingMethod("adminRequestsModule")
    @Transactional(rollbackFor = [Exception::class])
    override fun createAdminAnswer(id: AdminAnswerId?, title: AdminAnswerTitle,
        description: AdminAnswerDescription, adminRequestId: AdminRequestId): AdminAnswerId {

        val adminAnswerEntity = AdminAnswer.create(
            id = id,
            title = title,
            description = description,
            adminRequestId = adminRequestId
        )

        val adminAnswerId = try {
            adminRequestRepository.saveAdminAnswer(adminAnswerEntity)
        } catch (_: Exception){
            throw AdminRequestException.FailedToCreateAdminAnswer(title)
        }

        return adminAnswerId
    }


    @LoggingMethod("adminRequestsModule")
    @Transactional(rollbackFor = [Exception::class])
    override fun changeAdminRequestStatus(id: AdminRequestId, status: AdminRequestStatus): AdminRequestDetails {
        val oldAdminRequest = adminRequestRepository.getAdminRequestById(id) ?: throw AdminRequestException.AdminRequestNotExist(id)

        val requestWithNewStatus = oldAdminRequest.changeStatus(status)

        val adminRequest = try {
            adminRequestRepository.updateAdminRequest(id,requestWithNewStatus)
        }catch (_: Exception){
            throw AdminRequestException.FailedToChangeAdminRequestStatus(id, status)
        }


        return AdminRequestToDetailsMapper.toDetails(adminRequest)
    }


    @LoggingMethod("adminRequestsModule")
    override fun getAdminRequestsList(): List<AdminRequestDetails> {
        val list = adminRequestRepository.getAllAdminRequestsList()

        return list.map { AdminRequestToDetailsMapper.toDetails(it) }
    }


    @LoggingMethod("adminRequestsModule")
    override fun getAdminRequest(id: AdminRequestId): AdminRequestDetails {
        val adminRequestEntity = adminRequestRepository.getAdminRequestById(id) ?: throw AdminRequestException.AdminRequestNotExist(id)

        return AdminRequestToDetailsMapper.toDetails(adminRequestEntity)
    }


    @LoggingMethod("adminRequestsModule")
    override fun getCreatedAdminRequestsBySenderList(senderId: AdminRequestSenderId): List<AdminRequestDetails> {
        val list = adminRequestRepository.getAdminRequestsListBySenderId(senderId)

        return list.map { AdminRequestToDetailsMapper.toDetails(it) }
    }


    @LoggingMethod("adminRequestsModule")
    override fun getAdminAnswerForRequest(id: AdminRequestId): AdminAnswerDetails {
        val answer = adminRequestRepository.getAdminAnswerByRequestId(id) ?: throw AdminRequestException.AdminAnswerNotExistForRequest(id)

        return AdminRequestToDetailsMapper.toDetails(answer)
    }

}