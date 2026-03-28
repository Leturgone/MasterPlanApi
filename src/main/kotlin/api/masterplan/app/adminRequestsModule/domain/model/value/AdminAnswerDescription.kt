package api.masterplan.app.adminRequestsModule.domain.model.value

import api.masterplan.app.adminRequestsModule.domain.exception.AdminRequestException

class AdminAnswerDescription(val value: String) {
    companion object {
        fun validate(desc: String):AdminAnswerDescription{
            try {
                require(desc.isNotEmpty()) { "Description cant be blank" }
                require(desc.length <=255){ "Description too long" }
            }catch(e:IllegalStateException){
                throw AdminRequestException.InvalidAdminAnswerDesc(e.message)
            }
            return AdminAnswerDescription(desc)
        }
    }
}