package api.masterplan.app.employeeModule.application.service

import api.masterplan.app.employeeModule.application.mapper.EmpEntityToDtoMapper
import api.masterplan.app.employeeModule.domain.dtos.DirectorDetails
import api.masterplan.app.employeeModule.domain.dtos.EmployeeDetails
import api.masterplan.app.employeeModule.domain.dtos.EmployeeWithMetricsDetails
import api.masterplan.app.employeeModule.domain.exceptions.EmployeeException
import api.masterplan.app.employeeModule.domain.interfaces.EmployeeMetricsService
import api.masterplan.app.employeeModule.domain.interfaces.EmployeeRepository
import api.masterplan.app.employeeModule.domain.interfaces.EmployeeService
import api.masterplan.app.employeeModule.domain.model.entity.Employee
import api.masterplan.app.employeeModule.domain.model.value.EmployeeId
import api.masterplan.app.employeeModule.domain.model.value.EmployeeName
import api.masterplan.app.employeeModule.domain.model.value.EmployeePatronymic
import api.masterplan.app.employeeModule.domain.model.value.EmployeeSurname
import api.masterplan.app.employeeModule.domain.model.value.EmployeeUserId
import api.masterplan.app.logging.LoggingMethod
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EmployeeServiceImpl(
    private val employeeRepository: EmployeeRepository,
    private val employeeMetricsService: EmployeeMetricsService,
): EmployeeService {

    @LoggingMethod("employeeModule")
    override fun getAllEmployees(): List<EmployeeDetails> {
        val employees = employeeRepository.getAllEmployees()

        return employees.map {
            EmpEntityToDtoMapper.toEmployeeDetails(it)
        }
    }


    @LoggingMethod("employeeModule")
    override fun getEmployeeById(id: EmployeeId): EmployeeDetails {
        val employee = employeeRepository.getEmployeeById(id) ?: throw EmployeeException.EmployeeNotExist(id)

        return EmpEntityToDtoMapper.toEmployeeDetails(employee)
    }


    @LoggingMethod("employeeModule")
    override fun searchEmployee(query: String): List<EmployeeDetails> {
        val searchResult = employeeRepository.searchByNameOrSurname(query)

        return searchResult.map { EmpEntityToDtoMapper.toEmployeeDetails(it) }
    }

    override fun searchDirEmployee(query: String, directorId: EmployeeId): List<EmployeeDetails> {
        val searchResult = employeeRepository.searchByNameOrSurnameAndDirId(query, directorId)

        return searchResult.map { EmpEntityToDtoMapper.toEmployeeDetails(it) }
    }


    @LoggingMethod("employeeModule")
    override fun getAllDirectorsEmployee(directorId: EmployeeId): List<EmployeeDetails> {
        val employees = employeeRepository.findByDirectorId(directorId)

        return employees.map { EmpEntityToDtoMapper.toEmployeeDetails(it) }
    }


    @LoggingMethod("employeeModule")
    @Transactional
    override suspend fun getAllDirectorsEmployeeSortByRating(directorId: EmployeeId): List<EmployeeDetails> {
        val employees = employeeRepository.findByDirectorId(directorId)

        val metricsMap = employeeMetricsService.calculateMetricsForEmployees(employees)

        return employees.sortedByDescending { empl ->
            metricsMap[empl]?.rating ?: 0.0
        }.map {
            EmpEntityToDtoMapper.toEmployeeDetails(it)
        }
    }


    @LoggingMethod("employeeModule")
    @Transactional
    override suspend fun getAllDirectorsEmployeeSortByWorkLoad(directorId: EmployeeId): List<EmployeeDetails> {
        val employees = employeeRepository.findByDirectorId(directorId)

        val metricsMap = employeeMetricsService.calculateMetricsForEmployees(employees)

        return employees.sortedByDescending { empl ->
            metricsMap[empl]?.workload ?: 0.0
        }.map {
            EmpEntityToDtoMapper.toEmployeeDetails(it)
        }
    }

    @LoggingMethod("employeeModule")
    @Transactional
    override suspend fun getAllDirectorsEmployeesWithoutTasks(directorId: EmployeeId): List<EmployeeDetails> {
        val employees = employeeRepository.findByDirectorId(directorId)

        val metricsMap = employeeMetricsService.calculateMetricsForEmployees(employees)

        return employees.filter { empl ->
            metricsMap[empl]?.assignedTasksCount == 0  || metricsMap[empl] == null
        }.map {
            EmpEntityToDtoMapper.toEmployeeDetails(it)
        }
    }


    @LoggingMethod("employeeModule")
    @Transactional
    override fun createEmployee(
        id: EmployeeId?, name: EmployeeName, surname: EmployeeSurname,
        patronymic: EmployeePatronymic?, directorId: EmployeeId?, userId: EmployeeUserId
    ): EmployeeId {

        if (employeeRepository.isEmployeeExist(userId)) throw EmployeeException.EmployeeAlreadyExists(
            name,surname,patronymic
        )

        val employeeEntity = Employee.create(
            id = id,
            name = name,
            surname = surname,
            patronymic = patronymic,
            directorId = directorId,
            userId = userId,
        )

        val employeeId = employeeRepository.saveEmployee(employeeEntity)?: throw EmployeeException.FailedToCreateEmployee(
            name,surname,patronymic
        )
        return employeeId
    }


    @LoggingMethod("employeeModule")
    override fun updateEmployee(id: EmployeeId, newEmployee: Employee): EmployeeDetails {

        employeeRepository.getEmployeeById(id) ?: throw EmployeeException.EmployeeNotExist(id)

        val updatedEmployee = employeeRepository.updateEmployee(id, newEmployee)
            ?: throw EmployeeException.FailedToUpdateEmployee(id)

        val updateEmployeeDetails = EmpEntityToDtoMapper.toEmployeeDetails(updatedEmployee)

        return updateEmployeeDetails
    }


    @LoggingMethod("employeeModule")
    @Transactional
    override fun getEmployeeWithMetrics(employeeId: EmployeeId): EmployeeWithMetricsDetails {
        val employeeEntity = employeeRepository.getEmployeeById(employeeId) ?: throw EmployeeException
            .EmployeeNotExist(employeeId)

        val directorProfile = employeeEntity.directorId?.let {
            employeeRepository.getEmployeeById(it)
        } ?: throw EmployeeException.FailedToGetDirectorDetailsForEmployee(employeeId)

        val directorDetails = DirectorDetails(directorProfile.name, directorProfile.surname, directorProfile.patronymic)

        val metrics = employeeMetricsService.calculateMetricsForEmployee(employeeId)

        val profile = EmpEntityToDtoMapper.toEmployeeWithMetricsDetails(
            entity = employeeEntity,
            directorDetails = directorDetails,
            metrics = metrics,
        )

        return profile
    }


}