package apps.mjn.countries.utils

import android.content.Context

fun Context.getDrawableId(name: String){
    resources.getIdentifier(name, "drawable", packageName)
}