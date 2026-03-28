package api.masterplan.app.adminRequestsModule.domain.model.value

import api.masterplan.app.adminRequestsModule.domain.exception.AdminRequestException

class AdminRequestTitle(val value: String) {
    companion object {
        fun validate(desc: String):AdminRequestTitle{
            try {
                require(desc.isNotEmpty()) { "Title cant be blank" }
                require(desc.length <=100){ "Title too long" }
            }catch(e:IllegalStateException){
                throw AdminRequestException.InvalidAdminRequestTitle(e.message)
            }
            return AdminRequestTitle(desc)
        }
    }
}