package christianzoeller.matane.project.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import christianzoeller.matane.ui.tooling.CompactPreview
import christianzoeller.matane.ui.tooling.ExpandedPreview
import christianzoeller.matane.ui.tooling.MediumPreview
import com.lemonappdev.konsist.api.container.KoScope
import com.lemonappdev.konsist.api.ext.list.withAnnotation

fun KoScope.composableFunctions() =
    functions().withAnnotation { annotation ->
        annotation.name == Composable::class.simpleName
    }

fun KoScope.previewComposableFunctions() =
    composableFunctions().withAnnotation { annotation ->
        previewAnnotations.any { it == annotation.name }
    }

private val previewAnnotations = listOf(
    Preview::class.simpleName,
    CompactPreview::class.simpleName,
    MediumPreview::class.simpleName,
    ExpandedPreview::class.simpleName
)