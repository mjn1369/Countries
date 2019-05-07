package apps.mjn.countries.app

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class CountriesApp : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }
}