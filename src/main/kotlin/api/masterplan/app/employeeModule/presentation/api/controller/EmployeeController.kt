package api.masterplan.app.employeeModule.presentation.api.controller

import api.masterplan.app.employeeModule.application.command.*
import api.masterplan.app.employeeModule.application.dto.FileModel
import api.masterplan.app.employeeModule.application.usecase.*
import api.masterplan.app.employeeModule.domain.dtos.EmployeeDetails
import api.masterplan.app.employeeModule.domain.dtos.EmployeeWithMetricsDetails
import api.masterplan.app.employeeModule.domain.exceptions.EmployeeException
import api.masterplan.app.employeeModule.domain.model.entity.Employee
import api.masterplan.app.employeeModule.domain.model.value.*
import api.masterplan.app.employeeModule.presentation.dto.request.CreateEmployeeRequest
import api.masterplan.app.employeeModule.presentation.dto.request.UpdateEmployeeRequest
import api.masterplan.app.employeeModule.presentation.dto.responce.EmployeeDetailsResponse
import api.masterplan.app.employeeModule.presentation.dto.responce.EmployeeFileResponse
import api.masterplan.app.employeeModule.presentation.dto.responce.EmployeeIdResponse
import api.masterplan.app.employeeModule.presentation.dto.responce.EmployeeWithMetricsDetailsResponse
import api.masterplan.app.employeeModule.presentation.mapper.EmployeeExceptionToHttpCodeMapper
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.util.*


