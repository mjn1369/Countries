package apps.mjn.countries.utils

import apps.mjn.domain.entity.Country

enum class SortType { ASC, DESC }

fun getCountryNameComparator(sortType: SortType): Comparator<Country> {
    return when (sortType) {
        SortType.ASC -> {
            compareBy { it.name }
        }
        SortType.DESC -> {
            compareByDescending { it.name }
        }
    }
}

fun getCountryAreaComparator(sortType: SortType): Comparator<Country> {
    return when (sortType) {
        SortType.ASC -> {
            compareBy { it.area }
        }
        SortType.DESC -> {
            compareByDescending { it.area }
        }
    }
}

fun getCountryPopulationComparator(sortType: SortType): Comparator<Country> {
    return when (sortType) {
        SortType.ASC -> {
            compareBy { it.population }
        }
        SortType.DESC -> {
            compareByDescending { it.population }
        }
    }
}