package api.masterplan.app.adminRequestsModule.domain.model.value

import api.masterplan.app.adminRequestsModule.domain.exception.AdminRequestException

class AdminRequestDescription(val value: String) {
    companion object {
        fun validate(desc: String):AdminRequestDescription{
            try {
                require(desc.isNotEmpty()) { "Description cant be blank" }
                require(desc.length <=255){ "Description too long" }
            }catch(e:IllegalStateException){
                throw AdminRequestException.InvalidAdminRequestDesc(e.message)
            }
            val text = desc.replace("[^a-zA-Zа-яА-я0-9 ]".toRegex(), "_")
            return AdminRequestDescription(text)
        }
    }
}