package api.masterplan.app.notification

import org.springframework.modulith.ApplicationModule

@ApplicationModule(allowedDependencies = [
    "apiContracts :: notifications",
    "logging::annotations"
])
class NotificationModuleMetadata {
}