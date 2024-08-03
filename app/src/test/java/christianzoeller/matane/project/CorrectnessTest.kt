package christianzoeller.matane.project

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import christianzoeller.matane.project.extensions.composableFunctions
import christianzoeller.matane.project.extensions.previewComposableFunctions
import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.withNameEndingWith
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.Test

class CorrectnessTest {
    @Test
    fun `screen content composables do not have a modifier parameter but accept content padding`() {
        Konsist.scopeFromProject()
            .composableFunctions()
            .withNameEndingWith(screenContentComposableSuffix)
            .assertTrue { function ->
                val hasContentPaddingParameter = function.hasParameter { parameter ->
                    parameter.hasTypeOf(PaddingValues::class) &&
                            parameter.name == contentPaddingParameterName &&
                            !parameter.hasDefaultValue()
                }

                val hasModifierParameter = function.hasParameter { parameter ->
                    parameter.hasTypeOf(Modifier::class)
                }

                hasContentPaddingParameter && !hasModifierParameter
            }
    }

    @Test
    fun `preview composables are private`() {
        Konsist.scopeFromProject()
            .previewComposableFunctions()
            .assertTrue { function -> function.hasPrivateModifier }
    }
}