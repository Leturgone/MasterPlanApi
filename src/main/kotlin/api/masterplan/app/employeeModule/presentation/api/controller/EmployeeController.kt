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
import kotlinx.coroutines.runBlocking
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
    private val getEmployeeByUserIdUseCase: GetEmployeeByUserIdUseCase,
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
    @PostMapping("/admin/employee")
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
            ),
            ApiResponse(
                responseCode = "404",
                description = "Руководитель не найден",
                content = [Content(schema = Schema(implementation = EmployeeDetailsResponse::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера: сбой при экспорте сотрудников",
                content = [Content(schema = Schema(implementation = EmployeeErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли руководителя"
            )

        ]
    )
    @GetMapping("/dir/employee/{directorId}/employees/export")
    fun exportDirEmployees(@PathVariable(value = "directorId") directorId: UUID): ResponseEntity<ByteArray> {
        return runBlocking {
            val domainId = EmployeeId(directorId)
            val command = ExportDirEmployeesCommand(directorId = domainId)
            val result = exportDirEmployeesUseCase(command).getOrThrow()
            val resp = EmployeeDomainToResponseMapper.empFileToResponse(result)
            ResponseEntity.ok().headers(resp.fileHeaders).body(resp.fileData)
        }


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
                description = "Нет роли руководителя"
            )

        ]
    )
    @GetMapping("/dir/employee/{directorId}/employees")
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
    @GetMapping("/admin/employee")
    fun getAllEmployees(): ResponseEntity<List<EmployeeDetailsResponse>> {
        val result = getAllEmployeesUseCase().getOrThrow()
        val resp = EmployeeDomainToResponseMapper.empDetailsListToResponse(result)
        return ResponseEntity.ok(resp)
    }



    @Operation(
        summary = "Получение списка подчиненных сотрудников без задач",
        description = "Получение списка подчиненных сотрудников без задач по id руководителя",
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
                description = "Нет роли руководителя"
            )

        ]
    )
    @GetMapping("/dir/employee/{directorId}/employees/withoutTasks")
    fun getDirEmployeesWithoutTasks(@PathVariable(value = "directorId") directorId: UUID): ResponseEntity<List<EmployeeDetailsResponse>> {
        return runBlocking {
            val domainId = EmployeeId(directorId)
            val command = GetDirEmployeesWithoutTasksCommand(domainId)
            val result = getDirEmployeesWithoutTasksUseCase(command).getOrThrow()
            val resp = EmployeeDomainToResponseMapper.empDetailsListToResponse(result)
            ResponseEntity.ok(resp)
        }

    }


    @Operation(
        summary = "Получение данных сотрудника по id",
        description = "Получение данных сотрудника по id",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Сотрудник успешно получен",
                content = [Content(schema = Schema(implementation = EmployeeDetailsResponse::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Сотрудник не найден",
                content = [Content(schema = Schema(implementation = EmployeeDetailsResponse::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера: сбой при получении данных сотрудника",
                content = [Content(schema = Schema(implementation = EmployeeErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли руководителя"
            )

        ]
    )
    @GetMapping("/dir/employee/{id}")
    fun getEmployeeById(@PathVariable(value = "id") empId: UUID): ResponseEntity<EmployeeDetailsResponse> {
        val employeeId = EmployeeId(empId)
        val command = GetEmployeeByIdCommand(employeeId)
        val result = getEmployeeByIdUseCase(command).getOrThrow()
        val resp = EmployeeDomainToResponseMapper.empDetailsToResponse(result)
        return ResponseEntity.ok(resp)
    }

    @Operation(
        summary = "Получение данных сотрудника по id пользователя",
        description = "Получение данных сотрудника по id пользователя",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Сотрудник успешно получен",
                content = [Content(schema = Schema(implementation = EmployeeDetailsResponse::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Сотрудник не найден",
                content = [Content(schema = Schema(implementation = EmployeeDetailsResponse::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера: сбой при получении данных сотрудника",
                content = [Content(schema = Schema(implementation = EmployeeErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли руководителя"
            )

        ]
    )
    @GetMapping("/emp/employee/userId/{id}")
    fun getEmployeeByUserId(@PathVariable(value = "id") userId: UUID): ResponseEntity<EmployeeDetailsResponse> {
        val userId = EmployeeUserId(userId)
        val command = GetEmployeeByUserIdCommand(userId)
        val result = getEmployeeByUserIdUseCase(command).getOrThrow()
        val resp = EmployeeDomainToResponseMapper.empDetailsToResponse(result)
        return ResponseEntity.ok(resp)
    }



    @Operation(
        summary = "Получение данных профиля по id",
        description = "Получение данных профиля включающего метрики и данные о руководителе по id",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Профиль успешно получен",
                content = [Content(schema = Schema(implementation = EmployeeWithMetricsDetailsResponse::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Сотрудник не найден",
                content = [Content(schema = Schema(implementation = EmployeeDetailsResponse::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера: сбой при получении данных профиля",
                content = [Content(schema = Schema(implementation = EmployeeErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли сотрудника"
            )

        ]
    )
    @GetMapping("/emp/profile/{id}")
    fun getProfileInformation(@PathVariable(value = "id") empId: UUID): ResponseEntity<EmployeeWithMetricsDetailsResponse>{
        val profileId = EmployeeId(empId)
        val command = GetProfileInformationCommand(profileId)
        val result = getProfileInformationUseCase(command).getOrThrow()
        val resp = EmployeeDomainToResponseMapper.empMetricsDetailsToResponse(result)
        return ResponseEntity.ok(resp)
    }



    @Operation(
        summary = "Поиск подчиненного сотрудника по имени или фамилии",
        description = "Получение списка подчиненных сотрудников по имени или фамилии",
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
                description = "Нет роли руководителя"
            )

        ]
    )
    @GetMapping("/dir/employee/{directorId}/employees/search/")
    fun searchDirEmployeeByName(
        @PathVariable(value = "directorId") directorId: UUID,
        @RequestParam query: String): ResponseEntity<List<EmployeeDetailsResponse>>{
        val domainDirectorId = EmployeeId(directorId)
        val command = SearchDirEmployeeByNameCommand(query, domainDirectorId)
        val result = searchDirEmployeeByNameUseCase(command).getOrThrow()
        val resp = EmployeeDomainToResponseMapper.empDetailsListToResponse(result)
        return ResponseEntity.ok(resp)
    }


    @Operation(
        summary = "Поиск сотрудника по имени или фамилии",
        description = "Получение списка сотрудников по имени или фамилии",
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
    @GetMapping("/admin/employee/search/")
    fun searchEmployeeByName(@RequestParam query: String): ResponseEntity<List<EmployeeDetailsResponse>>{
        val command = SearchEmployeeByNameCommand(query)
        val result = searchEmployeeByNameUseCase(command).getOrThrow()
        val resp = EmployeeDomainToResponseMapper.empDetailsListToResponse(result)
        return ResponseEntity.ok(resp)
    }


    @Operation(
        summary = "Сортировка подчиненных сотрудников по рейтингу",
        description = "Получение списка подчиненных сотрудников отсортированных по рейтингу",
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
                description = "Нет роли руководителя"
            )

        ]
    )
    @GetMapping("/dir/{directorId}/getSortedEmpByRating/")
    fun sortDirEmployeesByRating(@PathVariable(value = "directorId") directorId: UUID): ResponseEntity<List<EmployeeDetailsResponse>>{
        return runBlocking {
            val directorId = EmployeeId(directorId)
            val command = SortDirEmployeesByRatingCommand(directorId)
            val result = sortDirEmployeesByRatingUseCase(command).getOrThrow()
            val resp = EmployeeDomainToResponseMapper.empDetailsListToResponse(result)
            ResponseEntity.ok(resp)
        }

    }



    @Operation(
        summary = "Сортировка подчиненных сотрудников по загруженности",
        description = "Получение списка подчиненных сотрудников отсортированных по загруженности",
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
                description = "Нет роли руководителя"
            )

        ]
    )
    @GetMapping("/dir/{directorId}/employeesgetSortedEmpByWorkload/")
    fun sortDirEmployeesByWorkload(@PathVariable(value = "directorId") directorId: UUID): ResponseEntity<List<EmployeeDetailsResponse>>{
        return runBlocking {
            val directorId = EmployeeId(directorId)
            val command = SortDirEmployeesByWorkloadCommand(directorId)
            val result = sortDirEmployeesByWorkloadUseCase(command).getOrThrow()
            val resp = EmployeeDomainToResponseMapper.empDetailsListToResponse(result)
            ResponseEntity.ok(resp)
        }

    }


    @Operation(
        summary = "Обновление сотрудника по id",
        description = "Обновление сотрудника по id путем загрузки новых данных",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Сотрудник обновлен",
                content = [Content(
                    array = ArraySchema(schema = Schema(implementation = EmployeeIdResponse::class))
                )]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Сотрудник не найден",
                content = [Content(schema = Schema(implementation = EmployeeDetailsResponse::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Внутренняя ошибка сервера: сбой при обновлении сотрудника",
                content = [Content(schema = Schema(implementation = EmployeeErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Нет роли руководителя"
            )

        ]
    )
    @PatchMapping("/admin/employee/")
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


