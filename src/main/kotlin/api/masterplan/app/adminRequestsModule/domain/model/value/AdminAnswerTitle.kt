package api.masterplan.app.adminRequestsModule.domain.model.value

import api.masterplan.app.adminRequestsModule.domain.exception.AdminRequestException

class AdminAnswerTitle(val value: String) {
    companion object {
        fun validate(title: String):AdminAnswerTitle{
            try {
                require(title.isNotEmpty()) { "Title cant be blank" }
                require(title.length <=100){ "Title too long" }
            }catch(e:IllegalStateException){
                throw AdminRequestException.InvalidAdminAnswerTitle(e.message)
            }
            val text = title.replace("[^a-zA-Zа-яА-я0-9 ]".toRegex(), "_")
            return AdminAnswerTitle(text)
        }
    }
}