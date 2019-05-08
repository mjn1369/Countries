package apps.mjn.countries.ui.screens.main

import androidx.lifecycle.ViewModel
import apps.mjn.countries.dependency.annotation.ViewModelKey
import apps.mjn.countries.ui.viewmodel.GetCountriesViewModel
import apps.mjn.data.datasource.CountriesRemoteDataSource
import apps.mjn.data.repository.CountriesRepositoryImpl
import apps.mjn.domain.repository.CountriesRepository
import apps.mjn.remote.datasource.CountriesRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class MainModule {

    @ContributesAndroidInjector
    internal abstract fun mainActivity(): MainActivity

    @Binds
    @IntoMap
    @ViewModelKey(GetCountriesViewModel::class)
    abstract fun bindCountriesListViewModel(getCountriesViewModel: GetCountriesViewModel): ViewModel

    @Binds
    abstract fun bindCountriesRepository(countriesRepository: CountriesRepositoryImpl): CountriesRepository

    @Binds
    abstract fun bindCountriesRemoteDataSource(countriesRemoteDataSource: CountriesRemoteDataSourceImpl): CountriesRemoteDataSource
}