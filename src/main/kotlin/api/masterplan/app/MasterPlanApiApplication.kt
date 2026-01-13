package api.masterplan.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MasterPlanApiApplication

fun main(args: Array<String>) {
	runApplication<MasterPlanApiApplication>(*args)
}
