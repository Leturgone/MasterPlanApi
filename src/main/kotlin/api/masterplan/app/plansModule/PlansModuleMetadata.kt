package api.masterplan.app.plansModule

import org.springframework.modulith.ApplicationModule

@ApplicationModule(allowedDependencies = [
    "apiContracts :: plans",
    "apiContracts :: employee",
    "apiContracts :: files",
    "apiContracts :: notifications",
    "logging :: annotations",
    "export :: annotations"
])
class PlansModuleMetadata {
}