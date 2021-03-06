package apps.mjn.countries.extension

import android.view.View
import apps.mjn.countries.R

//region View Extensions
fun View.visible() {
    visibility = View.VISIBLE
    animate().alpha(1.0F).duration = context.resources.getInteger(R.integer.animation_duration_visible).toLong()
}

fun View.gone() {
    visibility = View.GONE
    alpha = 0.0F
}
//endregion