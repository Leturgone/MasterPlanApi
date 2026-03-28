package api.masterplan.app.adminRequestsModule.domain.model.value

import api.masterplan.app.adminRequestsModule.domain.exception.AdminRequestException

class AdminAnswerTitle(val value: String) {
    companion object {
        fun validate(desc: String):AdminAnswerDescription{
            try {
                require(desc.isNotEmpty()) { "Title cant be blank" }
                require(desc.length <=100){ "Title too long" }
            }catch(e:IllegalStateException){
                throw AdminRequestException.InvalidAdminAnswerTitle(e.message)
            }
            return AdminAnswerDescription(desc)
        }
    }
}