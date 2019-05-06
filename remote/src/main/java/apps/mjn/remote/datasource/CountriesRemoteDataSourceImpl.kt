package apps.mjn.remote.datasource

import apps.mjn.data.datasource.CountriesRemoteDataSource
import apps.mjn.domain.entity.Country
import apps.mjn.remote.RemoteConstants
import apps.mjn.remote.extension.toRxSingle
import apps.mjn.remote.retrofit.CountriesRetrofitService
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class CountriesRemoteDataSourceImpl @Inject constructor(okHttpClient: OkHttpClient) : CountriesRemoteDataSource {

    private val service: CountriesRetrofitService = Retrofit.Builder().baseUrl(RemoteConstants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient).build().create(CountriesRetrofitService::class.java)

    override fun getCountries(): Single<List<Country>> {
        return service.getCountries().toRxSingle()
    }

}