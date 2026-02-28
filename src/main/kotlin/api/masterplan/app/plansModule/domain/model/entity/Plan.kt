package api.masterplan.app.plansModule.domain.model.entity

import api.masterplan.app.plansModule.domain.model.value.*
import java.time.LocalDate

@ConsistentCopyVisibility
data class Plan private constructor(
    val id: PlanId,
    val title: PlanTitle,
    val description: PlanDescription,
    val startDate: PlanDate,
    val endDate: PlanDate,
    val status: PlanStatus,
    val directorId: PlanDirectorId? = null,
    val documentId: PlanDocumentId? = null
){
    companion object{
        fun create(id: PlanId? = null, title: PlanTitle, description: PlanDescription, startDate: PlanDate? = null,
                   endDate: PlanDate, directorId: PlanDirectorId,
                   documentId: PlanDocumentId? = null): Plan{
            return Plan(
                id = id ?: PlanId.generate(),
                title = title,
                description = description,
                startDate = startDate?: PlanDate(LocalDate.now()),
                endDate = endDate,
                status = PlanStatus.NOT_STARTED,
                directorId = directorId,
                documentId = documentId,
            )
        }
    }

    fun addDocument(documentId: PlanDocumentId): Plan{
        return this.copy(documentId = documentId)
    }

    fun changePlanStatus(planStatus: PlanStatus): Plan{
        return this.copy(status=planStatus)
    }

}