@RestController
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

    @PostMapping("/admin/createEmployee")
    fun createEmployee(@RequestBody request: CreateEmployeeRequest): ResponseEntity<EmployeeIdResponse> {
        val command = try {
            val name = EmployeeName.validate(request.name)
            val surname = EmployeeSurname.validate(request.surname)
            val patronymic = request.patronymic?.let { EmployeePatronymic.validate(it) }
            val directorId = request.directorId?.let { EmployeeId(it) }
            val userId = EmployeeUserId(request.userId)
            CreateEmployeeCommand(
                name = name,
                surname = surname,
                patronymic = patronymic,
                directorId = directorId,
                userId = userId,
            )
        }catch (e: EmployeeException){
            return handleEmployeeIdException(e)
        }
        return createEmployeeUseCase(command).handleEmployeeIdResult()
    }




    @GetMapping("/dir/{directorId}/exportMyEmployees/")
    suspend fun exportDirEmployees(@PathVariable(value = "directorId") directorId: UUID): ResponseEntity<EmployeeFileResponse> {
        val command = try {
            val domainId = EmployeeId(directorId)
            ExportDirEmployeesCommand(
                directorId = domainId
            )
        }catch (e: EmployeeException){
            return handleEmployeeFileException(e)
        }
        return exportDirEmployeesUseCase(command).handleEmployeeFileResult()
    }




    @GetMapping("/dir/{directorId}/myEmployees/")
    fun getDirEmployees(@PathVariable(value = "directorId") directorId: UUID): ResponseEntity<List<EmployeeDetailsResponse>> {
        val command = try {
            val domainId = EmployeeId(directorId)
            GetAllDirectorEmployeesCommand(
                directorId = domainId
            )
        }catch (e: EmployeeException){
            return handleEmployeeDetailsListException(e)
        }
        return getAllDirEmployeesUseCase(command).handleEmployeeDetailsListResult()
    }




    @GetMapping("/admin/allEmployees")
    fun getAllEmployees(): ResponseEntity<List<EmployeeDetailsResponse>> {
        return getAllEmployeesUseCase().handleEmployeeDetailsListResult()
    }

    @GetMapping("/dir/{directorId}/myEmployeesWithoutTasks/")
    suspend fun getDirEmployeesWithoutTasks(@PathVariable(value = "directorId") directorId: UUID): ResponseEntity<List<EmployeeDetailsResponse>> {
        val command = try {
            val domainId = EmployeeId(directorId)
            GetDirEmployeesWithoutTasksCommand(domainId)
        }catch (e: EmployeeException){
            return handleEmployeeDetailsListException(e)
        }
        return getDirEmployeesWithoutTasksUseCase(command).handleEmployeeDetailsListResult()
    }

    @GetMapping("/dir/getEmployee/{id}")
    fun getEmployeeById(@PathVariable(value = "id") empId: UUID): ResponseEntity<EmployeeDetailsResponse> {
        val command = try {
            val employeeId = EmployeeId(empId)
            GetEmployeeByIdCommand(employeeId)
        }catch (e: EmployeeException){
            return handleEmployeeDetailsException(e)
        }
        return getEmployeeByIdUseCase(command).handleEmployeeDetailsResult()
    }



    @GetMapping("/emp/getProfile/{id}")
    fun getProfileInformation(@PathVariable(value = "id") empId: UUID): ResponseEntity<EmployeeWithMetricsDetailsResponse>{
        val command = try {
            val profileId = EmployeeId(empId)
            GetProfileInformationCommand(profileId)
        }catch (e: EmployeeException){
            return handleEmployeeMetricsDetailsException(e)
        }
        return getProfileInformationUseCase(command).handleEmployeeMetricsDetailsResult()
    }




    @GetMapping("/dir/{directorId}/searchEmployee/")
    fun searchDirEmployeeByName(
        @PathVariable(value = "directorId") directorId: UUID,
        @RequestParam query: String): ResponseEntity<List<EmployeeDetailsResponse>>{
        val command = try {
            val domainDirectorId = EmployeeId(directorId)
            SearchDirEmployeeByNameCommand(query, domainDirectorId)
        }catch (e: EmployeeException){
            return handleEmployeeDetailsListException(e)
        }
        return searchDirEmployeeByNameUseCase(command).handleEmployeeDetailsListResult()
    }


    @GetMapping("/admin/searchEmployee/")
    fun searchEmployeeByName(@RequestParam query: String): ResponseEntity<List<EmployeeDetailsResponse>>{
        val command = try {
            SearchEmployeeByNameCommand(query)
        }catch (e: EmployeeException){
            return handleEmployeeDetailsListException(e)
        }
        return searchEmployeeByNameUseCase(command).handleEmployeeDetailsListResult()
    }


    @GetMapping("/dir/{directorId}/getSortedEmpByRating/")
    suspend fun sortDirEmployeesByRating(@PathVariable(value = "directorId") directorId: UUID): ResponseEntity<List<EmployeeDetailsResponse>>{
        val command = try {
            val directorId = EmployeeId(directorId)
            SortDirEmployeesByRatingCommand(directorId)
        }catch (e: EmployeeException){
            return handleEmployeeDetailsListException(e)
        }
        return sortDirEmployeesByRatingUseCase(command).handleEmployeeDetailsListResult()
    }


    @GetMapping("/dir/{directorId}/getSortedEmpByWorkload/")
    suspend fun sortDirEmployeesByWorkload(@PathVariable(value = "directorId") directorId: UUID): ResponseEntity<List<EmployeeDetailsResponse>>{
        val command = try {
            val directorId = EmployeeId(directorId)
            SortDirEmployeesByWorkloadCommand(directorId)
        }catch (e: EmployeeException){
            return handleEmployeeDetailsListException(e)
        }
        return sortDirEmployeesByWorkloadUseCase(command).handleEmployeeDetailsListResult()
    }


    @PatchMapping("/admin/updateEmployee/")
    fun updateEmployee(@RequestBody request: UpdateEmployeeRequest): ResponseEntity<EmployeeIdResponse>{
        val command = try {
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
            UpdateEmployeeCommand(employeeId,newEmployee)
        }catch (e: EmployeeException){
            return handleEmployeeIdException(e)
        }
        return updateEmployeeUseCase(command).handleEmployeeIdResult()
    }


    private fun handleEmployeeIdException(e: EmployeeException): ResponseEntity<EmployeeIdResponse> {
        val status = EmployeeExceptionToHttpCodeMapper.exceptionToHttpCode(e)
        val body = EmployeeIdResponse.Error(
            status = status.value(),
            message = e.message,
            timestamp = LocalDateTime.now()
        )
        return ResponseEntity.status(status).body(body)
    }

    private fun handleEmployeeFileException(e: EmployeeException): ResponseEntity<EmployeeFileResponse> {
        val status = EmployeeExceptionToHttpCodeMapper.exceptionToHttpCode(e)
        val body = EmployeeFileResponse.Error(
            status = status.value(),
            message = e.message,
            timestamp = LocalDateTime.now()
        )
        return ResponseEntity.status(status).body(body)

    }

    private fun handleEmployeeDetailsListException(e: EmployeeException): ResponseEntity<List<EmployeeDetailsResponse>> {
        val status = EmployeeExceptionToHttpCodeMapper.exceptionToHttpCode(e)
        val body = listOf(EmployeeDetailsResponse.Error(
            status = status.value(),
            message = e.message,
            timestamp = LocalDateTime.now()
        ))
        return ResponseEntity.status(status).body(body)
    }

    private fun handleEmployeeMetricsDetailsException(e: EmployeeException): ResponseEntity<EmployeeWithMetricsDetailsResponse> {
        val status = EmployeeExceptionToHttpCodeMapper.exceptionToHttpCode(e)
        val body = EmployeeWithMetricsDetailsResponse.Error(
            status = status.value(),
            message = e.message,
            timestamp = LocalDateTime.now()
        )
        return ResponseEntity.status(status).body(body)
    }

    private fun handleEmployeeDetailsException(e: EmployeeException): ResponseEntity<EmployeeDetailsResponse> {
        val status = EmployeeExceptionToHttpCodeMapper.exceptionToHttpCode(e)
        val body = EmployeeDetailsResponse.Error(
            status = status.value(),
            message = e.message,
            timestamp = LocalDateTime.now()
        )
        return ResponseEntity.status(status).body(body)
    }



    private fun Result<EmployeeId>.handleEmployeeIdResult(): ResponseEntity<EmployeeIdResponse> {}

    private fun Result<FileModel>.handleEmployeeFileResult(): ResponseEntity<EmployeeFileResponse> {}

    private fun Result<EmployeeWithMetricsDetails>.handleEmployeeMetricsDetailsResult(): ResponseEntity<EmployeeWithMetricsDetailsResponse> {
        TODO("Not yet implemented")
    }

    private fun Result<EmployeeDetails>.handleEmployeeDetailsResult(): ResponseEntity<EmployeeDetailsResponse> {
        TODO("Not yet implemented")
    }


    private fun Result<List<EmployeeDetails>>.handleEmployeeDetailsListResult(): ResponseEntity<List<EmployeeDetailsResponse>> {

    }
}


