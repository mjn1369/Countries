package apps.mjn.countries.utils.comparator

import apps.mjn.domain.entity.Country

class CountryPopulationComparator : Comparator<Country> {
    override fun compare(country1: Country, country2: Country): Int {
        return country1.population.compareTo(country2.population)
    }
}