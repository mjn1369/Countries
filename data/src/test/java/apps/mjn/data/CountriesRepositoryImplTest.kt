package apps.mjn.data

import apps.mjn.data.datasource.CountriesRemoteDataSource
import apps.mjn.data.repository.CountriesRepositoryImpl
import apps.mjn.domain.repository.CountriesRepository
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class CountriesRepositoryImplTest {
    private val dataSize = 5
    private val dataSource: CountriesRemoteDataSource = mockk()
    private val repository: CountriesRepository = CountriesRepositoryImpl(dataSource)

    @Before
    fun setup() {
        clearAllMocks()
    }

    @Test
    fun `getCountries() sent back results`() {
        val list = CountryGenerator.randomCountries(dataSize)
        every { dataSource.getCountries() } returns Single.just(list)
        with(repository.getCountries().test()) {
            assertNoErrors()
            assertComplete()
            assertValue(list)
        }
    }
}