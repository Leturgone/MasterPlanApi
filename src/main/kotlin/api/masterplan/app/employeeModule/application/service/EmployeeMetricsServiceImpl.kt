package api.masterplan.app.employeeModule.application.service

import api.masterplan.app.employeeModule.application.dto.EmpTaskModel
import api.masterplan.app.employeeModule.application.dto.EmpTaskStatus
import api.masterplan.app.employeeModule.application.ports.TaskInfProvider
import api.masterplan.app.employeeModule.domain.interfaces.EmployeeMetricsService
import api.masterplan.app.employeeModule.domain.model.entity.Employee
import api.masterplan.app.employeeModule.domain.model.value.EmployeeId
import api.masterplan.app.employeeModule.domain.model.value.EmployeeMetrics
import api.masterplan.app.logging.LoggingMethod
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Service

@Service
class EmployeeMetricsServiceImpl(
    private val taskInfProvider: TaskInfProvider
) : EmployeeMetricsService {

    @LoggingMethod("employeeModule")
    override fun calculateMetricsForEmployee(employeeId: EmployeeId): EmployeeMetrics {
        val tasks = taskInfProvider.getTasksByEmployeeId(employeeId)
        return calculateMetricsForTasks(tasks)
    }

    @LoggingMethod("employeeModule")
    override suspend fun calculateMetricsForEmployees(employees: List<Employee>): Map<Employee, EmployeeMetrics>  = coroutineScope {
        val employeesIds = employees.map { it.id }.toSet()
        val tasks = taskInfProvider.getTasksByEmployeeIds(employeesIds)
        val tasksByEmployee = tasks.flatMap {task ->
            // flatMap для добавления в общий список
            // делаем список для каждой задачи empId -> задача
            task.employeeIds.map{ employeeId ->
                employeeId to task
            }
        }.groupBy(
            keySelector = { it.first },
            valueTransform = { it.second }
        )

        val defResult = employees.map{ employee ->
            async{
                val empTasks = tasksByEmployee[employee.id] ?: emptyList()
                employee to calculateMetricsForTasks(empTasks)
            }
        }

        defResult.awaitAll().toMap()
    }



    private fun calculateMetricsForTasks(tasks: List<EmpTaskModel>):EmployeeMetrics {
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