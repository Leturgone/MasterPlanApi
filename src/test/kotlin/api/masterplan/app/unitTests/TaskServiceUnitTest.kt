package api.masterplan.app.unitTests

import api.masterplan.app.plansModule.application.mapper.TasksPlanToDetailsMapper
import api.masterplan.app.plansModule.application.service.TaskServiceImpl
import api.masterplan.app.plansModule.domain.dtos.TaskDetails
import api.masterplan.app.plansModule.domain.exceptions.PlanException
import api.masterplan.app.plansModule.domain.interfaces.TaskRepository
import api.masterplan.app.plansModule.domain.model.entity.Task
import api.masterplan.app.plansModule.domain.model.value.*
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate
import java.util.*

class TaskServiceUnitTest {
    private val taskRepository = mockk<TaskRepository>()
    private val taskService = TaskServiceImpl(taskRepository)

    private val taskId = TaskId(UUID.randomUUID())
    private val planId = PlanId(UUID.randomUUID())
    private val executorId = ExecutorId(UUID.randomUUID())
    private val documentId = TaskDocumentId(UUID.randomUUID())
    private val title = TaskTitle("Test Task")
    private val description = TaskDescription("Test Description")
    private val endDate = TaskDate(LocalDate.now().plusDays(7))
    private val taskStatus = TaskStatus.NOT_STARTED
    private val urgency = TaskUrgency.validate(8.0)
    private val executorsIds = mutableListOf(executorId)

    private val mockTask = Task.create(
        id = taskId,
        title = title,
        description = description,
        endDate = endDate,
        urgency = urgency,
        planId = planId,
        documentId = documentId,
        executorsId = executorsIds
    ).changeTaskStatus(taskStatus)

    private val mockTaskDetails = TaskDetails(
        id = taskId,
        title = title,
        description = description,
        endDate = endDate,
        status = taskStatus,
        planId = planId,
        documentId = documentId,
        urgency = urgency,
        executorsIds = executorsIds
    )

    @Test
    fun `getTaskById return task details when task exists`() {
        every { taskRepository.getTask(taskId) } returns mockTask
        val result = taskService.getTaskById(taskId)
        assertEquals(mockTaskDetails, result)
        verify { taskRepository.getTask(taskId) }
    }

    @Test
    fun `getTaskById throw TaskNotExist when task not found`() {
        every { taskRepository.getTask(taskId) } returns null
        assertThrows<PlanException.TaskNotExist> {
            taskService.getTaskById(taskId)
        }
    }

    @Test
    fun `getTasksByPlanId return list of task details`() {
        val tasks = listOf(mockTask)
        every { taskRepository.getTasksByPlanId(planId) } returns tasks
        val result = taskService.getTasksByPlanId(planId)
        assertEquals(listOf(mockTaskDetails), result)
    }

    @Test
    fun `getTasksByPlanId return empty list when no tasks found`() {
        every { taskRepository.getTasksByPlanId(planId) } returns emptyList()
        val result = taskService.getTasksByPlanId(planId)
        assertTrue(result.isEmpty())
    }

    @Test
    fun `createTask return task id when successful`() {
        every { taskRepository.isTaskExist(title, planId) } returns false
        every { taskRepository.saveTask(any()) } returns taskId
        val result = taskService.createTask(
            id = null,
            title = title,
            description = description,
            endDate = endDate,
            planId = planId,
            documentId = null,
            executorsId = executorsIds
        )
        assertEquals(taskId, result)
    }

    @Test
    fun `createTask throw TaskAlreadyExists when task with same title exists in plan`() {
        every { taskRepository.isTaskExist(title, planId) } returns true
        assertThrows<PlanException.TaskAlreadyExists> {
            taskService.createTask(
                id = null,
                title = title,
                description = description,
                endDate = endDate,
                planId = planId,
                documentId = null,
                executorsId = executorsIds
            )
        }
        verify { taskRepository.isTaskExist(title, planId) }
        verify(exactly = 0) { taskRepository.saveTask(any()) }
    }

