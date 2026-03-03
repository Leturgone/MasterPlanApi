package api.masterplan.app.plansModule.presentation.api.controller

import api.masterplan.app.plansModule.application.command.*
import api.masterplan.app.plansModule.application.usecase.*
import api.masterplan.app.plansModule.presentation.api.exceptionHandler.PlanControllerExceptionHandler
import api.masterplan.app.plansModule.presentation.dto.request.CreatePlanRequest
import api.masterplan.app.plansModule.presentation.dto.request.CreateTaskRequest
import api.masterplan.app.plansModule.presentation.dto.response.*
import api.masterplan.app.plansModule.presentation.mapper.PlanDomainToResponseMapper
import api.masterplan.app.plansModule.presentation.mapper.PlanRequestToDomainMapper
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@PlanControllerExceptionHandler
@RequestMapping("/api/v1")
@Tag(name = "Plans and Tasks", description = "Управление планами мероприятий")
class PlanController(
    private val addTaskToPlanUseCase: AddTaskToPlanUseCase,
    private val changePlanStatusUseCase: ChangePlanStatusUseCase,
    private val changeTaskStatusUseCase: ChangeTaskStatusUseCase,
    private val createPlanUseCase: CreatePlanUseCase,
    private val deletePlanUseCase: DeletePlanUseCase,
    private val deleteTaskFromPlanUseCase: DeleteTaskFromPlanUseCase,
    private val exportPlanUseCase: ExportPlanUseCase,
    private val filterAssignedTasksByStatusUseCase: FilterAssignedTasksByStatusUseCase,
    private val filterDirPlansByStatusUseCase: FilterDirPlansByStatusUseCase,
    private val filterPlanTasksByStatusUseCase: FilterPlanTasksByStatusUseCase,
    private val getAssignedTasksUseCase: GetAssignedTasksUseCase,
    private val getDirPlansUseCase: GetDirPlansUseCase,
    private val getPlanInfUseCase: GetPlanInfUseCase,
    private val getTaskInfUseCase: GetTaskInfUseCase,
    private val getTasksFromPlanUseCase: GetTasksFromPlanUseCase,
    private val searchAssignedTasksByTitleUseCase: SearchAssignedTasksByTitleUseCase,
    private val sortAssignedTasksByEndDateUseCase: SortAssignedTasksByEndDateUseCase,
    private val sortDirPlansByEndDateUseCase: SortDirPlansByEndDateUseCase,
    private val sortPlanTasksByEndDateUseCase: SortPlanTasksByEndDateUseCase,
    private val updatePlanUseCase: UpdatePlanUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase
) {


    // Получить информацию о плане мероприятий
    @GetMapping("/emp/plans/getPlan/{planId}/")
    fun getPlanInformation(@PathVariable(value = "planId") planId: UUID): ResponseEntity<PlanInformationResponse> {
        val command = GetPlanInfCommand(
            planId = PlanRequestToDomainMapper.toPlanId(planId)
        )
        val result = getPlanInfUseCase(command).getOrThrow()
        val resp = PlanDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }


    // Получить информацию о задаче

    @GetMapping("/emp/tasks/getTask/{taskId}/")
    fun getTaskInformation(@PathVariable(value = "taskId") taskId: UUID): ResponseEntity<TaskInformationResponse> {
        val command = GetTaskInfCommand(
            taskId = PlanRequestToDomainMapper.toTaskId(taskId)
        )
        val result = getTaskInfUseCase(command).getOrThrow()
        val resp = PlanDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }


    //Просматривать список задач из плана

    @GetMapping("/dir/plans/getPlan/{planId}/tasks}")
    fun getPlanTasks(@PathVariable(value = "planId") planId: UUID): ResponseEntity<List<TaskInformationResponse>>{
        val command = GetTasksFromPlanCommand(
            planId = PlanRequestToDomainMapper.toPlanId(planId)
        )
        val result = getTasksFromPlanUseCase(command).getOrThrow()
        val resp = PlanDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }

    // Фильтр задач по статусу
    @GetMapping("/dir/plans/{planId}/tasks/filterStatus/{status}")
    fun getPlanTasksFilterByStatus(
        @PathVariable(value = "planId") planId: UUID,
        @PathVariable(value = "status") status: String): ResponseEntity<List<TaskInformationResponse>>{
        val command = FilterPlanTasksByStatusCommand(
            planId = PlanRequestToDomainMapper.toPlanId(planId),
            taskStatus = PlanRequestToDomainMapper.toTaskStatus(status)
        )
        val result = filterPlanTasksByStatusUseCase(command).getOrThrow()
        val resp = PlanDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }

    // Фильтр задач по времени

    @GetMapping("/dir/plans/{planId}/tasks/sortTime")
    fun getPlanTasksSortByTime(@PathVariable(value = "planId") planId: UUID): ResponseEntity<List<TaskInformationResponse>>{
        val command = SortPlanTasksByEndDateCommand(
            planId = PlanRequestToDomainMapper.toPlanId(planId)
        )
        val result = sortPlanTasksByEndDateUseCase(command).getOrThrow()
        val resp = PlanDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }

    // Просматривать порученные задачи

    @GetMapping("/emp/{executorId}/assignedTasks")
    fun getAssignedTasks(@PathVariable(value = "executorId") executorId: UUID): ResponseEntity<List<TaskInformationResponse>>{
        val command = GetAssignedTasksCommand(
            executorId = PlanRequestToDomainMapper.toExecutorId(executorId)
        )
        val result = getAssignedTasksUseCase(command).getOrThrow()
        val resp = PlanDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }

    // Поиск задач по названию

    @GetMapping("/emp/{executorId}/assignedTasks/search/{query}")
    fun searchAssignedTasksByTitle(
        @PathVariable(value = "executorId") executorId: UUID,
        @PathVariable(value = "query") query: String): ResponseEntity<List<TaskInformationResponse>>{
        val command = SearchAssignedTasksByTitleCommand(
            query = query,
            executorId = PlanRequestToDomainMapper.toExecutorId(executorId)
        )
        val result = searchAssignedTasksByTitleUseCase(command).getOrThrow()
        val resp = PlanDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }

    // Фильтр порученных задач по статусу

    @GetMapping("/emp/{executorId}/assignedTasks/filterStatus/{status}")
    fun getAssignedTasksFilterByStatus(
        @PathVariable(value = "executorId") executorId: UUID,
        @PathVariable(value = "status") status: String
    ): ResponseEntity<List<TaskInformationResponse>>{
        val command = FilterAssignedTasksByStatusCommand(
            executorId = PlanRequestToDomainMapper.toExecutorId(executorId),
            taskStatus = PlanRequestToDomainMapper.toTaskStatus(status)
        )
        val result = filterAssignedTasksByStatusUseCase(command).getOrThrow()
        val resp = PlanDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }

    // Фильтр порученных задач по времени

    @GetMapping("/emp/{executorId}/assignedTasks/sortTime")
    fun getAssignedTasksSortByTime(@PathVariable(value = "executorId") executorId: UUID): ResponseEntity<List<TaskInformationResponse>>{
        val command = SortAssignedTasksByEndDateCommand(
            executorId = PlanRequestToDomainMapper.toExecutorId(executorId)
        )
        val result = sortAssignedTasksByEndDateUseCase(command).getOrThrow()
        val resp = PlanDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }

    // Экспортировать план мероприятий

    @GetMapping("/dir/plans/{planId}/export")
    fun exportPlan(@PathVariable(value = "planId") planId: UUID): ResponseEntity<ExportPlanResponse>{
        val command = ExportPlanCommand(
            planId = PlanRequestToDomainMapper.toPlanId(planId)
        )
        val result = exportPlanUseCase(command).getOrThrow()
        val resp = PlanDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }

    // Создать планы мероприятий
    @PostMapping("/dir/plans/createPlan")
    fun createPLan(@RequestBody request: CreatePlanRequest): ResponseEntity<PlanIdResponse>{
        val file = PlanRequestToDomainMapper.toPlanFile(
            fileName = request.documentName,
            fileData = request.document
        )
        val command = CreatePlanCommand(
            id = request.id?.let { PlanRequestToDomainMapper.toPlanId(it)},
            title = PlanRequestToDomainMapper.toPlanTitle(request.title),
            description = PlanRequestToDomainMapper.toPlanDescription(request.description),
            startDate = request.startDate?.let { PlanRequestToDomainMapper.toPlanDate(it) },
            endDate = PlanRequestToDomainMapper.toPlanDate(request.endDate),
            directorId = PlanRequestToDomainMapper.toDirectorId(request.directorId),
            document = file
        )
        val result = createPlanUseCase(command).getOrThrow()
        val resp = PlanDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)

    }

    // Добавить задачу в план мероприятий

    @PostMapping("/dir/tasks/createTask")
    fun addTaskToPlan(@RequestBody request: CreateTaskRequest): ResponseEntity<TaskIdResponse>{
        val file = PlanRequestToDomainMapper.toTaskFile(
            fileName = request.documentName,
            fileData = request.document
        )
        val command = AddTaskToPlanCommand(
            planId = PlanRequestToDomainMapper.toPlanId(request.planId),
            taskId = request.taskId?.let { PlanRequestToDomainMapper.toTaskId(it) },
            title = PlanRequestToDomainMapper.toTaskTitle(request.title),
            description = PlanRequestToDomainMapper.toTaskDescription(request.description),
            endDate = PlanRequestToDomainMapper.toTaskDate(request.endDate),
            document = file,
            executorsId = PlanRequestToDomainMapper.toExecutorList(request.executorsIds),
        )
        val result = addTaskToPlanUseCase(command).getOrThrow()
        val resp = PlanDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }

    // Просматривать список планов мероприятий
    @GetMapping("/dir/{directorId}/plans/myPlans")
    fun getDirPlans(@PathVariable(value = "directorId") directorId: UUID): ResponseEntity<List<PlanInformationResponse>>{
        val command = GetDirPlansCommand(PlanRequestToDomainMapper.toDirectorId(directorId))
        val result = getDirPlansUseCase(command).getOrThrow()
        val resp = PlanDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }

    // Фильтр планов по статусу

    @GetMapping("/dir/{directorId}/plans/filterStatus/{status}")
    fun getDirPlansFilterByStatus(
        @PathVariable(value = "directorId") directorId: UUID,
        @PathVariable(value = "status") status: String): ResponseEntity<List<PlanInformationResponse>>{
        val command = FilterDirPlansByStatusCommand(
            directorId = PlanRequestToDomainMapper.toDirectorId(directorId),
            status = PlanRequestToDomainMapper.toPlanStatus(status)
        )
        val result = filterDirPlansByStatusUseCase(command).getOrThrow()
        val resp = PlanDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }

    // Фильтр планов по времени

    fun getDirPlansSortByTime(@PathVariable(value = "directorId") directorId: UUID): ResponseEntity<List<PlanInformationResponse>>{}

    // Изменение задачи

    fun updateTask(): ResponseEntity<TaskIdResponse>{}

    // Изменение плана

    fun updatePlan(): ResponseEntity<PlanIdResponse>{}

// УДаление плана
fun deletePlan(): ResponseEntity<PlanIdResponse>{}

    // УДаление задач из плана
    fun deleteTask(): ResponseEntity<TaskIdResponse>{}

// Изменение статуса плана
fun updatePlanStatus(): ResponseEntity<PlanIdResponse>{}

    // Изменение статуса задачи
    fun updateTaskStatus(): ResponseEntity<TaskIdResponse>{}

}