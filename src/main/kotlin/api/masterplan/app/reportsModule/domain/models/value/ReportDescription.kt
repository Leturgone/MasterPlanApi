package api.masterplan.app.reportsModule.domain.models.value

import api.masterplan.app.reportsModule.domain.exceptions.ReportException

@JvmInline
value class ReportDescription(val value: String) {
    fun validate(desc: String): ReportDescription {
        try {
            require(desc.isNotEmpty()) { "Description cant be blank" }
            require(desc.length <=255){ "Description too long" }
        }catch(e:IllegalStateException){
            throw ReportException.InvalidReportDescription(e.message)
        }
        return ReportDescription(desc)
    }
}