package apps.mjn.domain

import apps.mjn.domain.data.CountryGenerator
import apps.mjn.domain.entity.Country
import apps.mjn.domain.executer.PostExecutionThread
import apps.mjn.domain.executer.UseCaseExecutor
import apps.mjn.domain.interactor.GetCountriesUseCase
import apps.mjn.domain.repository.CountriesRepository
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class GetCountriesUseCaseTest {
    private val dataSize = 5
    private val repository: CountriesRepository = mockk()
    private val useCaseExecutor: UseCaseExecutor = mockk(relaxed = true)
    private val postExecutionThread: PostExecutionThread = mockk(relaxed = true)
    private val usecase: GetCountriesUseCase =
        GetCountriesUseCase(repository, useCaseExecutor, postExecutionThread)
    private val params = GetCountriesUseCase.Params()

    @Before
    fun setup() {
        clearAllMocks()
    }

    @Test
    fun `getCountries() is called`() {
        mockGetListResponse()
        usecase.execute(params, {}, {})
        verify(exactly = 1) { repository.getCountries() }
    }

    private fun mockGetListResponse(list: List<Country> = CountryGenerator.randomCountries(dataSize)) {
        every { repository.getCountries() } returns Single.just(list)
    }
}