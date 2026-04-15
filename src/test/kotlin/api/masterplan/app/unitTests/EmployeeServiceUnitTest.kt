package api.masterplan.app.unitTests

import api.masterplan.app.employeeModule.application.service.EmployeeServiceImpl
import api.masterplan.app.employeeModule.domain.dtos.DirectorDetails
import api.masterplan.app.employeeModule.domain.exceptions.EmployeeException
import api.masterplan.app.employeeModule.domain.interfaces.EmployeeMetricsService
import api.masterplan.app.employeeModule.domain.interfaces.EmployeeRepository
import api.masterplan.app.employeeModule.domain.model.entity.Employee
import api.masterplan.app.employeeModule.domain.model.value.*
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.UUID


class EmployeeServiceUnitTest {

    // Данные для моков
    private val employeeId = EmployeeId.generate()
    private val directorId = EmployeeId.generate()
    private val userId = EmployeeUserId(UUID.randomUUID())
    private val name = EmployeeName("Name")
    private val surname = EmployeeSurname("Surname")
    private val patronymic = EmployeePatronymic("Patronymic")
    private val query = "Name"

    private val employeeRepository = mockk<EmployeeRepository>()
    private val employeeMetricsService = mockk<EmployeeMetricsService>()
    private val employeeService = EmployeeServiceImpl(employeeRepository, employeeMetricsService)

    @Test
    fun `getAllEmployees return list of employee details`() {
        val employee1 = Employee.create(
            id = EmployeeId.generate(),
            name = EmployeeName("Name1"),
            surname = EmployeeSurname("Surname2"),
            patronymic = null,
            directorId = directorId,
            userId = userId
        )
        val employee2 = Employee.create(
            id = EmployeeId.generate(),
            name = name,
            surname = surname,
            patronymic = patronymic,
            directorId = directorId,
            userId = EmployeeUserId(UUID.randomUUID())
        )
        val employees = listOf(employee1, employee2)

        every { employeeRepository.getAllEmployees() } returns employees

        val result = employeeService.getAllEmployees()

        assertEquals(2, result.size)
        assertEquals("Name1", result[0].name.value)
        assertEquals("Name", result[1].name.value)
    }

    @Test
    fun `getEmployeeById return employee details when employee exists`() {
        val employee = Employee.create(
            id = employeeId,
            name = name,
            surname = surname,
            patronymic = patronymic,
            directorId = directorId,
            userId = userId
        )

        every { employeeRepository.getEmployeeById(employeeId) } returns employee

        val result = employeeService.getEmployeeById(employeeId)

        assertEquals(employeeId, result.id)
        assertEquals(name, result.name)
        assertEquals(surname, result.surname)
    }

    @Test
    fun `getEmployeeById throw EmployeeNotExist when employee does not exist`() {
        every { employeeRepository.getEmployeeById(employeeId) } returns null

        assertThrows<EmployeeException.EmployeeNotExist> {
            employeeService.getEmployeeById(employeeId)
        }
    }

    @Test
    fun `getEmployeeByUserId return employee details when employee exists`() {
        val employee = Employee.create(
            id = employeeId,
            name = name,
            surname = surname,
            patronymic = patronymic,
            directorId = directorId,
            userId = userId
        )

        every { employeeRepository.getEmployeeByUserId(userId) } returns employee
        val result = employeeService.getEmployeeByUserId(userId)

        assertEquals(employeeId, result.id)
        assertEquals(userId, result.userId)
    }

    @Test
    fun `getEmployeeByUserId throw EmployeeNotExistWithUserId when employee with user id does not exist`() {
        every { employeeRepository.getEmployeeByUserId(userId) } returns null

        assertThrows<EmployeeException.EmployeeNotExistWithUserId> {
            employeeService.getEmployeeByUserId(userId)
        }
    }

    @Test
    fun `searchEmployee return list of employees by query`() {
        val searchResult = listOf(
            Employee.create(
                id = EmployeeId.generate(),
                name = name,
                surname = surname,
                patronymic = null,
                directorId = directorId,
                userId = userId
            )
        )

        every { employeeRepository.searchByNameOrSurname(query) } returns searchResult

        val result = employeeService.searchEmployee(query)

        assertEquals(1, result.size)
        assertTrue(result[0].name.value.contains("Name"))
    }

    @Test
    fun `searchDirEmployee return list of employees for director`() = runBlocking {
        val searchResult = listOf(
            Employee.create(
                id = EmployeeId.generate(),
                name = name,
                surname = surname,
                patronymic = patronymic,
                directorId = directorId,
                userId = userId
            )
        )

        every { employeeRepository.searchByNameOrSurnameAndDirId(query, directorId) } returns searchResult

        val result = employeeService.searchDirEmployee(query, directorId)

        assertEquals(1, result.size)
        assertEquals(directorId, result[0].directorId)
    }

