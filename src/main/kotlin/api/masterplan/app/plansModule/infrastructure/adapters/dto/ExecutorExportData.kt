package api.masterplan.app.plansModule.infrastructure.adapters.dto

data class ExecutorExportData(
    val name: String,
    val surname: String,
    val patronymic: String? = null
){
    override fun toString(): String {
        return "$name $surname ${patronymic?: ""}"
    }
}