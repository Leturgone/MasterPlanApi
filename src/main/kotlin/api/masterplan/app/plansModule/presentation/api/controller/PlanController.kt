package api.masterplan.app.plansModule.presentation.api.controller

import api.masterplan.app.plansModule.presentation.api.exceptionHandler.PlanControllerExceptionHandler
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.RestController

@RestController
@PlanControllerExceptionHandler
@Tag(name = "Plans and Tasks", description = "Управление планами мероприятий")
class PlanController {
}