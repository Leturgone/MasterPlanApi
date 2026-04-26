package api.masterplan.app.plansModule.presentation.api.controller

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
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
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
    @GetMapping("/plans/emp/plan/{planId}/")
    fun getPlanInformation(
        @PathVariable(value = "planId") planId: UUID
    ): ResponseEntity<PlanInformationResponse> {
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
    @GetMapping("/tasks/emp/task/{taskId}/")
    fun getTaskInformation(
        @PathVariable(value = "taskId") taskId: UUID
    ): ResponseEntity<TaskInformationResponse> {
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
                description = "Внутренняя ошибка сервера: сбой при получении",
                content = [Content(schema = Schema(implementation = PlanErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли руководителя"
            )

        ]
    )
    @GetMapping("/plans/emp/plan/{planId}/tasks")
    fun getPlanTasks(
        @PathVariable(value = "planId") planId: UUID
    ): ResponseEntity<List<TaskInformationResponse>>{
        val command = GetTasksFromPlanCommand(
            planId = PlanRequestToDomainMapper.toPlanId(planId)
        )
        val result = getTasksFromPlanUseCase(command).getOrThrow()
        val resp = PlanDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }


    @Operation(
        summary = "Получение задач из плана мероприятий отфильтрованных по статусу",
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
                description = "Внутренняя ошибка сервера: сбой при получении",
                content = [Content(schema = Schema(implementation = PlanErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли руководителя"
            )

        ]
    )
    @GetMapping("/plans/emp/plan/{planId}/tasks/status/{status}")
    fun getPlanTasksFilterByStatus(
        @PathVariable(value = "planId") planId: UUID,
        @PathVariable(value = "status") status: String
    ): ResponseEntity<List<TaskInformationResponse>>{
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
                responseCode = "500",
                description = "Внутренняя ошибка сервера: сбой при получении",
                content = [Content(schema = Schema(implementation = PlanErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли руководителя"
            )

        ]
    )
    @GetMapping("/plans/emp/plan/{planId}/tasks/sortTime")
    fun getPlanTasksSortByTime(
        @PathVariable(value = "planId") planId: UUID
    ): ResponseEntity<List<TaskInformationResponse>>{
        val command = SortPlanTasksByEndDateCommand(
            planId = PlanRequestToDomainMapper.toPlanId(planId)
        )
        val result = sortPlanTasksByEndDateUseCase(command).getOrThrow()
        val resp = PlanDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }

    @Operation(
        summary = "Получение порученных исполнителю задач",
        description = "Получение списка порученных исполнителю задач по id исполнителя",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Список задач получен",
                content = [Content(
                    array = ArraySchema(schema = Schema(implementation = TaskInformationResponse::class)))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера: сбой при получении",
                content = [Content(schema = Schema(implementation = PlanErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли сотрудника"
            )

        ]
    )
    @GetMapping("/tasks/emp/{executorId}/assignedTasks")
    fun getAssignedTasks(
        @PathVariable(value = "executorId") executorId: UUID
    ): ResponseEntity<List<TaskInformationResponse>>{
        val command = GetAssignedTasksCommand(
            executorId = PlanRequestToDomainMapper.toExecutorId(executorId)
        )
        val result = getAssignedTasksUseCase(command).getOrThrow()
        val resp = PlanDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }


    @Operation(
        summary = "Поиск по списку порученных задач",
        description = "Поиск по списку порученных исполнителю задач по id исполнителя",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Список задач получен",
                content = [Content(
                    array = ArraySchema(schema = Schema(implementation = TaskInformationResponse::class)))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера: сбой при получении ",
                content = [Content(schema = Schema(implementation = PlanErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли сотрудника"
            )

        ]
    )
    @GetMapping("/tasks/emp/{executorId}/assignedTasks/search/{query}")
    fun searchAssignedTasksByTitle(
        @PathVariable(value = "executorId") executorId: UUID,
        @PathVariable(value = "query") query: String
    ): ResponseEntity<List<TaskInformationResponse>>{
        val command = SearchAssignedTasksByTitleCommand(
            query = query,
            executorId = PlanRequestToDomainMapper.toExecutorId(executorId)
        )
        val result = searchAssignedTasksByTitleUseCase(command).getOrThrow()
        val resp = PlanDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }


    @Operation(
        summary = "Получение порученных задач отфильтрованных по статусу",
        description = "Получение списка порученных исполнителю задач отфильтрованных по id исполнителя и статусу",
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
                description = "Внутренняя ошибка сервера: сбой при получении",
                content = [Content(schema = Schema(implementation = PlanErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли сотрудника"
            )

        ]
    )
    @GetMapping("/tasks/emp/{executorId}/assignedTasks/status/{status}")
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


    @Operation(
        summary = "Получение порученных задач отсортированных по времени",
        description = "Получение списка порученных исполнителю задач отсортированных по времени по id исполнителя",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Список задач получен",
                content = [Content(
                    array = ArraySchema(schema = Schema(implementation = TaskInformationResponse::class)))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера: сбой при получении",
                content = [Content(schema = Schema(implementation = PlanErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли сотрудника"
            )

        ]
    )
    @GetMapping("/tasks/emp/{executorId}/assignedTasks/sortTime")
    fun getAssignedTasksSortByTime(
        @PathVariable(value = "executorId") executorId: UUID
    ): ResponseEntity<List<TaskInformationResponse>>{
        val command = SortAssignedTasksByEndDateCommand(
            executorId = PlanRequestToDomainMapper.toExecutorId(executorId)
        )
        val result = sortAssignedTasksByEndDateUseCase(command).getOrThrow()
        val resp = PlanDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }


    @Operation(
        summary = "Получение файла с экспортированным планом",
        description = "Получение экспортированного плана по id плана",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Экспортированный план получен"
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера: сбой при получении",
                content = [Content(schema = Schema(implementation = PlanErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "План не найден",
                content = [Content(schema = Schema(implementation = PlanErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли сотрудника"
            )

        ]
    )
    @GetMapping("/plans/dir/plan/{planId}/export")
    fun exportPlan(
        @PathVariable(value = "planId") planId: UUID
    ): ResponseEntity<ByteArray>{
        val command = ExportPlanCommand(
            planId = PlanRequestToDomainMapper.toPlanId(planId)
        )
        val result = exportPlanUseCase(command).getOrThrow()
        val resp = PlanDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok().headers(resp.fileHeaders).body(resp.fileData)
    }


    @Operation(
        summary = "Создание плана мероприятий",
        description = "Создание плана мероприятий со всеми данными",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "План создан",
                content = [Content(schema = Schema(implementation = PlanIdResponse::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера: сбой при создании",
                content = [Content(schema = Schema(implementation = PlanErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Некорректные данные для плана",
                content = [Content(schema = Schema(implementation = PlanErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "409",
                description = "План уже существует",
                content = [Content(schema = Schema(implementation = PlanErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли руководителя"
            )

        ]
    )
    @PostMapping("/plans/dir/plan",consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun createPLan(
        @RequestPart("request") request: CreatePlanRequest,
        @RequestPart(value = "file", required = false) file: MultipartFile?
    ): ResponseEntity<PlanIdResponse>{
        val fileByteArray = file?.bytes
        val fileName = file?.originalFilename
        val file = PlanRequestToDomainMapper.toPlanFile(
            fileName = fileName,
            fileData = fileByteArray
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



    @Operation(
        summary = "Добавление задачи в план мероприятий",
        description = "Создание задачи путем добавления в план мероприятий",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Задача создана и добавлена",
                content = [Content(schema = Schema(implementation = TaskIdResponse::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера: сбой при создании",
                content = [Content(schema = Schema(implementation = PlanErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Некорректные данные для задачи",
                content = [Content(schema = Schema(implementation = PlanErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "409",
                description = "Задача уже существует",
                content = [Content(schema = Schema(implementation = PlanErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "План не найден",
                content = [Content(schema = Schema(implementation = PlanErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли руководителя"
            )

        ]
    )
    @PostMapping("/tasks/dir/task",consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun addTaskToPlan(
        @RequestPart("request") request: CreateTaskRequest,
        @RequestPart(value = "file", required = false) file: MultipartFile?
    ): ResponseEntity<TaskIdResponse>{
        val fileByteArray = file?.bytes
        val fileName = file?.originalFilename
        val file = PlanRequestToDomainMapper.toTaskFile(
            fileName = fileName,
            fileData = fileByteArray
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


    @Operation(
        summary = "Получение списка созданных руководителем планов",
        description = "Получение списка созданных руководителем планов по id руководителя",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Список планов получен",
                content = [Content(
                    array = ArraySchema(schema = Schema(implementation = PlanInformationResponse::class)))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера: сбой при получении",
                content = [Content(schema = Schema(implementation = PlanErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли руководителя"
            )

        ]
    )
    @GetMapping("/plans/dir/{directorId}/plans")
    fun getDirPlans(
        @PathVariable(value = "directorId") directorId: UUID
    ): ResponseEntity<List<PlanInformationResponse>>{
        val command = GetDirPlansCommand(PlanRequestToDomainMapper.toDirectorId(directorId))
        val result = getDirPlansUseCase(command).getOrThrow()
        val resp = PlanDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }



    @Operation(
        summary = "Получение списка созданных руководителем планов с фильтрацией по статуса",
        description = "Получение списка созданных руководителем планов по id руководителя с фильтрацией по статусу",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Список планов получен",
                content = [Content(
                    array = ArraySchema(schema = Schema(implementation = PlanInformationResponse::class)))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера: сбой при получении",
                content = [Content(schema = Schema(implementation = PlanErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Некорректные данные: неправильный статус",
                content = [Content(schema = Schema(implementation = PlanErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли руководителя"
            )

        ]
    )
    @GetMapping("/plans/dir/{directorId}/plans/status/{status}")
    fun getDirPlansFilterByStatus(
        @PathVariable(value = "directorId") directorId: UUID,
        @PathVariable(value = "status") status: String
    ): ResponseEntity<List<PlanInformationResponse>>{
        val command = FilterDirPlansByStatusCommand(
            directorId = PlanRequestToDomainMapper.toDirectorId(directorId),
            status = PlanRequestToDomainMapper.toPlanStatus(status)
        )
        val result = filterDirPlansByStatusUseCase(command).getOrThrow()
        val resp = PlanDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }


    @Operation(
        summary = "Получение списка созданных руководителем планов с сортировкой по времени",
        description = "Получение списка созданных руководителем планов по id руководителя с сортировкой по времени",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Список планов получен",
                content = [Content(
                    array = ArraySchema(schema = Schema(implementation = PlanInformationResponse::class)))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера: сбой при получении",
                content = [Content(schema = Schema(implementation = PlanErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли руководителя"
            )

        ]
    )
    @GetMapping("/plans/dir/{directorId}/plans/sortTime")
    fun getDirPlansSortByTime(
        @PathVariable(value = "directorId") directorId: UUID
    ): ResponseEntity<List<PlanInformationResponse>>{
        val command = SortDirPlansByEndDateCommand(
            directorId = PlanRequestToDomainMapper.toDirectorId(directorId)
        )
        val result = sortDirPlansByEndDateUseCase(command).getOrThrow()
        val resp = PlanDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }


    @Operation(
        summary = "Обновление задачи",
        description = "Обновление задачи по id задачи",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Задача обновлена",
                content = [Content(
                    array = ArraySchema(schema = Schema(implementation = TaskIdResponse::class)))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Некорректные данные: неправильные данные для обновления",
                content = [Content(schema = Schema(implementation = PlanErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Задача с указанным id не найдена",
                content = [Content(schema = Schema(implementation = PlanErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера: сбой при обновлении",
                content = [Content(schema = Schema(implementation = PlanErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли руководителя"
            )

        ]
    )
    @PutMapping("/tasks/dir/task/{taskId}",consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun updateTask(
        @PathVariable(value = "taskId") taskId: UUID,
        @RequestPart("request") request: UpdateTaskRequest,
        @RequestPart(value = "file", required = false) file: MultipartFile?
    ): ResponseEntity<TaskIdResponse>{
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
        val fileBytes = file?.bytes
        val fileName = file?.originalFilename
        val file = PlanRequestToDomainMapper.toTaskFile(
            fileName = fileName,
            fileData = fileBytes
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


    @Operation(
        summary = "Обновление плана",
        description = "Обновление плана по id плана",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "План обновлен",
                content = [Content(
                    array = ArraySchema(schema = Schema(implementation = PlanIdResponse::class)))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Некорректные данные: неправильные данные для обновления",
                content = [Content(schema = Schema(implementation = PlanErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "План с указанным id не найден",
                content = [Content(schema = Schema(implementation = PlanErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера: сбой при обновлении",
                content = [Content(schema = Schema(implementation = PlanErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли руководителя"
            )

        ]
    )
    @PutMapping("/plans/dir/plan/{planId}",consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun updatePlan(
        @PathVariable(value = "planId") planId: UUID,
        @RequestPart("request") request: UpdatePlanRequest,
        @RequestPart(value = "file", required = false) file: MultipartFile?
    ): ResponseEntity<PlanIdResponse>{
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
        val fileBytes = file?.bytes
        val fileName = file?.originalFilename
        val file = PlanRequestToDomainMapper.toPlanFile(
            fileName = fileName,
            fileData = fileBytes
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


    @Operation(
        summary = "Удаление плана",
        description = "Удаление плана по id плана",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "План удален",
                content = [Content(
                    array = ArraySchema(schema = Schema(implementation = PlanIdResponse::class)))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "План с указанным id не найден",
                content = [Content(schema = Schema(implementation = PlanErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера: сбой при удалении",
                content = [Content(schema = Schema(implementation = PlanErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли руководителя"
            )

        ]
    )
    @DeleteMapping("/plans/dir/plan/{planId}")
    fun deletePlan(
        @PathVariable(value = "planId") planId: UUID
    ): ResponseEntity<PlanIdResponse>{
        val planDomainId = PlanRequestToDomainMapper.toPlanId(planId)
        val command = DeletePlanCommand(planDomainId)
        val result = deletePlanUseCase(command).getOrThrow()
        val resp = PlanDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }


    @Operation(
        summary = "Удаление плана",
        description = "Удаление плана по id плана",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Задача удалена",
                content = [Content(
                    array = ArraySchema(schema = Schema(implementation = TaskIdResponse::class)))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Задача с указанным id не найдена",
                content = [Content(schema = Schema(implementation = PlanErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера: сбой при удалении",
                content = [Content(schema = Schema(implementation = PlanErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли руководителя"
            )

        ]
    )
    @DeleteMapping("/tasks/dir/task/{taskId}")
    fun deleteTask(
        @PathVariable(value = "taskId") taskId: UUID
    ): ResponseEntity<TaskIdResponse>{
        val taskDomainId = PlanRequestToDomainMapper.toTaskId(taskId)
        val command = DeleteTaskFromPlanCommand(taskDomainId)
        val result = deleteTaskFromPlanUseCase(command).getOrThrow()
        val resp = PlanDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }


    @Operation(
        summary = "Обновление статуса плана",
        description = "Обновление статуса плана по id плана",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Статус плана обновлен",
                content = [Content(
                    array = ArraySchema(schema = Schema(implementation = PlanIdResponse::class)))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Некорректные данные: неправильный статус для обновления",
                content = [Content(schema = Schema(implementation = PlanErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "План с указанным id не найден",
                content = [Content(schema = Schema(implementation = PlanErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера: сбой при обновлении",
                content = [Content(schema = Schema(implementation = PlanErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли руководителя"
            )

        ]
    )
    @PatchMapping("/plans/dir/plan/{planId}/status")
    fun updatePlanStatus(
        @PathVariable(value = "planId") planId: UUID,
        @RequestBody request: UpdatePlanStatusRequest
    ): ResponseEntity<PlanIdResponse>{
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


    @Operation(
        summary = "Обновление статуса задачи",
        description = "Обновление статуса плана по id плана",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Статус задачи обновлен",
                content = [Content(
                    array = ArraySchema(schema = Schema(implementation = PlanIdResponse::class)))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Некорректные данные: неправильный статус для обновления",
                content = [Content(schema = Schema(implementation = PlanErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Задача с указанным id не найдена",
                content = [Content(schema = Schema(implementation = PlanErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера: сбой при обновлении",
                content = [Content(schema = Schema(implementation = PlanErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли руководителя"
            )

        ]
    )
    @PatchMapping("/tasks/emp/task/{taskId}/status")
    fun updateTaskStatus(
        @PathVariable(value = "taskId") taskId: UUID,
        @RequestBody request: UpdateTaskStatusRequest
    ): ResponseEntity<TaskIdResponse>{
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