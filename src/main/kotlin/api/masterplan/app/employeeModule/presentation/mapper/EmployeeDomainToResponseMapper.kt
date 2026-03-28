package api.masterplan.app.employeeModule.presentation.mapper

import api.masterplan.app.employeeModule.application.dto.FileModel
import api.masterplan.app.employeeModule.domain.dtos.DirectorDetails
import api.masterplan.app.employeeModule.domain.dtos.EmployeeDetails
import api.masterplan.app.employeeModule.domain.dtos.EmployeeWithMetricsDetails
import api.masterplan.app.employeeModule.domain.model.value.EmployeeId
import api.masterplan.app.employeeModule.domain.model.value.EmployeeMetrics
import api.masterplan.app.employeeModule.presentation.dto.responce.*
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

object EmployeeDomainToResponseMapper {

    fun empIdToResponse(domainId: EmployeeId) = EmployeeIdResponse(domainId.value)

    fun empFileToResponse(empFile: FileModel): EmployeeFileResponse{
        val contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        val headers = HttpHeaders()
        val safeFilename = transliterate(empFile.fileName)
        headers.contentType = MediaType.parseMediaType(contentType)
        headers.setContentDispositionFormData("attachment",safeFilename)
        headers.contentLength = empFile.fileData.size.toLong()
        return EmployeeFileResponse(
            fileHeaders = headers,
            fileData = empFile.fileData
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

    private fun transliterate(text: String): String {
        val transliterationMap = hashMapOf(
            'А' to "A", 'Б' to "B", 'В' to "V", 'Г' to "G", 'Д' to "D",
            'Е' to "E", 'Ё' to "Yo", 'Ж' to "Zh", 'З' to "Z", 'И' to "I",
            'Й' to "Y", 'К' to "K", 'Л' to "L", 'М' to "M", 'Н' to "N",
            'О' to "O", 'П' to "P", 'Р' to "R", 'С' to "S", 'Т' to "T",
            'У' to "U", 'Ф' to "F", 'Х' to "Kh", 'Ц' to "Ts", 'Ч' to "Ch",
            'Ш' to "Sh", 'Щ' to "Sh", 'Ы' to "Y", 'Э' to "E",
            'Ю' to "Yu", 'Я' to "Ya",
            'а' to "a", 'б' to "b", 'в' to "v", 'г' to "g", 'д' to "d",
            'е' to "e", 'ё' to "yo", 'ж' to "zh", 'з' to "z", 'и' to "i",
            'й' to "y", 'к' to "k", 'л' to "l", 'м' to "m", 'н' to "n",
            'о' to "o", 'п' to "p", 'р' to "r", 'с' to "s", 'т' to "t",
            'у' to "u", 'ф' to "f", 'х' to "kh", 'ц' to "ts", 'ч' to "ch",
            'ш' to "sh", 'щ' to "sh", 'ы' to "y", 'э' to "e",
            'ю' to "yu", 'я' to "ya"
        )
        return buildString {
            for (char in text){
                this.append(transliterationMap[char]?: char)
            }
        }
    }
}