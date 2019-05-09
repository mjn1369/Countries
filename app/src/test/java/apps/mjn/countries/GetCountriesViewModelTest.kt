package apps.mjn.countries

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import apps.mjn.countries.ui.model.ResourceState
import apps.mjn.countries.ui.viewmodel.GetCountriesViewModel
import apps.mjn.domain.entity.Country
import apps.mjn.domain.interactor.GetCountriesUseCase
import apps.mjn.remote.failure.Failure
import io.mockk.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class GetCountriesViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val dataSize = 5
    private val getCountriesUseCase: GetCountriesUseCase = mockk(relaxed = true)
    private val viewModel: GetCountriesViewModel =
        GetCountriesViewModel(getCountriesUseCase)

    @Before
    fun setup() {
        clearAllMocks()
    }

    @Test
    fun `getCountriesUseCase is called`() {
        viewModel.load()
        verify(exactly = 1) { getCountriesUseCase.execute(any(), any(), any()) }
    }

    @Test
    fun `load() goes to loading state`() {
        viewModel.load()
        assert(viewModel.getData().value?.resourceState == ResourceState.LOADING)
    }

    @Test
    fun `load() completed with failure state`() {
        val error = slot<(throwable: Throwable) -> Unit>()

        every {
            getCountriesUseCase.execute(any(), any(), capture(error))
        } answers {
            error.invoke(Failure.ServerError("Server Error"))
        }

        viewModel.load()
        assert(viewModel.getData().value?.resourceState == ResourceState.ERROR)
    }

    @Test
    fun `load() completed with success state`() {
        val success = slot<(list: List<Country>) -> Unit>()

        every {
            getCountriesUseCase.execute(any(), capture(success), any())
        } answers {
            success.invoke(CountryGenerator.randomCountries(dataSize))
        }

        viewModel.load()
        assert(viewModel.getData().value?.resourceState == ResourceState.SUCCESS)
    }

    @Test
    fun `load() has data`() {
        val list: List<Country> = CountryGenerator.randomCountries(dataSize)
        val success = slot<(list: List<Country>) -> Unit>()

        every {
            getCountriesUseCase.execute(any(), capture(success), any())
        } answers {
            success.invoke(list)
        }

        viewModel.load()
        assert(viewModel.getData().value?.resourceState == ResourceState.SUCCESS)
        assert(viewModel.getData().value?.data == list)
    }

    @Test
    fun `search(query) returns correct data`(){
        val list: List<Country> = CountryGenerator.randomCountries(dataSize)
        val success = slot<(list: List<Country>) -> Unit>()

        every {
            getCountriesUseCase.execute(any(), capture(success), any())
        } answers {
            success.invoke(list)
        }

        viewModel.load()
        val query = "a"
        assert(viewModel.search(query).all { it.name.contains(query) })
    }
}