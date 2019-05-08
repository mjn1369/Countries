package apps.mjn.countries.utils.comparator

import apps.mjn.domain.entity.Country

class CountryAreaComparator : Comparator<Country> {
    override fun compare(country1: Country, country2: Country): Int {
        return country1.area.compareTo(country2.area)
    }
}