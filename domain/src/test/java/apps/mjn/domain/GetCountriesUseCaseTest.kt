package apps.mjn.domain

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
    private val useCase: GetCountriesUseCase =
        GetCountriesUseCase(repository, useCaseExecutor, postExecutionThread)
    private val params = GetCountriesUseCase.Params()

    @Before
    fun setup() {
        clearAllMocks()
    }

    private fun mockGetListResponse(list: List<Country> = CountryGenerator.randomCountries(dataSize)) {
        every { repository.getCountries() } returns Single.just(list)
    }

    @Test
    fun `getCountries() is called`() {
        mockGetListResponse()
        useCase.execute(params, {}, {})
        verify(exactly = 1) { repository.getCountries() }
    }

    @Test
    fun `getCountries() received valid parameters`() {
        mockGetListResponse()
        useCase.execute(params, {}, {})
        verify { repository.getCountries() }
    }

    @Test
    fun `getCountries() completed`() {
        mockGetListResponse()
        useCase.buildSingle(params).test().assertComplete()
    }

    @Test
    fun `getCountries() sent back results`() {
        val list = CountryGenerator.randomCountries(dataSize)
        mockGetListResponse(list)
        useCase.buildSingle(params).test().assertValue(list)
    }
}