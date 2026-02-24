package api.masterplan.app.plansModule.domain.model.value

import api.masterplan.app.plansModule.domain.exceptions.PlanException

@JvmInline
value class TaskDescription(val value: String) {
    companion object {
        fun validate(desc: String):TaskDescription {
            try {
                require(desc.isNotEmpty()) { "Description cant be blank" }
                require(desc.length <=255){ "Description too long" }
            }catch(e:IllegalStateException){
                throw PlanException.InvalidPlanDesc(e.message)
            }
            return TaskDescription(desc)
        }
    }
}