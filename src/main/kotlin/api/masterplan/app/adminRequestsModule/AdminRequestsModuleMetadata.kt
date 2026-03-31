package api.masterplan.app.adminRequestsModule

import org.springframework.modulith.ApplicationModule

@ApplicationModule(allowedDependencies = [
    "apiContracts :: notifications",
    "logging::annotations"
])
class AdminRequestsModuleMetadata {
}