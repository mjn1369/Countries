package apps.mjn.data.datasource

import apps.mjn.domain.entity.Country
import io.reactivex.Single

interface CountriesRemoteDataSource {
    fun getCountries(): Single<List<Country>>
}