package com.example.data.repository

import apps.mjn.domain.entity.Country
import apps.mjn.domain.repository.CountriesRepository
import com.example.data.datasource.CountriesRemoteDataSource
import io.reactivex.Single
import javax.inject.Inject

class CountriesRepositoryImpl @Inject constructor(private val remoteDataSource: CountriesRemoteDataSource) :
    CountriesRepository {

    override fun getCountries(): Single<List<Country>> {
        return remoteDataSource.getCountries()
    }
}