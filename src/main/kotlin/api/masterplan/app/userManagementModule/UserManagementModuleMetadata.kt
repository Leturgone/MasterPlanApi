package api.masterplan.app.userManagementModule

import org.springframework.modulith.ApplicationModule

@ApplicationModule(allowedDependencies = [
    "apiContracts :: employee",
    "apiContracts :: userManagement",
    "logging::annotations"
])
class UserManagementModuleMetadata {
}