    @Test
    fun `getAllDirectorsEmployee return employees for director`() {
        val employees = listOf(
            Employee.create(
                id = EmployeeId.generate(),
                name = name,
                surname = surname,
                patronymic = patronymic,
                directorId = directorId,
                userId = userId
            )
        )

        every { employeeRepository.findByDirectorId(directorId) } returns employees

        val result = employeeService.getAllDirectorsEmployee(directorId)

        assertEquals(1, result.size)
        assertEquals(directorId, result[0].directorId)
    }

    @Test
    fun `getAllDirectorsEmployeesWithMetrics return employees with metrics`() = runBlocking {
        val director = Employee.create(
            id = directorId,
            name = EmployeeName("Директор"),
            surname = EmployeeSurname("Главный"),
            patronymic = null,
            directorId = null,
            userId = EmployeeUserId(UUID.randomUUID())
        )
        val employee = Employee.create(
            id = employeeId,
            name = name,
            surname = surname,
            patronymic = patronymic,
            directorId = directorId,
            userId = userId
        )
        val employees = listOf(employee)
        val metrics = EmployeeMetrics(4.5, 80.0, 3)

        every { employeeRepository.getEmployeeById(directorId) } returns director
        every { employeeRepository.findByDirectorId(directorId) } returns employees
        coEvery { employeeMetricsService.calculateMetricsForEmployees(employees) } returns mapOf(employee to metrics)


        val result = employeeService.getAllDirectorsEmployeesWithMetrics(directorId)

        assertEquals(1, result.size)
        assertEquals(metrics, result[0].metrics)
    }

    @Test
    fun `createEmployee create new employee successfully`() {
        val employeeEntity = Employee.create(
            id = employeeId,
            name = name,
            surname = surname,
            patronymic = patronymic,
            directorId = directorId,
            userId = userId
        )

        every { employeeRepository.isEmployeeExist(userId) } returns false
        every { employeeRepository.saveEmployee(employeeEntity) } returns employeeId

        val result = employeeService.createEmployee(
            employeeId, name, surname, patronymic, directorId, userId
        )

        assertEquals(employeeId, result)
    }

    @Test
    fun `createEmployee throw EmployeeAlreadyExists when employee already exists`() {
        every { employeeRepository.isEmployeeExist(userId) } returns true

        assertThrows<EmployeeException.EmployeeAlreadyExists> {
            employeeService.createEmployee(null, name, surname, patronymic, directorId, userId)
        }
    }

    @Test
    fun `updateEmployee update employee successfully`() {
        val existingEmployee = Employee.create(
            id = employeeId,
            name = name,
            surname = surname,
            patronymic = patronymic,
            directorId = directorId,
            userId = userId
        )
        val updatedEmployee = Employee.create(
            id = employeeId,
            name = EmployeeName("Updated name"),
            surname = surname,
            patronymic = patronymic,
            directorId = directorId,
            userId = userId
        )

        every { employeeRepository.getEmployeeById(employeeId) } returns existingEmployee
        every { employeeRepository.getEmployeeById(employeeId) } returns existingEmployee
        every { employeeRepository.updateEmployee(employeeId, updatedEmployee) } returns updatedEmployee


        val result = employeeService.updateEmployee(employeeId, updatedEmployee)

        assertEquals(employeeId, result)
    }

    @Test
    fun `updateEmployee throw EmployeeNotExist when employee does not exist`() {
        every { employeeRepository.getEmployeeById(employeeId) } returns null

        assertThrows<EmployeeException.EmployeeNotExist> {
            employeeService.updateEmployee(employeeId, mockk())
        }
    }

    @Test
    fun `getAllDirectorsEmployeeSortByRating return employees sorted by rating`() = runBlocking {
        val employee1 = Employee.create(
            id = EmployeeId.generate(),
            name = EmployeeName("Name1"),
            surname = EmployeeSurname("Surname1"),
            patronymic = null,
            directorId = directorId,
            userId = userId
        )
        val employee2 = Employee.create(
            id = EmployeeId.generate(),
            name = name,
            surname = surname,
            patronymic = patronymic,
            directorId = directorId,
            userId = EmployeeUserId(UUID.randomUUID())
        )
        val employees = listOf(employee1, employee2)
        val metricsMap = mapOf(
            employee1 to EmployeeMetrics(5.0, 90.0, 2),
            employee2 to EmployeeMetrics(3.0, 70.0, 5)
        )

        every { employeeRepository.findByDirectorId(directorId) } returns employees
        coEvery { employeeMetricsService.calculateMetricsForEmployees(employees) } returns metricsMap

        val result = employeeService.getAllDirectorsEmployeeSortByRating(directorId)

        assertEquals(2, result.size)
        assertEquals("Name1", result[0].name.value)
        assertEquals("Name", result[1].name.value)
    }

