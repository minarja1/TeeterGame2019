package cz.uhk.teeter

import android.content.res.Resources

// extensions - how to move dp to pixels
val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

// 39f Dots Per Inches (39 inches = 1m)
val Float.metersToPx: Float
    get() = (this * 39f * Resources.getSystem().displayMetrics.densityDpi)

val Float.pxToMeters: Float
    get() = (this / Resources.getSystem().displayMetrics.densityDpi / 39f)