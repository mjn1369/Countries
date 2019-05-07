package apps.mjn.countries.ui.main

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import apps.mjn.countries.R
import apps.mjn.countries.ui.base.BaseActivity
import apps.mjn.countries.ui.model.Resource
import apps.mjn.countries.ui.model.ResourceState
import apps.mjn.countries.ui.viewmodel.GetCountriesViewModel
import apps.mjn.domain.entity.Country

class MainActivity : BaseActivity() {

    lateinit var viewModel: GetCountriesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buildViewModel()
        observeViewModel()
        getCountries()
    }

    private fun buildViewModel(){
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GetCountriesViewModel::class.java)
    }

    private fun observeViewModel(){
        viewModel.getData().observe(this, Observer(::listenToViewModelData))
    }

    private fun getCountries(){
        viewModel.load()
    }

    private fun listenToViewModelData(resource: Resource<List<Country>>?) {
        resource?.let {
            when (resource.resourceState) {
                ResourceState.LOADING -> {}
                ResourceState.SUCCESS -> {}
                ResourceState.ERROR -> {}
            }
        }
    }
}
