package api.masterplan.app.plansModule.presentation.mapper

import api.masterplan.app.plansModule.application.dto.PlanExportFile
import api.masterplan.app.plansModule.domain.dtos.PlanDetails
import api.masterplan.app.plansModule.domain.dtos.TaskDetails
import api.masterplan.app.plansModule.domain.model.value.PlanId
import api.masterplan.app.plansModule.domain.model.value.TaskId
import api.masterplan.app.plansModule.presentation.dto.response.*
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

object PlanDomainToResponseMapper {

    fun toResponse(plan: PlanDetails): PlanInformationResponse {
        return PlanInformationResponse(
            id = plan.id.value,
            title = plan.title.value,
            description = plan.description.value,
            startDate = plan.startDate.value,
            endDate = plan.endDate.value,
            status = plan.status.name,
            directorId = plan.directorId?.value,
            documentId = plan.documentId?.value,
        )
    }


    @JvmName("toPlanResponse")
    fun toResponse(planList: List<PlanDetails>): List<PlanInformationResponse> {
        return planList.map { toResponse(it) }
    }

    fun toResponse(task: TaskDetails): TaskInformationResponse {
        return TaskInformationResponse(
            id = task.id.value,
            title = task.title.value,
            description = task.description.value,
            endDate = task.endDate.value,
            status = task.status.name,
            planId = task.planId.value,
            documentId = task.documentId?.value,
            urgency = task.urgency.value,
            executorsIds = task.executorsIds.map { it.value },
        )
    }


    @JvmName("toTaskResponse")
    fun toResponse(taskList: List<TaskDetails>): List<TaskInformationResponse> {
        return taskList.map { toResponse(it) }
    }

    fun toResponse(taskId: TaskId) = TaskIdResponse(taskId.value)

    fun toResponse(planId: PlanId) = PlanIdResponse(planId.value)

    fun toResponse(exportFile: PlanExportFile): ExportPlanResponse {
        val contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"

        val headers = HttpHeaders()
        val safeFilename = transliterate(exportFile.fileName)
        headers.contentType = MediaType.parseMediaType(contentType)
        headers.setContentDispositionFormData("attachment",safeFilename)
        headers.contentLength = exportFile.fileData.size.toLong()

        return ExportPlanResponse(
            fileHeaders = headers,
            fileData = exportFile.fileData
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