package christianzoeller.matane.styleguide.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import christianzoeller.matane.ui.tooling.CompactPreview
import christianzoeller.matane.ui.tooling.MatanePreview
import com.google.firebase.Firebase
import com.google.firebase.crashlytics.crashlytics

@Composable
fun TextLink(
    text: String,
    url: String,
    modifier: Modifier = Modifier,
    style: TextStyle = typography.bodyMedium
) {
    val context = LocalContext.current

    Text(
        text = text,
        modifier = modifier.clickable {
            context.openInExternalBrowser(url)
        },
        textDecoration = TextDecoration.Underline,
        style = style
    )
}

@CompactPreview
@Composable
private fun TextLink_Preview() = MatanePreview {
    TextLink(
        text = "Click on this link",
        url = Uri.EMPTY.toString(),
        modifier = Modifier.padding(16.dp)
    )
}

private fun Context.openInExternalBrowser(link: String) {
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
    try {
        startActivity(browserIntent)
    } catch (e: Exception) {
        Firebase.crashlytics.recordException(e)
    }
}