    @Test
    fun `getAllDirectorsEmployeeSortByWorkLoad return employees sorted by workload`() = runBlocking {
        val employee1 = Employee.create(
            id = EmployeeId.generate(),
            name = EmployeeName("Name1"),
            surname = EmployeeSurname("Surname1"),
            patronymic = null,
            directorId = directorId,
            userId = userId
        )
        val employee2 = Employee.create(
            id = EmployeeId.generate(),
            name = name,
            surname = surname,
            patronymic = patronymic,
            directorId = directorId,
            userId = EmployeeUserId(UUID.randomUUID())
        )
        val employees = listOf(employee1, employee2)
        val metricsMap = mapOf(
            employee1 to EmployeeMetrics(4.0, 100.0, 3),
            employee2 to EmployeeMetrics(4.5, 60.0, 4)
        )

        every { employeeRepository.findByDirectorId(directorId) } returns employees
        coEvery { employeeMetricsService.calculateMetricsForEmployees(employees) } returns metricsMap

        val result = employeeService.getAllDirectorsEmployeeSortByWorkLoad(directorId)

        assertEquals(2, result.size)
        assertEquals("Name1", result[0].name.value)
        assertEquals("Name", result[1].name.value)
    }

    @Test
    fun `getAllDirectorsEmployeesWithoutTasks return employees without assigned tasks`() = runBlocking {

        val employeeWithTasks = Employee.create(
            id = EmployeeId.generate(),
            name = EmployeeName("Name1"),
            surname = EmployeeSurname("Surname1"),
            patronymic = null,
            directorId = directorId,
            userId = userId
        )
        val employeeWithoutTasks = Employee.create(
            id = EmployeeId.generate(),
            name = name,
            surname = surname,
            patronymic = patronymic,
            directorId = directorId,
            userId = EmployeeUserId(UUID.randomUUID())
        )
        val employees = listOf(employeeWithTasks, employeeWithoutTasks)
        val metricsMap = mapOf(
            employeeWithTasks to EmployeeMetrics(4.0, 80.0, 2),
            employeeWithoutTasks to EmployeeMetrics(0.0, 0.0, 0)
        )

        every { employeeRepository.findByDirectorId(directorId) } returns employees
        coEvery { employeeMetricsService.calculateMetricsForEmployees(employees) } returns metricsMap

        val result = employeeService.getAllDirectorsEmployeesWithoutTasks(directorId)

        assertEquals(1, result.size)
        assertEquals("Name", result[0].name.value)
    }

    @Test
    fun `getEmployeeWithMetrics return employee with metrics and director details`() = runBlocking {

        val director = Employee.create(
            id = directorId,
            name = EmployeeName("Dir"),
            surname = EmployeeSurname("Dir"),
            patronymic = null,
            directorId = null,
            userId = EmployeeUserId(UUID.randomUUID())
        )
        val employee = Employee.create(
            id = employeeId,
            name = name,
            surname = surname,
            patronymic = patronymic,
            directorId = directorId,
            userId = userId
        )
        val metrics = EmployeeMetrics(4.8, 85.0, 1)
        val directorDetails = DirectorDetails(
            name = director.name,
            surname = director.surname,
            patronymic = director.patronymic
        )

        every { employeeRepository.getEmployeeById(employeeId) } returns employee
        every { employeeRepository.getEmployeeById(directorId) } returns director
        every { employeeMetricsService.calculateMetricsForEmployee(employeeId) } returns metrics


        val result = employeeService.getEmployeeWithMetrics(employeeId)

        assertEquals(employeeId, result.id)
        assertEquals(metrics, result.metrics)
        assertEquals(directorDetails, result.director)
    }

    @Test
    fun `getEmployeeWithMetrics throw FailedToGetDirectorDetailsForEmployee when director details not found`(): Unit = runBlocking {
        val employee = Employee.create(
            id = employeeId,
            name = name,
            surname = surname,
            patronymic = patronymic,
            directorId = directorId,
            userId = userId
        )

        every { employeeRepository.getEmployeeById(employeeId) } returns employee
        every { employeeRepository.getEmployeeById(directorId) } returns null

        every { employeeMetricsService.calculateMetricsForEmployee(employeeId) } returns EmployeeMetrics(0.0, 0.0, 0)

        assertThrows<EmployeeException.FailedToGetDirectorDetailsForEmployee> {
            employeeService.getEmployeeWithMetrics(employeeId)
        }
    }
}