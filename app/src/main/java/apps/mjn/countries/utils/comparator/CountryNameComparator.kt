package apps.mjn.countries.utils.comparator

import apps.mjn.domain.entity.Country

class CountryNameComparator : Comparator<Country> {
    override fun compare(country1: Country, country2: Country): Int {
        return country1.name.compareTo(country2.name)
    }
}