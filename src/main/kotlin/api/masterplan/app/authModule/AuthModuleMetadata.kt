package api.masterplan.app.authModule

import org.springframework.modulith.ApplicationModule

@ApplicationModule(allowedDependencies = [
    "apiContracts :: userManagement",
    "logging::annotations"
])
class AuthModuleMetadata {
}