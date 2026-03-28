package api.masterplan.app.employeeModule

import org.springframework.modulith.ApplicationModule

@ApplicationModule(allowedDependencies = [
    "apiContracts :: files",
    "apiContracts :: plans",
    "apiContracts :: employee",
    "logging::annotations",
    "export :: annotations",
])
class EmployeeModuleMetadata {
}