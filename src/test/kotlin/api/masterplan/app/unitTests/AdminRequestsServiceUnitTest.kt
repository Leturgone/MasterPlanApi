package api.masterplan.app.unitTests

import api.masterplan.app.adminRequestsModule.application.service.AdminRequestsServiceImpl
import api.masterplan.app.adminRequestsModule.domain.exception.AdminRequestException
import api.masterplan.app.adminRequestsModule.domain.interfaces.AdminRequestsRepository
import api.masterplan.app.adminRequestsModule.domain.model.entity.AdminAnswer
import api.masterplan.app.adminRequestsModule.domain.model.entity.AdminRequest
import api.masterplan.app.adminRequestsModule.domain.model.value.*
import io.mockk.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime
import java.util.*

class AdminRequestsServiceUnitTest {

    private val adminRequestRepository = mockk<AdminRequestsRepository>()
    private val adminRequestsService = AdminRequestsServiceImpl(adminRequestRepository)

    private val adminRequestId = AdminRequestId.generate()
    private val adminAnswerId = AdminAnswerId.generate()
    private val senderId = AdminRequestSenderId(UUID.randomUUID())
    private val title = AdminRequestTitle.validate("Test Request")
    private val answerTitle = AdminAnswerTitle.validate("Test Answer")
    private val description = AdminRequestDescription.validate("Test Description")
    private val answerDescription = AdminAnswerDescription.validate("Answer Description")
    private val status = AdminRequestStatus.IN_PROGRESS

    @Test
    fun `createAdminRequest create new admin request successfully`() {
        every { adminRequestRepository.saveAdminRequest(any()) } returns adminRequestId
        val result = adminRequestsService.createAdminRequest(
            id = adminRequestId,
            title = title,
            description = description,
            senderId = senderId
        )
        assertEquals(adminRequestId, result)
    }

    @Test
    fun `createAdminRequest throw FailedToCreateAdminRequest when save fails`() {
        val adminRequestEntity = AdminRequest.create(
            id = null,
            title = title,
            description = description,
            senderId = senderId
        )

        every { adminRequestRepository.saveAdminRequest(adminRequestEntity) } throws Exception()

        assertThrows<AdminRequestException.FailedToCreateAdminRequest> {
            adminRequestsService.createAdminRequest(
                id = null,
                title = title,
                description = description,
                senderId = senderId
            )
        }
    }

    @Test
    fun `createAdminAnswer create new admin answer successfully`() {
        every { adminRequestRepository.saveAdminAnswer(any()) } returns adminAnswerId

        val result = adminRequestsService.createAdminAnswer(
            id = adminAnswerId,
            title = answerTitle,
            description = answerDescription,
            adminRequestId = adminRequestId
        )

        assertEquals(adminAnswerId, result)
    }

    @Test
    fun `createAdminAnswer throw FailedToCreateAdminAnswer when save fails`() {
        val adminAnswerEntity = AdminAnswer.create(
            id = null,
            title = answerTitle,
            description = answerDescription,
            adminRequestId = adminRequestId
        )

        every { adminRequestRepository.saveAdminAnswer(adminAnswerEntity) } throws Exception()

        assertThrows<AdminRequestException.FailedToCreateAdminAnswer> {
            adminRequestsService.createAdminAnswer(
                id = null,
                title = answerTitle,
                description = answerDescription,
                adminRequestId = adminRequestId
            )
        }
    }

    @Test
    fun `changeAdminRequestStatus update status successfully`() {
        val oldAdminRequest = AdminRequest.create(
            id = adminRequestId,
            title = title,
            description = description,
            senderId = senderId,
            status = AdminRequestStatus.NOT_STARTED
        )
        val requestWithNewStatus = oldAdminRequest.changeStatus(status)

        every { adminRequestRepository.getAdminRequestById(adminRequestId) } returns oldAdminRequest
        every { adminRequestRepository.updateAdminRequest(adminRequestId, requestWithNewStatus) } returns requestWithNewStatus

        val result = adminRequestsService.changeAdminRequestStatus(adminRequestId, status)

        assertEquals(status, result.status)
        assertEquals(adminRequestId, result.id)
    }

    @Test
    fun `changeAdminRequestStatus throw AdminRequestNotExist when admin request does not exist`() {
        every { adminRequestRepository.getAdminRequestById(adminRequestId) } returns null

        assertThrows<AdminRequestException.AdminRequestNotExist> {
            adminRequestsService.changeAdminRequestStatus(adminRequestId, status)
        }
    }

