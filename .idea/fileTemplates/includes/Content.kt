#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}

#end
import androidx.compose.runtime.Composable
import christianzoeller.matane.ui.tooling.CompactPreview
import christianzoeller.matane.ui.tooling.MatanePreview
#if (${STATE})
import ${STATE_PACKAGE_NAME}.${STATE}
#end

#parse("File Header.java")
@Composable
fun ${CONTENT}(
    #if (${STATE})
    data: ${STATE}.Content
    #else
    data: Unit
    #end
) {

}

@CompactPreview
@Composable
private fun ${CONTENT}_Preview() = MatanePreview {
    ${CONTENT}(
        #if (${STATE})
        data = ${STATE}.Content(
            data = TODO()
        )
        #else
        data = TODO()
        #end
    )
}