    @Test
    fun `createTask throw FailedToSaveTask when repository failed`() {
        every { taskRepository.isTaskExist(title, planId) } returns false
        every { taskRepository.saveTask(any()) } returns null

        assertThrows<PlanException.FailedToSaveTask> {
            taskService.createTask(
                id = null,
                title = title,
                description = description,
                endDate = endDate,
                planId = planId,
                documentId = null,
                executorsId = executorsIds
            )
        }
    }

    @Test
    fun `deleteTask return task id when successful`() {
        every { taskRepository.deleteTask(taskId) } returns taskId
        val result = taskService.deleteTask(taskId)
        assertEquals(taskId, result)
    }

    @Test
    fun `deleteTask should throw FailedToDeleteTask when repository failed`() {
        every { taskRepository.deleteTask(taskId) } returns null
        assertThrows<PlanException.FailedToDeleteTask> {
            taskService.deleteTask(taskId)
        }
    }

    @Test
    fun `updateTask return updated task details when successful`() {
        val updatedTask = Task.create(
            id = taskId,
            title = TaskTitle("Updated Title"),
            description = description,
            endDate = endDate,
            urgency = urgency,
            planId = planId,
            documentId = documentId,
            executorsId = executorsIds
        ).changeTaskStatus(taskStatus)
        val updatedTaskDetails = mockTaskDetails.copy(title = TaskTitle("Updated Title"))

        every { taskRepository.getTask(taskId) } returns mockTask
        every { taskRepository.updateTask(taskId, any()) } returns updatedTask

        val result = taskService.updateTask(taskId, updatedTask)
        assertEquals(updatedTaskDetails, result)
    }

    @Test
    fun `updateTask recalc urgency when endDate changes`() {
        val newEndDate = TaskDate(LocalDate.now().plusDays(1))
        val updatedTask = Task.create(
            id = taskId,
            title = title,
            description = description,
            endDate = newEndDate,
            urgency = urgency,
            planId = planId,
            documentId = documentId,
            executorsId = executorsIds
        ).changeTaskStatus(taskStatus)

        every { taskRepository.getTask(taskId) } returns mockTask
        every { taskRepository.updateTask(taskId, any()) } returns mockTask

        taskService.updateTask(taskId, updatedTask)

        verify {
            taskRepository.updateTask(
                taskId,
                match { it.endDate == newEndDate && it.urgency != mockTask.urgency }
            )
        }
    }

    @Test
    fun `updateTask throw TaskNotExist when task not found`() {
        every { taskRepository.getTask(taskId) } returns null

        assertThrows<PlanException.TaskNotExist> {
            taskService.updateTask(taskId, mockTask)
        }
    }

    @Test
    fun `updateTask throw FailedToUpdateTask when repository returns failed`() {
        every { taskRepository.getTask(taskId) } returns mockTask
        every { taskRepository.updateTask(taskId, any()) } returns null
        assertThrows<PlanException.FailedToUpdateTask> {
            taskService.updateTask(taskId, mockTask)
        }
        verify { taskRepository.getTask(taskId) }
        verify { taskRepository.updateTask(taskId, any()) }
    }

    @Test
    fun `getAssignedTasks return list of task details for executor`() {
        val tasks = listOf(mockTask)
        every { taskRepository.getTasksByExecutorId(executorId) } returns tasks
        val result = taskService.getAssignedTasks(executorId)
        assertEquals(listOf(mockTaskDetails), result)
        verify { taskRepository.getTasksByExecutorId(executorId) }
    }

    @Test
    fun `getAssignedTasksForMultipleExecutors return list of task details for multiple executors`() {
        val executorIds = setOf(executorId)
        val tasks = listOf(mockTask)
        every { taskRepository.getTasksByExecutorIds(executorIds) } returns tasks

        val result = taskService.getAssignedTasksForMultipleExecutors(executorIds)
        assertEquals(listOf(mockTaskDetails), result)
    }

