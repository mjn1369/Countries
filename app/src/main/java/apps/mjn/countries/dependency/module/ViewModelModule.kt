package apps.mjn.countries.dependency.module

import androidx.lifecycle.ViewModelProvider
import apps.mjn.countries.app.ViewModelFactory
import dagger.Binds

import dagger.Module

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}