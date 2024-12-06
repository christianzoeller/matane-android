package christianzoeller.matane.styleguide.modifiers

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.rememberTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.node.Ref
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import christianzoeller.matane.ui.tooling.CompactPreview
import christianzoeller.matane.ui.tooling.MatanePreview
import kotlin.math.max

/**
 * Placeholder modifier for skeleton loading.
 *
 * Based on the implementation from Accompanist but reduced to allow only a
 * very limited subset of customization options.
 */
fun Modifier.placeholder(
    visible: Boolean,
    color: Color = Color.Unspecified,
    shape: Shape? = null
): Modifier = composed {
    Modifier.placeholder(
        visible = visible,
        color = if (color.isSpecified) color else color(),
        highlight = Shimmer(highlightColor = highlightColor()),
        shape = shape ?: shapes.small
    )
}

private fun Modifier.placeholder(
    visible: Boolean,
    color: Color,
    highlight: Shimmer,
    shape: Shape = RectangleShape
): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "placeholder"
        value = visible
        properties["visible"] = visible
        properties["color"] = color
        properties["highlight"] = highlight
        properties["shape"] = shape
    }
) {
    // Values used for caching purposes
    val lastSize = remember { Ref<Size>() }
    val lastLayoutDirection = remember { Ref<LayoutDirection>() }
    val lastOutline = remember { Ref<Outline>() }

    // The current highlight animation progress
    var highlightProgress: Float by remember { mutableFloatStateOf(0f) }

    // This is our crossfade transition
    val transitionState = remember { MutableTransitionState(visible) }.apply {
        targetState = visible
    }
    val transition = rememberTransition(transitionState, "placeholder_crossfade")

    val placeholderAlpha by transition.animateFloat(
        transitionSpec = { spring() },
        label = "placeholder_fade",
        targetValueByState = { placeholderVisible -> if (placeholderVisible) 1f else 0f }
    )
    val contentAlpha by transition.animateFloat(
        transitionSpec = { spring() },
        label = "content_fade",
        targetValueByState = { placeholderVisible -> if (placeholderVisible) 0f else 1f }
    )

    // Run the optional animation spec and update the progress if the placeholder is visible
    val animationSpec = highlight.animationSpec
    if (visible || placeholderAlpha >= 0.01f) {
        val infiniteTransition = rememberInfiniteTransition(
            label = "infiniteTransition"
        )
        highlightProgress = infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = animationSpec,
            label = "highlightProgress"
        ).value
    }

    val paint = remember { Paint() }
    remember(color, shape, highlight) {
        drawWithContent {
            // Draw the composable content first
            if (contentAlpha in 0.01f..0.99f) {
                // If the content alpha is between 1% and 99%, draw it in a layer with
                // the alpha applied
                paint.alpha = contentAlpha
                withLayer(paint) {
                    with(this@drawWithContent) {
                        drawContent()
                    }
                }
            } else if (contentAlpha >= 0.99f) {
                // If the content alpha is > 99%, draw it with no alpha
                drawContent()
            }

            if (placeholderAlpha in 0.01f..0.99f) {
                // If the placeholder alpha is between 1% and 99%, draw it in a layer with
                // the alpha applied
                paint.alpha = placeholderAlpha
                withLayer(paint) {
                    lastOutline.value = drawPlaceholder(
                        shape = shape,
                        color = color,
                        highlight = highlight,
                        progress = highlightProgress,
                        lastOutline = lastOutline.value,
                        lastLayoutDirection = lastLayoutDirection.value,
                        lastSize = lastSize.value,
                    )
                }
            } else if (placeholderAlpha >= 0.99f) {
                // If the placeholder alpha is > 99%, draw it with no alpha
                lastOutline.value = drawPlaceholder(
                    shape = shape,
                    color = color,
                    highlight = highlight,
                    progress = highlightProgress,
                    lastOutline = lastOutline.value,
                    lastLayoutDirection = lastLayoutDirection.value,
                    lastSize = lastSize.value,
                )
            }

            // Keep track of the last size & layout direction
            lastSize.value = size
            lastLayoutDirection.value = layoutDirection
        }
    }
}

