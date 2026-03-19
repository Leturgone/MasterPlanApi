package api.masterplan.app.reportsModule.presentation.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

data class UpdateReportStatusRequest(
    @NotBlank(message = "report status cant be blank")
    @Schema(description = "Статус отчета", example = "CHECKED")
    val status: String
)
