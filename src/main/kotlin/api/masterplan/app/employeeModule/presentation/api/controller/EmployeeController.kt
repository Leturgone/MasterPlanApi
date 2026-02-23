package api.masterplan.app.employeeModule.presentation.api.controller

import api.masterplan.app.employeeModule.application.command.*
import api.masterplan.app.employeeModule.application.usecase.*
import api.masterplan.app.employeeModule.domain.model.entity.Employee
import api.masterplan.app.employeeModule.domain.model.value.*
import api.masterplan.app.employeeModule.presentation.api.exceptionHandler.EmployeeControllerExceptionHandler
import api.masterplan.app.employeeModule.presentation.dto.request.CreateEmployeeRequest
import api.masterplan.app.employeeModule.presentation.dto.request.UpdateEmployeeRequest
import api.masterplan.app.employeeModule.presentation.dto.responce.*
import api.masterplan.app.employeeModule.presentation.mapper.EmployeeDomainToResponseMapper
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
@EmployeeControllerExceptionHandler
@RequestMapping("/api/v1/employees")
@Tag(name = "Employees", description = "Управление сотрудниками")
class EmployeeController(
    private val createEmployeeUseCase: CreateEmployeeUseCase,
    private val exportDirEmployeesUseCase: ExportDirEmployeesUseCase,
    private val getAllDirEmployeesUseCase: GetAllDirectorEmployeesUseCase,
    private val getAllEmployeesUseCase: GetAllEmployeesUseCase,
    private val getDirEmployeesWithoutTasksUseCase: GetDirEmployeesWithoutTasksUseCase,
    private val getEmployeeByIdUseCase: GetEmployeeByIdUseCase,
    private val getProfileInformationUseCase: GetProfileInformationUseCase,
    private val searchDirEmployeeByNameUseCase: SearchDirEmployeeByNameUseCase,
    private val searchEmployeeByNameUseCase: SearchEmployeeByNameUseCase,
    private val sortDirEmployeesByRatingUseCase: SortDirEmployeesByRatingUseCase,
    private val sortDirEmployeesByWorkloadUseCase: SortDirEmployeesByWorkloadUseCase,
    private val updateEmployeeUseCase: UpdateEmployeeUseCase
) {

    @Operation(
        summary = "Создание сотрудника",
        description = "Создание сотрудника с передачей данных сотрудника",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Сотрудник успешно создан",
                content = [Content(schema = Schema(implementation = EmployeeIdResponse::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Некорректные данные: пустое имя фамилия и тд",
                content = [Content(schema = Schema(implementation = EmployeeErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "409",
                description = "Сотрудник с указанными данными уже существует",
                content = [Content(schema = Schema(implementation = EmployeeErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера: сбой при создании сотрудника",
                content = [Content(schema = Schema(implementation = EmployeeErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли админа"
            )

        ]
    )
    @PostMapping("/admin/createEmployee")
    fun createEmployee(@RequestBody request: CreateEmployeeRequest): ResponseEntity<EmployeeIdResponse> {
        val name = EmployeeName.validate(request.name)
        val surname = EmployeeSurname.validate(request.surname)
        val patronymic = request.patronymic?.let { EmployeePatronymic.validate(it) }
        val directorId = request.directorId?.let { EmployeeId(it) }
        val userId = EmployeeUserId(request.userId)
        val command = CreateEmployeeCommand(
            name = name,
            surname = surname,
            patronymic = patronymic,
            directorId = directorId,
            userId = userId,
        )
        val result = createEmployeeUseCase(command).getOrThrow()
        val resp = EmployeeDomainToResponseMapper.empIdToResponse(result)
        return ResponseEntity.ok(resp)
    }



    @Operation(
        summary = "Экспорт списка сотрудников",
        description = "Экспорт списка сотрудников с метриками в виде Эксель таблицы",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Список сотрудников успешно экспортирован",
                content = [Content(schema = Schema(implementation = EmployeeFileResponse::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера: сбой при экспорте сотрудников",
                content = [Content(schema = Schema(implementation = EmployeeErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли директора"
            )

        ]
    )
    @GetMapping("/dir/{directorId}/exportMyEmployees/")
    suspend fun exportDirEmployees(@PathVariable(value = "directorId") directorId: UUID): ResponseEntity<EmployeeFileResponse> {
        val domainId = EmployeeId(directorId)
        val command = ExportDirEmployeesCommand(directorId = domainId)
        val result = exportDirEmployeesUseCase(command).getOrThrow()
        val resp = EmployeeDomainToResponseMapper.empFileToResponse(result)
        return ResponseEntity.ok(resp)
    }



    @Operation(
        summary = "Получение списка подчиненных сотрудников",
        description = "Получение списка подчиненных сотрудников по id руоводителя",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Список сотрудников успешно получен",
                content = [Content(
                    array = ArraySchema(schema = Schema(implementation = EmployeeDetailsResponse::class))
                )]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера: сбой при получении списка сотрудников",
                content = [Content(schema = Schema(implementation = EmployeeErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли директора"
            )

        ]
    )
    @GetMapping("/dir/{directorId}/myEmployees/")
    fun getDirEmployees(@PathVariable(value = "directorId") directorId: UUID): ResponseEntity<List<EmployeeDetailsResponse>> {

        val domainId = EmployeeId(directorId)
        val command = GetAllDirectorEmployeesCommand(directorId = domainId)
        val result =  getAllDirEmployeesUseCase(command).getOrThrow()
        val resp = EmployeeDomainToResponseMapper.empDetailsListToResponse(result)
        return ResponseEntity.ok(resp)
    }


    @Operation(
        summary = "Получение списка всех сотрудников",
        description = "Получение списка всех сотрудников",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Список сотрудников успешно получен",
                content = [Content(
                    array = ArraySchema(schema = Schema(implementation = EmployeeDetailsResponse::class))
                )]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера: сбой при получении списка сотрудников",
                content = [Content(schema = Schema(implementation = EmployeeErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли админа"
            )

        ]
    )
    @GetMapping("/admin/allEmployees")
    fun getAllEmployees(): ResponseEntity<List<EmployeeDetailsResponse>> {
        val result = getAllEmployeesUseCase().getOrThrow()
        val resp = EmployeeDomainToResponseMapper.empDetailsListToResponse(result)
        return ResponseEntity.ok(resp)
    }



    @GetMapping("/dir/{directorId}/myEmployeesWithoutTasks/")
    suspend fun getDirEmployeesWithoutTasks(@PathVariable(value = "directorId") directorId: UUID): ResponseEntity<List<EmployeeDetailsResponse>> {
        val domainId = EmployeeId(directorId)
        val command = GetDirEmployeesWithoutTasksCommand(domainId)
        val result = getDirEmployeesWithoutTasksUseCase(command).getOrThrow()
        val resp = EmployeeDomainToResponseMapper.empDetailsListToResponse(result)
        return ResponseEntity.ok(resp)
    }

    @GetMapping("/dir/getEmployee/{id}")
    fun getEmployeeById(@PathVariable(value = "id") empId: UUID): ResponseEntity<EmployeeDetailsResponse> {
        val employeeId = EmployeeId(empId)
        val command = GetEmployeeByIdCommand(employeeId)
        val result = getEmployeeByIdUseCase(command).getOrThrow()
        val resp = EmployeeDomainToResponseMapper.empDetailsToResponse(result)
        return ResponseEntity.ok(resp)
    }



    @GetMapping("/emp/getProfile/{id}")
    fun getProfileInformation(@PathVariable(value = "id") empId: UUID): ResponseEntity<EmployeeWithMetricsDetailsResponse>{
        val profileId = EmployeeId(empId)
        val command = GetProfileInformationCommand(profileId)
        val result = getProfileInformationUseCase(command).getOrThrow()
        val resp = EmployeeDomainToResponseMapper.empMetricsDetailsToResponse(result)
        return ResponseEntity.ok(resp)
    }




    @GetMapping("/dir/{directorId}/searchEmployee/")
    fun searchDirEmployeeByName(
        @PathVariable(value = "directorId") directorId: UUID,
        @RequestParam query: String): ResponseEntity<List<EmployeeDetailsResponse>>{
        val domainDirectorId = EmployeeId(directorId)
        val command = SearchDirEmployeeByNameCommand(query, domainDirectorId)
        val result = searchDirEmployeeByNameUseCase(command).getOrThrow()
        val resp = EmployeeDomainToResponseMapper.empDetailsListToResponse(result)
        return ResponseEntity.ok(resp)
    }


    @GetMapping("/admin/searchEmployee/")
    fun searchEmployeeByName(@RequestParam query: String): ResponseEntity<List<EmployeeDetailsResponse>>{
        val command = SearchEmployeeByNameCommand(query)
        val result = searchEmployeeByNameUseCase(command).getOrThrow()
        val resp = EmployeeDomainToResponseMapper.empDetailsListToResponse(result)
        return ResponseEntity.ok(resp)
    }


    @GetMapping("/dir/{directorId}/getSortedEmpByRating/")
    suspend fun sortDirEmployeesByRating(@PathVariable(value = "directorId") directorId: UUID): ResponseEntity<List<EmployeeDetailsResponse>>{
        val directorId = EmployeeId(directorId)
        val command = SortDirEmployeesByRatingCommand(directorId)
        val result = sortDirEmployeesByRatingUseCase(command).getOrThrow()
        val resp = EmployeeDomainToResponseMapper.empDetailsListToResponse(result)
        return ResponseEntity.ok(resp)
    }


    @GetMapping("/dir/{directorId}/getSortedEmpByWorkload/")
    suspend fun sortDirEmployeesByWorkload(@PathVariable(value = "directorId") directorId: UUID): ResponseEntity<List<EmployeeDetailsResponse>>{
        val directorId = EmployeeId(directorId)
        val command = SortDirEmployeesByWorkloadCommand(directorId)
        val result = sortDirEmployeesByWorkloadUseCase(command).getOrThrow()
        val resp = EmployeeDomainToResponseMapper.empDetailsListToResponse(result)
        return ResponseEntity.ok(resp)
    }


    @PatchMapping("/admin/updateEmployee/")
    fun updateEmployee(@RequestBody request: UpdateEmployeeRequest): ResponseEntity<EmployeeIdResponse>{
        val employeeId = EmployeeId(request.id)
        val newName = EmployeeName.validate(request.newName)
        val newSurname = EmployeeSurname.validate(request.newSurname)
        val newPatronymic = request.newPatronymic?.let { EmployeePatronymic.validate(it) }
        val newDirectorId = request.newDirectorId?.let { EmployeeId(it) }
        val userId = EmployeeUserId(request.userId)
        val newEmployee = Employee.create(
            id = employeeId,
            name = newName,
            surname = newSurname,
            patronymic = newPatronymic,
            directorId = newDirectorId,
            userId = userId,
        )
        val command = UpdateEmployeeCommand(employeeId,newEmployee)
        val result = updateEmployeeUseCase(command).getOrThrow()
        val resp = EmployeeDomainToResponseMapper.empIdToResponse(result)
        return ResponseEntity.ok(resp)
    }
}


