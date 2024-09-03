package christianzoeller.matane.project

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
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

    /**
     * List-detail views have to handle back navigation in a special way. For that,
     * a [ThreePaneScaffoldNavigator] is necessary. If the list-detail view creates
     * this navigator itself, a top bar on screen-level with a back navigation icon
     * cannot replicate this specialized behaviour.
     *
     * Therefore, this test ensures that such Composables have a parameter of type
     * [ThreePaneScaffoldNavigator] that the parent screen Composable can access.
     */
    @OptIn(ExperimentalMaterial3AdaptiveApi::class)
    @Test
    fun `list-detail screen content composables have a navigator parameter`() {
        Konsist.scopeFromProject()
            .composableFunctions()
            .withNameEndingWith(listDetailScreenContentComposableSuffix)
            .assertTrue { function ->
                function.hasParameter { parameter ->
                    parameter.hasTypeOf(ThreePaneScaffoldNavigator::class)
                }
            }
    }

    /**
     * The list views in list-detail views might leave the composition tree if
     * the user navigates to a detail view and the list and detail views are on
     * separate screens due to the form factor of the device.
     *
     * That is why it is crucial that the list state of the lists are created
     * high enough in the tree to ensure that they are retained even if the
     * list leaves composition. Otherwise, the list will be back at the top if
     * the user navigates back to it.
     *
     * This test attempts to enforce correct handling of list state.
     */
    @Test
    fun `lists composables used in list-detail screens take a state parameter`() {
        Konsist.scopeFromProject()
            .composableFunctions()
            .withNameEndingWith(listDetailScreenContentComposableListViewSuffix)
            .assertTrue { function ->
                function.hasParameter { parameter ->
                    parameter.hasTypeOf(LazyListState::class)
                }
            }
    }
}