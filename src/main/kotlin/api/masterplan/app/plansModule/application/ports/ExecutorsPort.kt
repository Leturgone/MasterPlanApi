package api.masterplan.app.plansModule.application.ports

import api.masterplan.app.plansModule.application.dto.ExecutorDto
import api.masterplan.app.plansModule.domain.dtos.TaskDetails

interface ExecutorsPort {
    fun getExecutorsForTasks(tasks: List<TaskDetails>): List<ExecutorDto>
}