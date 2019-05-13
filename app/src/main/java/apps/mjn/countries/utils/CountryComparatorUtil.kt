package apps.mjn.countries.utils

import apps.mjn.domain.entity.Country

enum class SortType { ASC, DESC }

fun getCountryNameComparator(sortType: SortType): Comparator<Country> {
    return when (sortType) {
        SortType.ASC -> { compareBy { it.name } }
        SortType.DESC -> { compareByDescending { it.name } }
    }
}