private fun DrawScope.drawPlaceholder(
    shape: Shape,
    color: Color,
    highlight: Shimmer,
    progress: Float,
    lastOutline: Outline?,
    lastLayoutDirection: LayoutDirection?,
    lastSize: Size?,
): Outline? {
    // shortcut to avoid Outline calculation and allocation
    if (shape === RectangleShape) {
        // Draw the initial background color
        drawRect(color = color)

        // Draw the shimmer
        drawRect(
            brush = highlight.brush(progress, size),
            alpha = highlight.alpha(progress),
        )

        // We didn't create an outline so return null
        return null
    }

    // Otherwise we need to create an outline from the shape
    val outline = lastOutline.takeIf {
        size == lastSize && layoutDirection == lastLayoutDirection
    } ?: shape.createOutline(size, layoutDirection, this)

    // Draw the placeholder color
    drawOutline(outline = outline, color = color)

    // Draw the shimmer color
    drawOutline(
        outline = outline,
        brush = highlight.brush(progress, size),
        alpha = highlight.alpha(progress),
    )

    // Return the outline we used
    return outline
}

private inline fun DrawScope.withLayer(
    paint: Paint,
    drawBlock: DrawScope.() -> Unit,
) = drawIntoCanvas { canvas ->
    canvas.saveLayer(size.toRect(), paint)
    drawBlock()
    canvas.restore()
}

@Composable
private fun color(
    backgroundColor: Color = colorScheme.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    contentAlpha: Float = 0.1f,
): Color = contentColor.copy(contentAlpha).compositeOver(backgroundColor)

@Composable
private fun highlightColor(): Color =
    colorScheme.inverseSurface.copy(alpha = 0.2f) // Original value: 0.75f

private data class Shimmer(
    private val highlightColor: Color,
) {
    companion object {

        /**
         * The alpha value for max progress for [Shimmer].
         */
        const val PROGRESS_FOR_MAX_ALPHA: Float = 0.6f
    }

    /**
     * The [AnimationSpec] to use when running the animation for this highlight.
     */
    val animationSpec: InfiniteRepeatableSpec<Float> by lazy {
        infiniteRepeatable(
            animation = tween(durationMillis = 1700, delayMillis = 200),
            repeatMode = RepeatMode.Restart
        )
    }

    /**
     * Return a [Brush] to draw for the given [progress] and [size].
     *
     * @param progress the current animated progress in the range of 0f..1f.
     * @param size The size of the current layout to draw in.
     */
    fun brush(
        progress: Float,
        size: Size,
    ): Brush = Brush.radialGradient(
        colors = listOf(
            highlightColor.copy(alpha = 0f),
            highlightColor,
            highlightColor.copy(alpha = 0f),
        ),
        center = Offset(x = 0f, y = 0f),
        radius = (max(size.width, size.height) * progress * 2).coerceAtLeast(0.01f),
    )

    /**
     * Return the desired alpha value used for drawing the [Brush] returned from [brush].
     *
     * @param progress the current animated progress in the range of 0f..1f.
     */
    fun alpha(progress: Float): Float = when {
        // From 0f...ProgressForOpaqueAlpha we animate from 0..1
        progress <= PROGRESS_FOR_MAX_ALPHA -> {
            lerp(
                start = 0f,
                stop = 1f,
                fraction = progress / PROGRESS_FOR_MAX_ALPHA
            )
        }
        // From ProgressForOpaqueAlpha..1f we animate from 1..0
        else -> {
            lerp(
                start = 1f,
                stop = 0f,
                fraction = (progress - PROGRESS_FOR_MAX_ALPHA) / (1f - PROGRESS_FOR_MAX_ALPHA)
            )
        }
    }
}

@CompactPreview
@Composable
private fun Placeholder_Preview() = MatanePreview {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Text(
            text = "This is a headline",
            modifier = Modifier.placeholder(visible = true),
            style = typography.headlineMedium
        )
        Text(
            text = "This is a slightly longer text that might serve as an introductory sentence for an article or something like that.",
            modifier = Modifier.placeholder(visible = true),
            style = typography.bodyLarge
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f)
                .placeholder(visible = true)
        )
        Text(
            text = "And in the end, some final words.",
            modifier = Modifier.placeholder(visible = true)
        )
    }
}