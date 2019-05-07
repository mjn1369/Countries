package apps.mjn.countries.dependency.module

import android.content.Context
import apps.mjn.countries.app.CountriesApp
import apps.mjn.countries.executer.ExecutorThread
import apps.mjn.countries.executer.UIThread
import apps.mjn.domain.executer.PostExecutionThread
import apps.mjn.domain.executer.UseCaseExecutor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(application: CountriesApp): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideUseCaseExecutor(): UseCaseExecutor = ExecutorThread()

    @Provides
    @Singleton
    fun providePostExecutionThread(): PostExecutionThread = UIThread()
}