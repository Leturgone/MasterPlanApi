package api.masterplan.app.plansModule.domain.model.value

import api.masterplan.app.plansModule.domain.exceptions.PlanException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@JvmInline
value class TaskUrgency private constructor(val value: Double) {
    companion object {

        fun validate(value: Double): TaskUrgency {
            try {
                require(value in 0.0..1.0){
                    "Value must be between 0.0 and 1.0 inclusive"
                }
            }catch (e : IllegalArgumentException){
                PlanException.InvalidTaskUrgency(e.message)
            }
            return TaskUrgency(value)
        }

        fun calculate(deadline: LocalDate): TaskUrgency {
            val daysUntil = ChronoUnit.DAYS.between(
                LocalDate.now(),
                deadline
            )
            val value= when{
                daysUntil < 0 -> 10.0  // Пропущенный дедлайн
                daysUntil == 0L -> 10.0   // Выполнить сегодня
                daysUntil <= 3 -> 9.0    // Ближайшие 3 дня
                daysUntil <= 14 -> 7.0
                daysUntil <= 30 -> 5.0
                daysUntil <= 60 -> 3.0
                daysUntil <= 90 -> 2.0
                else -> 1.0
            }
            return TaskUrgency(value)
        }
    }
}