package christianzoeller.matane.ui.extensions

import androidx.compose.foundation.ScrollState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun ScrollState.scrollToTop(coroutineScope: CoroutineScope) {
    coroutineScope.launch {
        scrollTo(-value)
    }
}

fun ScrollState.animateScrollToTop(coroutineScope: CoroutineScope) {
    coroutineScope.launch {
        animateScrollTo(-value)
    }
}