package api.masterplan.app.employeeModule.application.service

import api.masterplan.app.employeeModule.application.dto.EmpTaskStatus
import api.masterplan.app.employeeModule.application.ports.TaskInfProvider
import api.masterplan.app.employeeModule.domain.interfaces.EmployeeMetricsService
import api.masterplan.app.employeeModule.domain.model.value.EmployeeId
import api.masterplan.app.employeeModule.domain.model.value.EmployeeMetrics
import org.springframework.stereotype.Service

@Service
class EmployeeMetricsServiceImpl(
    private val taskInfProvider: TaskInfProvider
) : EmployeeMetricsService {
    override fun calculateMetrics(employeeId: EmployeeId): EmployeeMetrics {
        val tasks = taskInfProvider.getTasksByEmployeeId(employeeId)

        if (tasks.isEmpty()) {
            return EmployeeMetrics(0.0,0.0,0)
        }

        val tasksCount = tasks.size
        val completedTasks = tasks.filter { it.status == EmpTaskStatus.COMPLETED }
        val completedTasksCount = completedTasks.size

        val sumWeightTotal = tasks.sumOf { it.weight }
        val sumWeightCompleted = completedTasks.sumOf { it.weight }

        // Рассчет рейтинга

        val rating = if (sumWeightTotal > 0 && completedTasksCount >0){
            (sumWeightCompleted / sumWeightTotal) * tasksCount
        }else 0.0

        // Рассчет загруженности
        val workload = tasks.asSequence().filter { it.status != EmpTaskStatus.COMPLETED }.sumOf { it.weight }

        val assignedTasksCount = tasksCount - completedTasksCount

        return EmployeeMetrics(rating, workload, assignedTasksCount)
    }
}