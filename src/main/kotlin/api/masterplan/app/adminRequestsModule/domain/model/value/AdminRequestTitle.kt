package api.masterplan.app.adminRequestsModule.domain.model.value

import api.masterplan.app.adminRequestsModule.domain.exception.AdminRequestException

class AdminRequestTitle(val value: String) {
    companion object {
        fun validate(title: String):AdminRequestTitle{
            try {
                require(title.isNotEmpty()) { "Title cant be blank" }
                require(title.length <=100){ "Title too long" }
            }catch(e:IllegalStateException){
                throw AdminRequestException.InvalidAdminRequestTitle(e.message)
            }
            val text = title.replace("[^a-zA-Zа-яА-я0-9 ]".toRegex(), "_")
            return AdminRequestTitle(text)
        }
    }
}