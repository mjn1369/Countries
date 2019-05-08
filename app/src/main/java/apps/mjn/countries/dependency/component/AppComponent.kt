package apps.mjn.countries.dependency.component

import apps.mjn.countries.app.CountriesApp
import apps.mjn.countries.dependency.module.AppModule
import apps.mjn.countries.dependency.module.NetworkModule
import apps.mjn.countries.dependency.module.ViewModelModule
import apps.mjn.countries.ui.screens.main.MainModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ViewModelModule::class,
        AppModule::class,
        NetworkModule::class,
        MainModule::class
    ]
)
interface AppComponent : AndroidInjector<CountriesApp> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<CountriesApp>()

}