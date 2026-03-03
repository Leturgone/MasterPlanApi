package api.masterplan.app.plansModule.presentation.api.controller

import api.masterplan.app.plansModule.application.command.GetDirPlansCommand
import api.masterplan.app.plansModule.application.command.GetPlanInfCommand
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
import api.masterplan.app.plansModule.domain.model.value.PlanId
import api.masterplan.app.plansModule.presentation.api.exceptionHandler.PlanControllerExceptionHandler
import api.masterplan.app.plansModule.presentation.dto.response.ExportPlanResponse
import api.masterplan.app.plansModule.presentation.dto.response.PlanIdResponse
import api.masterplan.app.plansModule.presentation.dto.response.PlanInformationResponse
import api.masterplan.app.plansModule.presentation.dto.response.TaskIdResponse
import api.masterplan.app.plansModule.presentation.dto.response.TaskInformationResponse
import api.masterplan.app.plansModule.presentation.mapper.PlanDomainToResponseMapper
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
    @GetMapping("/emp/plans/getPlan{planId}")
    fun getPlanInformation(@PathVariable(value = "planId") planId: UUID): ResponseEntity<PlanInformationResponse> {
        val command = GetPlanInfCommand(
            planId = PlanId(planId)
        )
        val result = getPlanInfUseCase(command).getOrThrow()
        val resp = PlanDomainToResponseMapper.toResponse(result)
        return ResponseEntity.ok(resp)
    }


    // Получить информацию о задаче

    @GetMapping("/emp/tasks/getTask{taskId}")
    fun getTaskInformation(@PathVariable(value = "taskId") taskId: UUID): ResponseEntity<TaskInformationResponse> {}


    //Просматривать список задач из плана

    fun getPlanTasks(): ResponseEntity<List<TaskInformationResponse>>{}

    // Фильтр задач по статусу
    fun getPlanTasksFilterByStatus(): ResponseEntity<List<TaskInformationResponse>>{}

    // Фильтр задач по времени

    fun getPlanTasksSortByTime(): ResponseEntity<List<TaskInformationResponse>>{}

    // Просматривать порученные задачи

    fun getAssignedTasks(): ResponseEntity<List<TaskInformationResponse>>{}

    // Поиск задач по названию

    fun searchAssignedTasksByTitle(): ResponseEntity<List<TaskInformationResponse>>{}

    // Фильтр порученных задач по статусу

    fun getAssignedTasksFilterByStatus(): ResponseEntity<List<TaskInformationResponse>>{}

    // Фильтр порученных задач по времени

    fun getAssignedTasksSortByTime(): ResponseEntity<List<TaskInformationResponse>>{}

    // Экспортировать план мероприятий

    fun exportPlan(): ResponseEntity<ExportPlanResponse>{}

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