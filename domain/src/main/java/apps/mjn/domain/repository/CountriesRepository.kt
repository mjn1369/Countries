package apps.mjn.domain.repository

import apps.mjn.domain.entity.Country
import io.reactivex.Single

interface CountriesRepository {
    fun getCountries(): Single<List<Country>>
}