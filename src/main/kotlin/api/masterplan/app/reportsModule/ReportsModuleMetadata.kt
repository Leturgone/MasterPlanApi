package api.masterplan.app.reportsModule

import org.springframework.modulith.ApplicationModule

@ApplicationModule(allowedDependencies = [
    "apiContracts :: employee",
    "apiContracts :: files",
    "logging::annotations"
])
class ReportsModuleMetadata {
}