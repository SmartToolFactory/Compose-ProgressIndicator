package com.smarttoolfactory.progressindicator

import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.progressBarRangeInfo
import androidx.compose.ui.semantics.semantics

/**
 * Contains the [semantics] required for an indeterminate progress indicator, that represents the
 * fact of the in-progress operation.
 *
 * If you need determinate progress 0.0 to 1.0, consider using overload with the progress
 * parameter.
 *
 */
@Stable
fun Modifier.progressSemantics(): Modifier {
    // Older versions of Talkback will ignore nodes with range info which aren't focusable or
    // screen reader focusable. Setting this semantics as merging descendants will mark it as
    // screen reader focusable.
    return semantics(mergeDescendants = true) {
        progressBarRangeInfo = ProgressBarRangeInfo.Indeterminate
    }
}