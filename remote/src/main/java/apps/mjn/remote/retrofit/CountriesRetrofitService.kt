package apps.mjn.remote.retrofit

import apps.mjn.domain.entity.Country
import retrofit2.Call
import retrofit2.http.GET

interface CountriesRetrofitService {
    @GET("all?fields=name;alpha2Code;capital;region;population;area;flag")
    fun getCountries(): Call<List<Country>>
}