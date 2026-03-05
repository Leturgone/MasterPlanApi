package api.masterplan.app.plansModule.presentation.api.controller

import api.masterplan.app.employeeModule.presentation.dto.responce.EmployeeErrorResponse
import api.masterplan.app.plansModule.application.command.*
import api.masterplan.app.plansModule.application.usecase.*
import api.masterplan.app.plansModule.presentation.api.exceptionHandler.PlanControllerExceptionHandler
import api.masterplan.app.plansModule.presentation.dto.request.*
import api.masterplan.app.plansModule.presentation.dto.response.*
import api.masterplan.app.plansModule.presentation.mapper.PlanDomainToResponseMapper
import api.masterplan.app.plansModule.presentation.mapper.PlanRequestToDomainMapper
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
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


    @Operation(
        summary = "Получение информации о плане мероприятий",
        description = "Получение информации о плане мероприятий по id плана",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Информация о плане получена",
                content = [Content(schema = Schema(implementation = PlanInformationResponse::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "План с указанным id не найден",
                content = [Content(schema = Schema(implementation = PlanErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера: сбой при получении плана",
                content = [Content(schema = Schema(implementation = PlanErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли сотрудника"
            )

        ]
    )
    @GetMapping("/emp/plans/getPlan/{planId}/")
    fun getPlanInformation(@PathVariable(value = "planId") planId: UUID): ResponseEntity<PlanInformationResponse> {
        val command = GetPlanInfCommand(
            planId = PlanRequestToDomainMapper.toPlanId(planId)
        )
        val result = getPlanInfUseCase(command).getOrThrow()
        val resp = PlanDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }


    @Operation(
        summary = "Получение информации о задаче",
        description = "Получение информации о задаче из плана мероприятий по id задачи",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Информация о задаче получена",
                content = [Content(schema = Schema(implementation = TaskInformationResponse::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Задаче с указанным id не найдена",
                content = [Content(schema = Schema(implementation = PlanErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера: сбой при получении задачи",
                content = [Content(schema = Schema(implementation = PlanErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли сотрудника"
            )

        ]
    )
    @GetMapping("/emp/tasks/getTask/{taskId}/")
    fun getTaskInformation(@PathVariable(value = "taskId") taskId: UUID): ResponseEntity<TaskInformationResponse> {
        val command = GetTaskInfCommand(
            taskId = PlanRequestToDomainMapper.toTaskId(taskId)
        )
        val result = getTaskInfUseCase(command).getOrThrow()
        val resp = PlanDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }


    @Operation(
        summary = "Получение задач из плана мероприятий",
        description = "Получение списка задач из плана мероприятий по id плана",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Список задач получен",
                content = [Content(
                    array = ArraySchema(schema = Schema(implementation = TaskInformationResponse::class)))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера: сбой при получении плана",
                content = [Content(schema = Schema(implementation = PlanErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли руководителя"
            )

        ]
    )
    @GetMapping("/dir/plans/getPlan/{planId}/tasks}")
    fun getPlanTasks(@PathVariable(value = "planId") planId: UUID): ResponseEntity<List<TaskInformationResponse>>{
        val command = GetTasksFromPlanCommand(
            planId = PlanRequestToDomainMapper.toPlanId(planId)
        )
        val result = getTasksFromPlanUseCase(command).getOrThrow()
        val resp = PlanDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }


    @Operation(
        summary = "Получение задач из плана мероприятий отфильтрованных по статуса",
        description = "Получение списка задач из плана мероприятий отфильтрованных по id плана и статусу",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Список задач получен",
                content = [Content(
                    array = ArraySchema(schema = Schema(implementation = TaskInformationResponse::class)))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Некорректные данные: неправильный статус",
                content = [Content(schema = Schema(implementation = PlanErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера: сбой при получении плана",
                content = [Content(schema = Schema(implementation = PlanErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли руководителя"
            )

        ]
    )
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



    @Operation(
        summary = "Получение задач из плана мероприятий отсортированных по времени",
        description = "Получение списка задач отсортированных по времени из плана по id плана",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Список задач получен",
                content = [Content(
                    array = ArraySchema(schema = Schema(implementation = TaskInformationResponse::class)))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Некорректные данные: неправильный статус",
                content = [Content(schema = Schema(implementation = PlanErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера: сбой при получении плана",
                content = [Content(schema = Schema(implementation = PlanErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли руководителя"
            )

        ]
    )
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

    @GetMapping("/dir/{directorId}/plans/sortTime")
    fun getDirPlansSortByTime(@PathVariable(value = "directorId") directorId: UUID): ResponseEntity<List<PlanInformationResponse>>{
        val command = SortDirPlansByEndDateCommand(
            directorId = PlanRequestToDomainMapper.toDirectorId(directorId)
        )
        val result = sortDirPlansByEndDateUseCase(command).getOrThrow()
        val resp = PlanDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }

    // Изменение задачи
    @PutMapping("/dir/tasks/updateTask/{taskId}")
    fun updateTask(@PathVariable(value = "taskId") taskId: UUID,
                   @RequestBody request: UpdateTaskRequest): ResponseEntity<TaskIdResponse>{
        val taskDomainId = PlanRequestToDomainMapper.toTaskId(taskId)
        val updatedTask = PlanRequestToDomainMapper.toTask(
            id = request.id,
            title = request.title,
            description = request.description,
            urgency = request.urgency,
            endDate = request.endDate,
            status = request.status,
            planId = request.planId,
            documentId = request.documentId,
            executorsIds = request.executorsIds,
        )
        val file = PlanRequestToDomainMapper.toTaskFile(
            fileName = request.documentName,
            fileData = request.document
        )
        val command = UpdateTaskCommand(
            taskId = taskDomainId,
            updatedTask = updatedTask,
            document = file,
        )
        val result = updateTaskUseCase(command).getOrThrow()
        val resp = PlanDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }

    // Изменение плана
    @PutMapping("/dir/plans/updatePlan/{planId}")
    fun updatePlan(@PathVariable(value = "planId") planId: UUID,
                   @RequestBody request: UpdatePlanRequest): ResponseEntity<PlanIdResponse>{
        val planDomainId = PlanRequestToDomainMapper.toPlanId(planId)
        val updatedPlan = PlanRequestToDomainMapper.toPlan(
            id = request.id,
            title = request.title,
            description = request.description,
            startDate = request.startDate,
            endDate = request.endDate,
            directorId = request.directorId,
            documentId = request.documentId,
            status = request.status,
        )
        val file = PlanRequestToDomainMapper.toPlanFile(
            fileName = request.documentName,
            fileData = request.document
        )
        val command = UpdatePlanCommand(
            planId = planDomainId,
            updatedPlan = updatedPlan,
            document = file
        )
        val result = updatePlanUseCase(command).getOrThrow()
        val resp = PlanDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }


    @DeleteMapping("/dir/plans/deletePlan/{planId}")
    fun deletePlan(@PathVariable(value = "planId") planId: UUID): ResponseEntity<PlanIdResponse>{
        val planDomainId = PlanRequestToDomainMapper.toPlanId(planId)
        val command = DeletePlanCommand(planDomainId)
        val result = deletePlanUseCase(command).getOrThrow()
        val resp = PlanDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }

    @DeleteMapping("/dir/tasks/deleteTask/{taskId}")
    fun deleteTask(@PathVariable(value = "taskId") taskId: UUID): ResponseEntity<TaskIdResponse>{
        val taskDomainId = PlanRequestToDomainMapper.toTaskId(taskId)
        val command = DeleteTaskFromPlanCommand(taskDomainId)
        val result = deleteTaskFromPlanUseCase(command).getOrThrow()
        val resp = PlanDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }

    @PatchMapping("/dir/plans/updatePlanStatus/{planId}")
    fun updatePlanStatus(@PathVariable(value = "planId") planId: UUID,
                         @RequestBody request: UpdatePlanStatusRequest): ResponseEntity<PlanIdResponse>{
        val planDomainId = PlanRequestToDomainMapper.toPlanId(planId)
        val status = PlanRequestToDomainMapper.toPlanStatus(request.status)
        val command = ChangePlanStatusCommand(
            planId = planDomainId,
            status = status,
        )
        val result = changePlanStatusUseCase(command).getOrThrow()
        val resp = PlanDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }


    @PatchMapping("/dir/tasks/updateTaskStatus/{taskId}")
    fun updateTaskStatus(@PathVariable(value = "taskId") taskId: UUID,
                         @RequestBody request: UpdateTaskStatusRequest): ResponseEntity<TaskIdResponse>{
        val taskDomainId = PlanRequestToDomainMapper.toTaskId(taskId)
        val status = PlanRequestToDomainMapper.toTaskStatus(request.status)
        val command = ChangeTaskStatusCommand(
            taskId = taskDomainId,
            status = status,
        )
        val result = changeTaskStatusUseCase(command).getOrThrow()
        val resp = PlanDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }

}