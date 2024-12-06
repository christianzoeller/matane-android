package christianzoeller.matane.feature.dictionary

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowWidthSizeClass
import christianzoeller.matane.R
import christianzoeller.matane.styleguide.components.DefaultTopAppBar
import christianzoeller.matane.ui.tooling.CompactPreview
import christianzoeller.matane.ui.tooling.MatanePreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onSearch: () -> Unit,
    onKanjiCardClick: () -> Unit,
    onRadicalCardClick: () -> Unit
) {
    Column {
        DefaultTopAppBar(
            onNavigateUp = null,
            title = R.string.dictionary_home_header,
            actions = {
                IconButton(
                    onClick = onSearch
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(id = R.string.dictionary_home_search_action_icon_description)
                    )
                }
            }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            Text(
                text = stringResource(id = R.string.dictionary_home_browse_header),
                modifier = Modifier.padding(horizontal = 16.dp),
                style = typography.titleLarge
            )
            Spacer(modifier = Modifier.height(32.dp))
            BrowseSection(
                onKanjiCardClick = onKanjiCardClick,
                onRadicalCardClick = onRadicalCardClick,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun BrowseSection(
    onKanjiCardClick: () -> Unit,
    onRadicalCardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val sections = listOf(
        BrowseSection.KanjiSection(onKanjiCardClick),
        BrowseSection.RadicalSection(onRadicalCardClick)
    )

    val sizeClass = currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass
    when (sizeClass) {
        WindowWidthSizeClass.COMPACT -> {
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                sections.forEach { section ->
                    BrowseCard(browseSection = section)
                }
            }
        }

        else -> {
            // TODO I don't have to do it like this, right? .. right?
            val chunkSize = if (sizeClass == WindowWidthSizeClass.MEDIUM) {
                2
            } else {
                3
            }
            val chunkedSections = sections.chunked(size = chunkSize)

            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                chunkedSections.forEach { chunk ->
                    Row(
                        modifier = Modifier.height(IntrinsicSize.Min),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        chunk.forEach { section ->
                            BrowseCard(
                                browseSection = section,
                                modifier = Modifier
                                    .weight(1f, fill = false)
                                    .fillMaxHeight()
                            )
                        }

                        if (chunk.size < chunkSize) {
                            Spacer(modifier = Modifier.weight(1f, fill = false))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BrowseCard(
    browseSection: BrowseSection,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = shapes.large,
        color = colorScheme.surfaceVariant,
        contentColor = colorScheme.onBackground
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = stringResource(id = browseSection.header),
                style = typography.titleMedium
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = stringResource(id = browseSection.description))
            Spacer(modifier = Modifier.height(20.dp))
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = browseSection.onClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorScheme.secondary,
                    contentColor = colorScheme.onSecondary
                )
            ) {
                Text(text = stringResource(id = browseSection.cta))
            }
        }
    }
}

private sealed interface BrowseSection {
    val header: Int
    val description: Int
    val cta: Int
    val onClick: () -> Unit

    data class KanjiSection(
        override val onClick: () -> Unit
    ) : BrowseSection {
        override val header: Int
            @StringRes get() = R.string.dictionary_home_browse_most_frequent_kanji_header
        override val description: Int
            @StringRes get() = R.string.dictionary_home_browse_most_frequent_kanji_description
        override val cta: Int
            @StringRes get() = R.string.dictionary_home_browse_cta
    }

    data class RadicalSection(
        override val onClick: () -> Unit
    ): BrowseSection {
        override val header: Int
            @StringRes get() = R.string.dictionary_home_browse_radical_header
        override val description: Int
            @StringRes get() = R.string.dictionary_home_browse_radical_description
        override val cta: Int
            @StringRes get() = R.string.dictionary_home_browse_cta
    }
}

@CompactPreview
@Composable
private fun HomeScreen_Preview() = MatanePreview {
    HomeScreen(
        onSearch = {},
        onKanjiCardClick = {},
        onRadicalCardClick = {}
    )
}