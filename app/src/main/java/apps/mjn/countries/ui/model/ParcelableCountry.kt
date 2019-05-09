package apps.mjn.countries.ui.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ParcelableCountry(
    val name: String,
    val alpha2Code: String,
    val capital: String?,
    val region: String,
    val population: Long,
    val area: Double?,
    val callingCodes: List<String>,
    val latlng: List<Double>
) : Parcelable