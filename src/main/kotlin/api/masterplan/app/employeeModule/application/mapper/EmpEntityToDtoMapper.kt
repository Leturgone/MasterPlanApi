package api.masterplan.app.employeeModule.application.mapper

import api.masterplan.app.employeeModule.domain.dtos.DirectorDetails
import api.masterplan.app.employeeModule.domain.dtos.EmployeeDetails
import api.masterplan.app.employeeModule.domain.dtos.EmployeeWithMetricsDetails
import api.masterplan.app.employeeModule.domain.model.entity.Employee
import api.masterplan.app.employeeModule.domain.model.value.EmployeeMetrics

object EmpEntityToDtoMapper {
    fun toEmployeeDetails(entity: Employee): EmployeeDetails{
        return EmployeeDetails(
            id = entity.id,
            name = entity.name,
            surname = entity.surname,
            patronymic = entity.patronymic,
            directorId = entity.directorId,
            userId = entity.userId
        )
    }

    fun toEmployeeWithMetricsDetails(entity: Employee,
                                     directorDetails: DirectorDetails? = null,
                                     metrics: EmployeeMetrics
                                     ): EmployeeWithMetricsDetails {
        return EmployeeWithMetricsDetails(
            id = entity.id,
            name = entity.name,
            surname = entity.surname,
            patronymic = entity.patronymic,
            director = directorDetails,
            metrics = metrics
        )
    }
}