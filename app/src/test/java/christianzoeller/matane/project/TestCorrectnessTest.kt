package christianzoeller.matane.project

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.Test

class TestCorrectnessTest {
    @Test
    fun `Konsist tests use default konsist scope`() {
        Konsist.scopeFromTest()
            .slice { file -> !file.path.contains("TestConventionsTest") }
            .functions()
            .filter { function ->
                function.text.contains("Konsist")
            }
            .assertTrue { function ->
                function.text.contains(".defaultScope()")
            }
    }
}