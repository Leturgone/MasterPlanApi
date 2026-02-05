package api.masterplan.app

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.modulith.core.ApplicationModules


@OpenAPIDefinition(
	info = Info(
		title = "Masterplan API",
		version = "1.0",
		description = "API документация для управления планами мероприятий"
	)
)
@SpringBootApplication
class MasterPlanApiApplication

fun main(args: Array<String>) {
	val modules: ApplicationModules = ApplicationModules.of(MasterPlanApiApplication::class.java)
	modules.verify()
	runApplication<MasterPlanApiApplication>(*args)
}
