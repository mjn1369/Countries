package apps.mjn.countries.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import apps.mjn.countries.ui.base.BaseViewModel
import apps.mjn.countries.ui.model.Resource
import apps.mjn.countries.ui.model.ResourceState
import apps.mjn.domain.entity.Country
import apps.mjn.domain.interactor.GetCountriesUseCase
import javax.inject.Inject

class GetCountriesViewModel @Inject constructor(private val getCountriesUseCase: GetCountriesUseCase) :
    BaseViewModel() {

    private lateinit var allCountriesList: List<Country>
    private val searchQuery: MutableLiveData<String> = MutableLiveData()
    private val data: MediatorLiveData<Resource<List<Country>>> = MediatorLiveData()

    init {
        useCases += getCountriesUseCase
        data.addSource(searchQuery) { query ->
            if (!query.isNullOrBlank()) {
                data.value = Resource(
                    ResourceState.SUCCESS,
                    allCountriesList.filter { it.name.toLowerCase().contains(query) }
                    )
            } else {
                data.value = Resource(ResourceState.SUCCESS, allCountriesList)
            }
        }
    }

    fun getData() = data

    fun load() {
        data.value = Resource(ResourceState.LOADING)
        getCountriesUseCase.execute(GetCountriesUseCase.Params(), ::success, ::failure)
    }

    private fun success(list: List<Country>) {
        allCountriesList = list
        data.value = Resource(ResourceState.SUCCESS, list)
    }

    private fun failure(throwable: Throwable) {
        data.value = Resource(ResourceState.ERROR, throwable = throwable)
    }

    fun search(query: String) {
        searchQuery.value = query
    }
}