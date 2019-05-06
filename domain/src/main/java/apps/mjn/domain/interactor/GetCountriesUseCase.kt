package apps.mjn.domain.interactor

import apps.mjn.domain.entity.Country
import apps.mjn.domain.executer.PostExecutionThread
import apps.mjn.domain.executer.UseCaseExecutor
import apps.mjn.domain.interactor.base.SingleUseCase
import apps.mjn.domain.repository.CountriesRepository
import io.reactivex.Single
import javax.inject.Inject

class GetCountriesUseCase @Inject constructor(
    private val countriesRepository: CountriesRepository,
    useCaseExecutor: UseCaseExecutor,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<List<Country>, GetCountriesUseCase.Params>(useCaseExecutor, postExecutionThread) {

    override fun buildSingle(params: Params): Single<List<Country>> {
        return countriesRepository.getCountries()
    }

    class Params
}