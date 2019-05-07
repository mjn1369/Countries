package apps.mjn.countries.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import apps.mjn.countries.ui.base.BaseViewModel
import apps.mjn.countries.ui.model.Resource
import apps.mjn.countries.ui.model.ResourceState
import apps.mjn.domain.entity.Country
import apps.mjn.domain.interactor.GetCountriesUseCase
import javax.inject.Inject

class GetCountriesViewModel @Inject constructor(private val getCountriesUseCase: GetCountriesUseCase) :
    BaseViewModel() {

    private val data: MutableLiveData<Resource<List<Country>>> = MutableLiveData()

    init {
        useCases += getCountriesUseCase
    }

    fun getData(): LiveData<Resource<List<Country>>> = data

    fun load() {
        data.value = Resource(ResourceState.LOADING)
        getCountriesUseCase.execute(GetCountriesUseCase.Params(), ::success, ::error)
    }

    private fun success(list: List<Country>) {
        data.value = Resource(ResourceState.SUCCESS, list)
    }

    private fun error(throwable: Throwable) {
        data.value = Resource(ResourceState.ERROR, throwable = throwable)
    }
}