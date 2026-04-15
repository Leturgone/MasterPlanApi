package api.masterplan.app.unitTests

import api.masterplan.app.plansModule.application.service.PlanServiceImpl
import api.masterplan.app.plansModule.domain.exceptions.PlanException
import api.masterplan.app.plansModule.domain.interfaces.PlanRepository
import api.masterplan.app.plansModule.domain.model.entity.Plan
import api.masterplan.app.plansModule.domain.model.value.*
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate
import java.util.*

class PlanServiceTest {

    private val planRepository = mockk<PlanRepository>()
    private val planService = PlanServiceImpl(planRepository)

    // Дефоолтные данные для моков
    private val planId = PlanId(UUID.randomUUID())
    private val directorId = PlanDirectorId(UUID.randomUUID())
    private val documentId = PlanDocumentId(UUID.randomUUID())
    private val title = PlanTitle("Test Plan")
    private val description = PlanDescription("Test Description")
    private val endDate = PlanDate(LocalDate.now().plusDays(7))

    @Test
    fun `getPlanById should return plan details when plan exists`() {

        val planEntity = Plan.create(
            id = planId,
            title = title,
            description = description,
            endDate = endDate,
            directorId = directorId
        )

        every { planRepository.getPlan(planId) } returns planEntity

        val result = planService.getPlanById(planId)

        assertEquals(planId, result.id)
        assertEquals(title, result.title)
        assertEquals(description, result.description)
    }

    @Test
    fun `getPlanById should throw exception when plan does not exist`() {

        every { planRepository.getPlan(planId) } returns null

        assertThrows<PlanException.PlanNotExist> {
            planService.getPlanById(planId)
        }
    }

    @Test
    fun `createPlan should create new plan successfully`() {


        every { planRepository.isPlanExist(title, directorId) } returns false
        every { planRepository.savePlan(any()) } returns planId


        val result = planService.createPlan(
            id = null,
            title = title,
            description = description,
            startDate = null,
            endDate = endDate,
            directorId = directorId,
            documentId = null
        )

        assertEquals(planId, result)
    }

    @Test
    fun `createPlan should throw exception when plan already exists`() {

        every { planRepository.isPlanExist(title, directorId) } returns true

        assertThrows<PlanException.PlanAlreadyExists> {
            planService.createPlan(
                id = null,
                title = title,
                description = description,
                startDate = null,
                endDate = endDate,
                directorId = directorId,
                documentId = null
            )
        }
    }

    @Test
    fun `createPlan should throw exception when save fails`() {

        every { planRepository.isPlanExist(title, directorId) } returns false
        every { planRepository.savePlan(any()) } returns null


        assertThrows<PlanException.FailedToCreatePlan> {
            planService.createPlan(
                id = null,
                title = title,
                description = description,
                startDate = null,
                endDate = endDate,
                directorId = directorId,
                documentId = null
            )
        }
    }

    @Test
    fun `getAllDirPlans should return list of plan details`() {

        val plan1 = Plan.create(
            id = PlanId(UUID.randomUUID()),
            title = PlanTitle("Plan 1"),
            description = description,
            endDate = endDate,
            directorId = directorId
        )
        val plan2 = Plan.create(
            id = PlanId(UUID.randomUUID()),
            title = PlanTitle("Plan 2"),
            description = description,
            endDate = endDate,
            directorId = directorId
        )
        val plans = listOf(plan1, plan2)

        every { planRepository.getDirPlans(directorId) } returns plans

        val result = planService.getAllDirPlans(directorId)

        assertEquals(2, result.size)
        assertEquals("Plan 1", result[0].title.value)
        assertEquals("Plan 2", result[1].title.value)
    }

    @Test
    fun `updatePlan should update plan successfully`() {

        val updatedPlan = Plan.create(
            id = planId,
            title = PlanTitle("Updated Plan"),
            description = PlanDescription("Updated Description"),
            endDate = endDate,
            directorId = directorId
        )

        every { planRepository.getPlan(planId) } returns Plan.create(
            id = planId,
            title = title,
            description = description,
            endDate = endDate,
            directorId = directorId
        )
        every { planRepository.updatePlan(planId, updatedPlan) } returns planId

        val result = planService.updatePlan(planId, updatedPlan)


        assertEquals(planId, result)
    }

