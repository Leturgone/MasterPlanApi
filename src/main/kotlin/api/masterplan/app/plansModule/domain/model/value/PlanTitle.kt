package api.masterplan.app.plansModule.domain.model.value

import api.masterplan.app.plansModule.domain.exceptions.PlanException

@JvmInline
value class PlanTitle(val value: String)  {
    companion object {
        fun validate(title: String): PlanTitle {
            try {
                require(title.isNotBlank()) { "Title cant be blank" }
                require(title.length <= 100) { "Title too long" }
            } catch (e: IllegalArgumentException) {
                throw PlanException.InvalidPlanTitle(e.message)
            }
            return PlanTitle(title)
        }
    }
}