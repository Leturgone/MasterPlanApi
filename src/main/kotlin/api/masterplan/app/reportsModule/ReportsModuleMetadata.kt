package api.masterplan.app.reportsModule

import org.springframework.modulith.ApplicationModule

@ApplicationModule(allowedDependencies = [
    "apiContracts :: employee",
    "apiContracts :: files",
    "apiContracts :: notifications",
    "logging::annotations"
])
class ReportsModuleMetadata {
}