    @Test
    fun `updatePlan should throw exception when plan does not exist`() {

        val updatedPlan = Plan.create(
            id = planId,
            title = title,
            description = description,
            endDate = endDate,
            directorId = directorId
        )

        every { planRepository.getPlan(planId) } returns null

        assertThrows<PlanException.PlanNotExist> {
            planService.updatePlan(planId, updatedPlan)
        }
    }

    @Test
    fun `deletePlan should delete plan successfully`() {
        every { planRepository.deletePlan(planId) } returns planId

        val result = planService.deletePlan(planId)

        assertEquals(planId, result)
    }

    @Test
    fun `deletePlan should throw exception when delete fails`() {
        every { planRepository.deletePlan(planId) } returns null

        assertThrows<PlanException.FailedToDeletePlan> {
            planService.deletePlan(planId)
        }
    }

    @Test
    fun `sortDirPlansByDate should return sorted plans`() {
        // Given
        val earlyDate = PlanDate(LocalDate.now().plusDays(1))
        val laterDate = PlanDate(LocalDate.now().plusDays(10))

        val plan1 = Plan.create(
            id = PlanId(UUID.randomUUID()),
            title = PlanTitle("Plan 1"),
            description = description,
            endDate = laterDate,
            directorId = directorId
        )
        val plan2 = Plan.create(
            id = PlanId(UUID.randomUUID()),
            title = PlanTitle("Plan 2"),
            description = description,
            endDate = earlyDate,
            directorId = directorId
        )
        val plans = listOf(plan1, plan2)

        every { planRepository.getDirPlans(directorId) } returns plans

        // When
        val result = planService.sortDirPlansByDate(directorId)

        // Then
        assertEquals(2, result.size)
        assertEquals("Plan 2", result[0].title.value) // Should be first (earlier date)
        assertEquals("Plan 1", result[1].title.value) // Should be second (later date)
    }

    @Test
    fun `filterDirPlansByStatus should return filtered plans`() {
        // Given
        val plan1 = Plan.create(
            id = PlanId(UUID.randomUUID()),
            title = PlanTitle("Plan 1"),
            description = description,
            endDate = endDate,
            directorId = directorId
        )
        val plan2 = Plan.create(
            id = PlanId(UUID.randomUUID()),
            title = PlanTitle("Plan 2"),
            description = description,
            endDate = endDate,
            directorId = directorId
        ).changePlanStatus(PlanStatus.IN_PROGRESS)

        val plans = listOf(plan1, plan2)

        every { planRepository.getDirPlans(directorId) } returns plans

        val result = planService.filterDirPlansByStatus(directorId, PlanStatus.NOT_STARTED)

        assertEquals(1, result.size)
        assertEquals("Plan 1", result[0].title.value)
        assertEquals(PlanStatus.NOT_STARTED, result[0].status)
    }

    @Test
    fun `assignPlanDocumentToPlan should assign document successfully`() {
        val plan = Plan.create(
            id = planId,
            title = title,
            description = description,
            endDate = endDate,
            directorId = directorId
        )

        every { planRepository.getPlan(planId) } returns plan
        every { planRepository.updatePlan(planId, any()) } returns planId

        val result = planService.assignPlanDocumentToPlan(planId, documentId)

        assertEquals(planId, result)
    }

    @Test
    fun `updatePlanStatus should update status successfully`() {
        val plan = Plan.create(
            id = planId,
            title = title,
            description = description,
            endDate = endDate,
            directorId = directorId
        )
        val newStatus = PlanStatus.COMPLETED

        every { planRepository.getPlan(planId) } returns plan
        every { planRepository.updatePlan(planId, any()) } returns planId


        val result = planService.updatePlanStatus(planId, newStatus)

        assertEquals(planId, result)
    }

    @Test
    fun `updatePlanStatus throw exception when plan does not exist`() {

        every { planRepository.getPlan(planId) } returns null

        assertThrows<PlanException.PlanNotExist> {
            planService.updatePlanStatus(planId, PlanStatus.COMPLETED)
        }
    }
}