package cz.uhk.teeter

import android.content.res.Resources

// extensions - how to move dp to pixels
val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()