    @Test
    fun `sortPlansTasksByDate return sorted tasks by date`() {

        val task1 = Task.create(
            id = TaskId(UUID.randomUUID()),
            title = title,
            description = description,
            endDate = TaskDate(LocalDate.now().plusDays(1)),
            urgency = urgency,
            planId = planId,
            documentId = documentId,
            executorsId = executorsIds
        )

        val task2 = Task.create(
            id = TaskId(UUID.randomUUID()),
            title = title,
            description = description,
            endDate = TaskDate(LocalDate.now().plusDays(3)),
            urgency = urgency,
            planId = planId,
            documentId = documentId,
            executorsId = executorsIds
        )

        val task3 = Task.create(
            id = TaskId(UUID.randomUUID()),
            title = title,
            description = description,
            endDate = TaskDate(LocalDate.now().plusDays(2)),
            urgency = urgency,
            planId = planId,
            documentId = documentId,
            executorsId = executorsIds
        )


        val tasks = listOf(task1, task2, task3)

        every { taskRepository.getTasksByPlanId(planId) } returns tasks

        val result = taskService.sortPlansTasksByDate(planId)
        assertEquals(listOf(task1, task3, task2).map { TasksPlanToDetailsMapper.toTaskDetails(it) }, result)
        verify { taskRepository.getTasksByPlanId(planId) }
    }

    @Test
    fun `sortAssignedTasksByDate return sorted tasks by date`() {
        val task1 = Task.create(
            id = TaskId(UUID.randomUUID()),
            title = title,
            description = description,
            endDate = TaskDate(LocalDate.now().plusDays(1)),
            urgency = urgency,
            planId = planId,
            documentId = documentId,
            executorsId = executorsIds
        )

        val task2 = Task.create(
            id = TaskId(UUID.randomUUID()),
            title = title,
            description = description,
            endDate = TaskDate(LocalDate.now().plusDays(3)),
            urgency = urgency,
            planId = planId,
            documentId = documentId,
            executorsId = executorsIds
        )

        val task3 = Task.create(
            id = TaskId(UUID.randomUUID()),
            title = title,
            description = description,
            endDate = TaskDate(LocalDate.now().plusDays(2)),
            urgency = urgency,
            planId = planId,
            documentId = documentId,
            executorsId = executorsIds
        )
        val tasks = listOf(task1, task2, task3)

        every { taskRepository.getTasksByExecutorId(executorId) } returns tasks
        val result = taskService.sortAssignedTasksByDate(executorId)
        assertEquals(listOf(task1, task3, task2).map { TasksPlanToDetailsMapper.toTaskDetails(it) }, result)
    }

    @Test
    fun `filterAssignedTasksByStatus should return filtered assigned tasks`() {
        val completedTask = mockTask.changeTaskStatus(TaskStatus.COMPLETED)
        val notStartedTask = mockTask.changeTaskStatus(TaskStatus.NOT_STARTED)
        val tasks = listOf(completedTask, notStartedTask)

        every { taskRepository.getTasksByExecutorId(executorId) } returns tasks

        val result = taskService.filterAssignedTasksByStatus(executorId, TaskStatus.COMPLETED)
        assertEquals(1, result.size)
        assertEquals(TaskStatus.COMPLETED, result.first().status)
    }

    @Test
    fun `filterPlanTasksByStatus return filtered plan tasks`() {
        val completedTask = mockTask.changeTaskStatus(TaskStatus.COMPLETED)
        val notStartedTask = mockTask.changeTaskStatus(TaskStatus.NOT_STARTED)
        val tasks = listOf(completedTask, notStartedTask)
        every { taskRepository.getTasksByPlanId(planId) } returns tasks

        val result = taskService.filterPlanTasksByStatus(planId, TaskStatus.NOT_STARTED)
        assertEquals(1, result.size)
        assertEquals(TaskStatus.NOT_STARTED, result.first().status)
    }

