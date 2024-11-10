package christianzoeller.matane.project.extensions

import com.lemonappdev.konsist.api.Konsist

fun Konsist.defaultScope() =
    this.scopeFromProduction()
        .slice { file -> !file.path.contains(".idea") }

