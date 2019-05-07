package apps.mjn.countries.ui.main

import androidx.lifecycle.ViewModel
import apps.mjn.countries.ui.viewmodel.GetCountriesViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainModule {

    @ContributesAndroidInjector
    internal abstract fun mainActivity(): MainActivity

    @Binds
    abstract fun bindCountriesListViewModel(getCountriesViewModel: GetCountriesViewModel): ViewModel
}