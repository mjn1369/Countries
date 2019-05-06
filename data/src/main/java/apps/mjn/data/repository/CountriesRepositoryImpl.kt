package apps.mjn.data.repository

import apps.mjn.domain.entity.Country
import apps.mjn.domain.repository.CountriesRepository
import apps.mjn.data.datasource.CountriesRemoteDataSource
import io.reactivex.Single
import javax.inject.Inject

class CountriesRepositoryImpl @Inject constructor(private val remoteDataSource: CountriesRemoteDataSource) :
    CountriesRepository {

    override fun getCountries(): Single<List<Country>> {
        return remoteDataSource.getCountries()
    }
}