    @Test
    fun `changeAdminRequestStatus throw FailedToChangeAdminRequestStatus when update fails`() {
        val oldAdminRequest = AdminRequest.create(
            id = adminRequestId,
            title = title,
            description = description,
            senderId = senderId,
            status = AdminRequestStatus.NOT_STARTED
        )
        val requestWithNewStatus = oldAdminRequest.changeStatus(status)

        every { adminRequestRepository.getAdminRequestById(adminRequestId) } returns oldAdminRequest
        every { adminRequestRepository.updateAdminRequest(adminRequestId, requestWithNewStatus) } throws Exception()

        assertThrows<AdminRequestException.FailedToChangeAdminRequestStatus> {
            adminRequestsService.changeAdminRequestStatus(adminRequestId, status)
        }
    }

    @Test
    fun `getAdminRequestsList return list of admin request details`() {
        val adminRequest1 = AdminRequest.create(
            id = AdminRequestId.generate(),
            title = AdminRequestTitle("Request 1"),
            description = description,
            senderId = senderId
        )
        val adminRequest2 = AdminRequest.create(
            id = AdminRequestId.generate(),
            title = AdminRequestTitle("Request 2"),
            description = description,
            senderId = senderId
        )
        val requests = listOf(adminRequest1, adminRequest2)

        every { adminRequestRepository.getAllAdminRequestsList() } returns requests

        val result = adminRequestsService.getAdminRequestsList()

        assertEquals(2, result.size)
        assertEquals("Request 1", result[0].title.value)
        assertEquals("Request 2", result[1].title.value)
    }

    @Test
    fun `getAdminRequest return admin request details when request exists`() {
        val adminRequestEntity = AdminRequest.create(
            id = adminRequestId,
            title = title,
            description = description,
            senderId = senderId
        )

        every { adminRequestRepository.getAdminRequestById(adminRequestId) } returns adminRequestEntity

        val result = adminRequestsService.getAdminRequest(adminRequestId)

        assertEquals(adminRequestId, result.id)
        assertEquals(title, result.title)
        assertEquals(description, result.description)
    }

    @Test
    fun `getAdminRequest throw AdminRequestNotExist when request does not exist`() {
        every { adminRequestRepository.getAdminRequestById(adminRequestId) } returns null

        assertThrows<AdminRequestException.AdminRequestNotExist> {
            adminRequestsService.getAdminRequest(adminRequestId)
        }
    }

    @Test
    fun `getCreatedAdminRequestsBySenderList return list of requests for sender`() {
        val adminRequest1 = AdminRequest.create(
            id = AdminRequestId.generate(),
            title = AdminRequestTitle("Sender Request 1"),
            description = description,
            senderId = senderId
        )
        val adminRequest2 = AdminRequest.create(
            id = AdminRequestId.generate(),
            title = AdminRequestTitle("Sender Request 2"),
            description = description,
            senderId = senderId
        )
        val requests = listOf(adminRequest1, adminRequest2)

        every { adminRequestRepository.getAdminRequestsListBySenderId(senderId) } returns requests

        val result = adminRequestsService.getCreatedAdminRequestsBySenderList(senderId)

        assertEquals(2, result.size)
        assertEquals("Sender Request 1", result[0].title.value)
        assertEquals("Sender Request 2", result[1].title.value)
    }

    @Test
    fun `getAdminAnswerForRequest return admin answer details when answer exists`() {
        val adminAnswer = AdminAnswer.create(
            id = adminAnswerId,
            title = answerTitle,
            description = answerDescription,
            adminRequestId = adminRequestId
        )

        every { adminRequestRepository.getAdminAnswerByRequestId(adminRequestId) } returns adminAnswer

        val result = adminRequestsService.getAdminAnswerForRequest(adminRequestId)

        assertEquals(adminAnswerId, result.id)
        assertEquals(answerTitle, result.title)
        assertEquals(answerDescription, result.description)
        assertEquals(adminRequestId, result.adminRequestId)
    }

    @Test
    fun `getAdminAnswerForRequest throw AdminAnswerNotExistForRequest when answer does not exist`() {
        every { adminRequestRepository.getAdminAnswerByRequestId(adminRequestId) } returns null

        assertThrows<AdminRequestException.AdminAnswerNotExistForRequest> {
            adminRequestsService.getAdminAnswerForRequest(adminRequestId)
        }
    }
}