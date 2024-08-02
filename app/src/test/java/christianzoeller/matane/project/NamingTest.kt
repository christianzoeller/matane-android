package christianzoeller.matane.project

import androidx.lifecycle.ViewModel
import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.withAllParentsOf
import com.lemonappdev.konsist.api.ext.list.withAnnotation
import com.lemonappdev.konsist.api.ext.list.withPackage
import com.lemonappdev.konsist.api.ext.list.withParameter
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.Test

class NamingTest {
    @Test
    fun `top-level screen composables have correct suffix`() {
        // Here, we define top-level screen composables as those
        // composables that have a view model parameter
        Konsist.scopeFromProject()
            .functions()
            .withAnnotation { annotation -> annotation.name == "Composable" }
            .withParameter { parameter ->
                parameter.hasType { type -> type.hasNameEndingWith(viewModelSuffix) }
            }
            .assertTrue { it.name.endsWith(topLevelScreenComposableSuffix) }
    }

    @Test
    fun `view models have correct suffix`() {
        Konsist.scopeFromProject()
            .classes()
            .withAllParentsOf(ViewModel::class)
            .assertTrue { it.name.endsWith(viewModelSuffix) }
    }

    @Test
    fun `repositories have correct suffix`() {
        Konsist.scopeFromProject()
            .classes()
            .withPackage(repositoryPackage)
            .assertTrue { it.name.endsWith(repositorySuffix) }
    }
}