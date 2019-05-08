package apps.mjn.countries.utils

import apps.mjn.countries.ui.model.ParcelableCountry
import apps.mjn.domain.entity.Country

fun Country.toParcelable() = 
        ParcelableCountry(
            name,
            alpha2Code,
            capital,
            region,
            population,
            area
        )