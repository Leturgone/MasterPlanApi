package api.masterplan.app.plansModule.domain.exceptions

sealed class PlanException(message: String): Exception(message){

    class InvalidTaskTitle(message: String?) : PlanException(
        "Invalid task title: ${message?.let {": $it"  }}"
    )

    class InvalidPlanTitle(message: String?) : PlanException(
        "Invalid plan title: ${message?.let {": $it"  }}"
    )

    class InvalidTaskDesc(message: String?) : PlanException(
        "Invalid task description: ${message?.let {": $it"  }}"
    )

    class InvalidPlanDesc(message: String?) : PlanException(
        "Invalid plan description: ${message?.let {": $it"  }}"
    )

}