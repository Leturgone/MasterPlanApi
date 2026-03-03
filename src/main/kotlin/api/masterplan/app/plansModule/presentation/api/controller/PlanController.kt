package api.masterplan.app.plansModule.presentation.api.controller

import api.masterplan.app.plansModule.application.command.ExportPlanCommand
import api.masterplan.app.plansModule.application.command.FilterAssignedTasksByStatusCommand
import api.masterplan.app.plansModule.application.command.FilterPlanTasksByStatusCommand
import api.masterplan.app.plansModule.application.command.GetAssignedTasksCommand
import api.masterplan.app.plansModule.application.command.GetPlanInfCommand
import api.masterplan.app.plansModule.application.command.GetTaskInfCommand
import api.masterplan.app.plansModule.application.command.GetTasksFromPlanCommand
import api.masterplan.app.plansModule.application.command.SearchAssignedTasksByTitleCommand
import api.masterplan.app.plansModule.application.command.SortAssignedTasksByEndDateCommand
import api.masterplan.app.plansModule.application.command.SortPlanTasksByEndDateCommand
import api.masterplan.app.plansModule.application.usecase.AddTaskToPlanUseCase
import api.masterplan.app.plansModule.application.usecase.ChangePlanStatusUseCase
import api.masterplan.app.plansModule.application.usecase.ChangeTaskStatusUseCase
import api.masterplan.app.plansModule.application.usecase.CreatePlanUseCase
import api.masterplan.app.plansModule.application.usecase.DeletePlanUseCase
import api.masterplan.app.plansModule.application.usecase.DeleteTaskFromPlanUseCase
import api.masterplan.app.plansModule.application.usecase.ExportPlanUseCase
import api.masterplan.app.plansModule.application.usecase.FilterAssignedTasksByStatusUseCase
import api.masterplan.app.plansModule.application.usecase.FilterDirPlansByStatusUseCase
import api.masterplan.app.plansModule.application.usecase.FilterPlanTasksByStatusUseCase
import api.masterplan.app.plansModule.application.usecase.GetAssignedTasksUseCase
import api.masterplan.app.plansModule.application.usecase.GetDirPlansUseCase
import api.masterplan.app.plansModule.application.usecase.GetPlanInfUseCase
import api.masterplan.app.plansModule.application.usecase.GetTaskInfUseCase
import api.masterplan.app.plansModule.application.usecase.GetTasksFromPlanUseCase
import api.masterplan.app.plansModule.application.usecase.SearchAssignedTasksByTitleUseCase
import api.masterplan.app.plansModule.application.usecase.SortAssignedTasksByEndDateUseCase
import api.masterplan.app.plansModule.application.usecase.SortDirPlansByEndDateUseCase
import api.masterplan.app.plansModule.application.usecase.SortPlanTasksByEndDateUseCase
import api.masterplan.app.plansModule.application.usecase.UpdatePlanUseCase
import api.masterplan.app.plansModule.application.usecase.UpdateTaskUseCase
import api.masterplan.app.plansModule.presentation.api.exceptionHandler.PlanControllerExceptionHandler
import api.masterplan.app.plansModule.presentation.dto.response.ExportPlanResponse
import api.masterplan.app.plansModule.presentation.dto.response.PlanIdResponse
import api.masterplan.app.plansModule.presentation.dto.response.PlanInformationResponse
import api.masterplan.app.plansModule.presentation.dto.response.TaskIdResponse
import api.masterplan.app.plansModule.presentation.dto.response.TaskInformationResponse
import api.masterplan.app.plansModule.presentation.mapper.PlanDomainToResponseMapper
import api.masterplan.app.plansModule.presentation.mapper.RequestToDomainMapper
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

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
    @GetMapping("/emp/plans/{planId}/")
    fun getPlanInformation(@PathVariable(value = "planId") planId: UUID): ResponseEntity<PlanInformationResponse> {
        val command = GetPlanInfCommand(
            planId = RequestToDomainMapper.toPlanId(planId)
        )
        val result = getPlanInfUseCase(command).getOrThrow()
        val resp = PlanDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }


    // Получить информацию о задаче

    @GetMapping("/emp/tasks/getTask/{taskId}/")
    fun getTaskInformation(@PathVariable(value = "taskId") taskId: UUID): ResponseEntity<TaskInformationResponse> {
        val command = GetTaskInfCommand(
            taskId = RequestToDomainMapper.toTaskId(taskId)
        )
        val result = getTaskInfUseCase(command).getOrThrow()
        val resp = PlanDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }


    //Просматривать список задач из плана

    @GetMapping("/dir/plans/getPlan/{planId}/tasks}")
    fun getPlanTasks(@PathVariable(value = "planId") planId: UUID): ResponseEntity<List<TaskInformationResponse>>{
        val command = GetTasksFromPlanCommand(
            planId = RequestToDomainMapper.toPlanId(planId)
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
            planId = RequestToDomainMapper.toPlanId(planId),
            taskStatus = RequestToDomainMapper.toTaskStatus(status)
        )
        val result = filterPlanTasksByStatusUseCase(command).getOrThrow()
        val resp = PlanDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }

    // Фильтр задач по времени

    @GetMapping("/dir/plans/{planId}/tasks/sortTime")
    fun getPlanTasksSortByTime(@PathVariable(value = "planId") planId: UUID): ResponseEntity<List<TaskInformationResponse>>{
        val command = SortPlanTasksByEndDateCommand(
            planId = RequestToDomainMapper.toPlanId(planId)
        )
        val result = sortPlanTasksByEndDateUseCase(command).getOrThrow()
        val resp = PlanDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }

    // Просматривать порученные задачи

    @GetMapping("/emp/{executorId}/assignedTasks")
    fun getAssignedTasks(@PathVariable(value = "executorId") executorId: UUID): ResponseEntity<List<TaskInformationResponse>>{
        val command = GetAssignedTasksCommand(
            executorId = RequestToDomainMapper.toExecutorId(executorId)
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
            executorId = RequestToDomainMapper.toExecutorId(executorId)
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
            executorId = RequestToDomainMapper.toExecutorId(executorId),
            taskStatus = RequestToDomainMapper.toTaskStatus(status)
        )
        val result = filterAssignedTasksByStatusUseCase(command).getOrThrow()
        val resp = PlanDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }

    // Фильтр порученных задач по времени

    @GetMapping("/emp/{executorId}/assignedTasks/sortTime")
    fun getAssignedTasksSortByTime(@PathVariable(value = "executorId") executorId: UUID): ResponseEntity<List<TaskInformationResponse>>{
        val command = SortAssignedTasksByEndDateCommand(
            executorId = RequestToDomainMapper.toExecutorId(executorId)
        )
        val result = sortAssignedTasksByEndDateUseCase(command).getOrThrow()
        val resp = PlanDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }

    // Экспортировать план мероприятий

    @GetMapping("/dir/plans/{planId}/export")
    fun exportPlan(@PathVariable(value = "planId") planId: UUID): ResponseEntity<ExportPlanResponse>{
        val command = ExportPlanCommand(
            planId = RequestToDomainMapper.toPlanId(planId)
        )
        val result = exportPlanUseCase(command).getOrThrow()
        val resp = PlanDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }

    // Создать планы мероприятий

    fun createPLan(): ResponseEntity<PlanIdResponse>{}

    // Добавить задачу в план мероприятий

    fun addTaskToPlan(): ResponseEntity<TaskIdResponse>{}

    // Просматривать список планов мероприятий

    fun getDirPlans(): ResponseEntity<List<PlanInformationResponse>>{}

    // Фильтр планов по статусу

    fun getDirPlansFilterByStatus(): ResponseEntity<List<PlanInformationResponse>>{}

    // Фильтр планов по времени

    fun getDirPlansSortByTime(): ResponseEntity<List<PlanInformationResponse>>{}

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