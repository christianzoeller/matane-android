#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}

#end
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
#if (${VIEWMODEL})
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
#end
import christianzoeller.matane.R
import christianzoeller.matane.styleguide.components.DefaultTopAppBar
import christianzoeller.matane.ui.theme.MataneTheme
import christianzoeller.matane.ui.tooling.CompactPreview
#if (${CONTENT})
import ${PACKAGE_NAME}.ui.${CONTENT}
#end

#parse("File Header.java")
@Composable
fun ${NAME}Screen(
    #if (${VIEWMODEL})
    viewModel: ${VIEWMODEL},
    #end
    onNavigateUp: () -> Unit
) {
    #if (${STATE})
    val state by viewModel.state.collectAsStateWithLifecycle()
    
    #end
    Screen(
        #if (${STATE})
        state = state,
        #end
        onNavigateUp = onNavigateUp
    )
}

@Composable
private fun Screen(
    #if (${STATE})
    state: ${STATE},
    #end
    onNavigateUp: () -> Unit
) {
    Column {
        DefaultTopAppBar(
            onNavigateUp = onNavigateUp,
            title = TODO()
        )

        #if (${STATE})
        when (state) {
            is ${STATE}.Loading -> TODO()

            is ${STATE}.Content -> {
                ${CONTENT}(
                    data = state
                )
            }

            is ${STATE}.Error -> TODO()
        }
        #elseif (${CONTENT})
        ${CONTENT}(
            data = TODO()
        )
        #end
    }
}

#if (${STATE})
@CompactPreview
@Composable
private fun ${NAME}Screen_Loading_Preview() = MataneTheme {
    Screen(
        state = ${STATE}.Loading,
        onNavigateUp = {}
    )
}

@CompactPreview
@Composable
private fun ${NAME}Screen_Content_Preview() = MataneTheme {
    Screen(
        state = ${STATE}.Content(
            data = TODO()
        ),
        onNavigateUp = {}
    )
}

@CompactPreview
@Composable
private fun ${NAME}Screen_Error_Preview() = MataneTheme {
    Screen(
        state = ${STATE}.Error,
        onNavigateUp = {}
    )
}
#else
@CompactPreview
@Composable
private fun ${NAME}Screen_Preview() = MataneTheme {
    Screen(
        onNavigateUp = {}
    )
}
#end