package api.masterplan.app.employeeModule.infrastructure.database.mapper

import api.masterplan.app.employeeModule.domain.model.entity.Employee
import api.masterplan.app.employeeModule.domain.model.value.EmployeeId
import api.masterplan.app.employeeModule.domain.model.value.EmployeeName
import api.masterplan.app.employeeModule.domain.model.value.EmployeePatronymic
import api.masterplan.app.employeeModule.domain.model.value.EmployeeSurname
import api.masterplan.app.employeeModule.domain.model.value.EmployeeUserId
import api.masterplan.app.employeeModule.infrastructure.database.entity.EmployeeEntity

object EmployeeDatabaseMapper {

    fun toDomain(employeeEntity: EmployeeEntity): Employee{
        return Employee.create(
            id = EmployeeId(employeeEntity.id),
            name = EmployeeName.validate(employeeEntity.name),
            surname = EmployeeSurname.validate(employeeEntity.surname),
            patronymic = employeeEntity.patronymic?.let { EmployeePatronymic.validate(it) },
            directorId = employeeEntity.director?.let { EmployeeId(it.id) },
            userId = EmployeeUserId(employeeEntity.appUserId),
        )
    }
}