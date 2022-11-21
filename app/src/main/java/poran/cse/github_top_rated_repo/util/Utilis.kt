package poran.cse.github_top_rated_repo.util

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat
import poran.cse.github_top_rated_repo.R
import kotlin.math.ln
import kotlin.math.pow
import kotlin.random.Random


fun numberFormat(count: Long): String {
    if (count < 1000) return "" + count
    val exp = (ln(count.toDouble()) / ln(1000.0)).toInt()
    return String.format(
        "%.1f %c",
        count / 1000.0.pow(exp.toDouble()),
        "kMGTPE"[exp - 1]
    )
}

/**
 * set random color in shape drawable
 */
fun Context.generateDrawable(): Drawable {
    val drawable = ContextCompat
        .getDrawable(this, R.drawable.round_background)?.mutate() as GradientDrawable
    drawable.setColor(getLanguageColourCode())
    return drawable
}

/**
 * Generate  random color
 */
fun getLanguageColourCode(): Int {
    return Color.argb(
        255,
        Random.nextInt(256),
        Random.nextInt(256),
        Random.nextInt(256)
    )
}