    @Test
    fun `getAssignedTasks handle empty executor tasks list`() {
        every { taskRepository.getTasksByExecutorId(executorId) } returns emptyList()
        val result = taskService.getAssignedTasks(executorId)
        assertTrue(result.isEmpty())
        verify { taskRepository.getTasksByExecutorId(executorId) }
    }

    @Test
    fun `getAssignedTasksForMultipleExecutors handle empty set`() {
        val emptySet = emptySet<ExecutorId>()
        every { taskRepository.getTasksByExecutorIds(emptySet) } returns emptyList()
        val result = taskService.getAssignedTasksForMultipleExecutors(emptySet)
        assertTrue(result.isEmpty())
    }


    @Test
    fun `filterAssignedTasksByStatus return empty list when no matches`() {
        val tasks = listOf(mockTask.changeTaskStatus(TaskStatus.NOT_STARTED))
        every { taskRepository.getTasksByExecutorId(executorId) } returns tasks
        val result = taskService.filterAssignedTasksByStatus(executorId, TaskStatus.COMPLETED)
        assertTrue(result.isEmpty())
    }

    @Test
    fun `searchAssignedTasksByTitle return matching tasks`() {
        val query = "test"
        val tasks = listOf(mockTask)
        every { taskRepository.searchExecutorTasksByTitle(executorId, query) } returns tasks
        val result = taskService.searchAssignedTasksByTitle(executorId, query)
        assertEquals(listOf(mockTaskDetails), result)
    }

    @Test
    fun `searchAssignedTasksByTitle return empty list when no matches`() {
        every { taskRepository.searchExecutorTasksByTitle(executorId, "12345") } returns emptyList()
        val result = taskService.searchAssignedTasksByTitle(executorId, "12345")
        assertTrue(result.isEmpty())
    }

    @Test
    fun `assignTaskDocumentToTask should return task id when successful`() {
        val taskWithDocument = mockTask.addDocument(documentId)
        every { taskRepository.getTask(taskId) } returns mockTask
        every { taskRepository.updateTask(taskId, any()) } returns taskWithDocument
        val result = taskService.assignTaskDocumentToTask(taskId, documentId)
        assertEquals(taskId, result)
    }


    @Test
    fun `assignTaskDocumentToTask should throw TaskNotExist when task not found`() {
        every { taskRepository.getTask(taskId) } returns null

        assertThrows<PlanException.TaskNotExist> {
            taskService.assignTaskDocumentToTask(taskId, documentId)
        }
    }

    @Test
    fun `assignTaskDocumentToTask throw FailedToAssignDocumentToTask when update fails`() {
        every { taskRepository.getTask(taskId) } returns mockTask
        every { taskRepository.updateTask(taskId, any()) } returns null
        assertThrows<PlanException.FailedToAssignDocumentToTask> {
            taskService.assignTaskDocumentToTask(taskId, documentId)
        }
    }

    @Test
    fun `updateTaskStatus return task details with updated data when successful`() {
        val newStatus = TaskStatus.IN_PROGRESS
        val updatedTask = mockTask.changeTaskStatus(newStatus)
        val updatedTaskDetails = mockTaskDetails.copy(status = newStatus)

        every { taskRepository.getTask(taskId) } returns mockTask
        every { taskRepository.updateTask(taskId, any()) } returns updatedTask
        val result = taskService.updateTaskStatus(taskId, newStatus)
        assertEquals(updatedTaskDetails, result)
    }

    @Test
    fun `updateTaskStatus throw TaskNotExist when task not found`() {
        every { taskRepository.getTask(taskId) } returns null
        assertThrows<PlanException.TaskNotExist> {
            taskService.updateTaskStatus(taskId, TaskStatus.COMPLETED)
        }
    }

    @Test
    fun `updateTaskStatus throw FailedToUpdateTaskStatus when update fails`() {
        every { taskRepository.getTask(taskId) } returns mockTask
        every { taskRepository.updateTask(taskId, any()) } returns null

        assertThrows<PlanException.FailedToUpdateTaskStatus> {
            taskService.updateTaskStatus(taskId, TaskStatus.COMPLETED)
        }
    }
}