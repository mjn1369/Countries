package apps.mjn.countries.ui.main

import androidx.lifecycle.ViewModel
import apps.mjn.countries.ui.viewmodel.GetCountriesViewModel
import apps.mjn.data.datasource.CountriesRemoteDataSource
import apps.mjn.data.repository.CountriesRepositoryImpl
import apps.mjn.domain.repository.CountriesRepository
import apps.mjn.remote.datasource.CountriesRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainModule {

    @ContributesAndroidInjector
    internal abstract fun mainActivity(): MainActivity

    @Binds
    abstract fun bindCountriesListViewModel(getCountriesViewModel: GetCountriesViewModel): ViewModel

    @Binds
    abstract fun bindCountriesRepository(countriesRepository: CountriesRepositoryImpl): CountriesRepository

    @Binds
    abstract fun bindCountriesRemoteDataSource(countriesRemoteDataSource: CountriesRemoteDataSourceImpl): CountriesRemoteDataSource
}