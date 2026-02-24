package api.masterplan.app.employeeModule.presentation.mapper

import api.masterplan.app.employeeModule.application.dto.FileModel
import api.masterplan.app.employeeModule.domain.dtos.DirectorDetails
import api.masterplan.app.employeeModule.domain.dtos.EmployeeDetails
import api.masterplan.app.employeeModule.domain.dtos.EmployeeWithMetricsDetails
import api.masterplan.app.employeeModule.domain.model.value.EmployeeId
import api.masterplan.app.employeeModule.domain.model.value.EmployeeMetrics
import api.masterplan.app.employeeModule.presentation.dto.responce.DirectorDetailsDto
import api.masterplan.app.employeeModule.presentation.dto.responce.EmployeeDetailsResponse
import api.masterplan.app.employeeModule.presentation.dto.responce.EmployeeFileResponse
import api.masterplan.app.employeeModule.presentation.dto.responce.EmployeeIdResponse
import api.masterplan.app.employeeModule.presentation.dto.responce.EmployeeMetricsDto
import api.masterplan.app.employeeModule.presentation.dto.responce.EmployeeWithMetricsDetailsResponse

object EmployeeDomainToResponseMapper {

    fun empIdToResponse(domainId: EmployeeId) = EmployeeIdResponse(domainId.value)

    fun empFileToResponse(empFile: FileModel): EmployeeFileResponse{
        return EmployeeFileResponse(
            fileData = empFile.fileData,
            fileName = empFile.fileName
        )
    }

    fun empDetailsListToResponse(list:List<EmployeeDetails>):List<EmployeeDetailsResponse> {
        return list.map { empDetailsToResponse(it) }
    }

    fun empDetailsToResponse(empDetails: EmployeeDetails): EmployeeDetailsResponse {
        return EmployeeDetailsResponse(
            id = empDetails.id.value,
            name = empDetails.name.value,
            surname = empDetails.surname.value,
            patronymic = empDetails.patronymic?.value,
            directorId = empDetails.directorId?.value,
            userId = empDetails.userId.value
        )
    }


    fun empMetricsDetailsToResponse(empDetails: EmployeeWithMetricsDetails): EmployeeWithMetricsDetailsResponse {
        return EmployeeWithMetricsDetailsResponse(
            id = empDetails.id.value,
            name = empDetails.name.value,
            surname = empDetails.surname.value,
            patronymic = empDetails.patronymic?.value,
            director = dirDetailsToResponse(empDetails.director),
            metrics = metricsToResponse(empDetails.metrics),
        )
    }

    private fun dirDetailsToResponse(dir: DirectorDetails? = null): DirectorDetailsDto?{
        if(dir == null){return null}
        return DirectorDetailsDto(
            name = dir.name.value,
            surname = dir.surname.value,
            patronymic = dir.patronymic?.value,
        )
    }

    private fun metricsToResponse(metrics: EmployeeMetrics): EmployeeMetricsDto {
        return EmployeeMetricsDto(
            rating = metrics.rating,
            workload = metrics.workload,
            assignedTasksCount = metrics.assignedTasksCount,
        )
    }
}