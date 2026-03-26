package api.masterplan.app.filesModule

import org.springframework.modulith.ApplicationModule

@ApplicationModule(allowedDependencies = [
    "apiContracts :: files",
    "logging::annotations",
    "export :: *"
])
class FilesModuleMetadata {
}