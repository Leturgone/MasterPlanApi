package api.masterplan.app.employeeModule.infrastructure.database.repository

import api.masterplan.app.employeeModule.domain.interfaces.EmployeeRepository
import api.masterplan.app.employeeModule.domain.model.entity.Employee
import api.masterplan.app.employeeModule.domain.model.value.EmployeeId
import api.masterplan.app.employeeModule.domain.model.value.EmployeeUserId
import api.masterplan.app.employeeModule.infrastructure.database.mapper.EmployeeDatabaseMapper
import org.springframework.stereotype.Repository
import kotlin.jvm.optionals.getOrElse

@Repository
class EmployeeRepositoryImpl(
    private val jpaEmployeeRepository: JpaEmployeeRepository
): EmployeeRepository {
    override fun getAllEmployees(): List<Employee> {
        val employeeList = jpaEmployeeRepository.findAll()
        return employeeList.map { EmployeeDatabaseMapper.toDomain(it) }

    }

    override fun getEmployeeById(employeeId: EmployeeId): Employee? {
        val employee = jpaEmployeeRepository.findById(employeeId.value).getOrElse { return null }
        return EmployeeDatabaseMapper.toDomain(employee)
    }

    override fun saveEmployee(employee: Employee): EmployeeId {
        val employeeEntity = EmployeeDatabaseMapper.toEntity(employee)
        val savedEmployee = jpaEmployeeRepository.save(employeeEntity)
        return EmployeeId(savedEmployee.id)
    }

    override fun searchByNameOrSurname(query: String): List<Employee> {
        val nameNSurname = query.split(' ')
        val name = nameNSurname.first()
        val surname = nameNSurname.last()
        val employeeList = jpaEmployeeRepository.searchByNameOrSurname(name, surname)
        return employeeList.map { EmployeeDatabaseMapper.toDomain(it) }
    }

    override fun searchByNameOrSurnameAndDirId(query: String, directorId: EmployeeId): List<Employee> {
        val nameNSurname = query.split(' ')
        val name = nameNSurname.first()
        val surname = nameNSurname.last()
        val employeeList = jpaEmployeeRepository.searchByNameOrSurnameAndDirId(name, surname,directorId.value)
        return employeeList.map { EmployeeDatabaseMapper.toDomain(it) }
    }

    override fun findByDirectorId(directorId: EmployeeId): List<Employee> {
        val directorEmployeeList = jpaEmployeeRepository.getByDirectorId(directorId.value)
        return directorEmployeeList.map { EmployeeDatabaseMapper.toDomain(it) }
    }

    override fun isEmployeeExist(userId: EmployeeUserId): Boolean {
        return jpaEmployeeRepository.existsByUserId(userId.value)
    }

    override fun updateEmployee(id: EmployeeId, newEmployee: Employee): Employee {
        val newEmployeeEntity = EmployeeDatabaseMapper.toEntity(newEmployee)
        val updatedEmployee = jpaEmployeeRepository.save(newEmployeeEntity)
        return EmployeeDatabaseMapper.toDomain(updatedEmployee)
    }

}