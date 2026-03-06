package api.masterplan.app.employeeModule.application.exceptions

class EmpTaskException(message: String) : Exception(message) {

    class InvalidStatusException(status: String?) : Exception(
        "Invalid employee task status: ${status?.let {": $it"  }}"
    )
}