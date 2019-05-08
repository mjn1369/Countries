package apps.mjn.countries.ui.screen.main

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import apps.mjn.countries.R
import apps.mjn.countries.ui.base.BaseActivity
import apps.mjn.countries.ui.screen.main.adapter.CountriesAdapter
import apps.mjn.countries.ui.screen.main.adapter.VerticalSpaceItemDecoration
import apps.mjn.countries.ui.model.Resource
import apps.mjn.countries.ui.model.ResourceState
import apps.mjn.countries.ui.screen.details.DetailsWindowBottomSheet
import apps.mjn.countries.ui.viewmodel.GetCountriesViewModel
import apps.mjn.countries.utils.SortType
import apps.mjn.countries.utils.getCountryNameComparator
import apps.mjn.domain.entity.Country
import kotlinx.android.synthetic.main.activity_main.*
import mjn.apps.weather.extension.gone
import mjn.apps.weather.extension.visible
import java.util.*

class MainActivity : BaseActivity() {

    private lateinit var viewModel: GetCountriesViewModel
    private lateinit var countriesAdapter: CountriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buildViewModel()
        observeViewModel()
        initList()
        getCountries()
    }

    private fun buildViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GetCountriesViewModel::class.java)
    }

    private fun observeViewModel() {
        viewModel.getData().observe(this, Observer(::listenToViewModelData))
    }

    private fun initList() {
        countriesAdapter = CountriesAdapter(
            ArrayList()
        ) {
            onCountryClick(it)
        }
        recyclerViewCountries.addItemDecoration(VerticalSpaceItemDecoration(resources.getDimensionPixelSize(R.dimen.space_0_5x)))
        recyclerViewCountries.adapter = countriesAdapter
    }

    private fun onCountryClick(country: Country) {
        showDetails(country)
    }

    private fun showDetails(country: Country) {
        DetailsWindowBottomSheet.getInstance(country).show(supportFragmentManager, "")
    }

    private fun getCountries() {
        viewModel.load()
    }

    private fun listenToViewModelData(resource: Resource<List<Country>>?) {
        resource?.let {
            when (resource.resourceState) {
                ResourceState.LOADING -> {
                    clearScreen()
                    showLoading()
                }
                ResourceState.SUCCESS -> {
                    clearScreen()
                    handleSuccess(resource.data)
                }
                ResourceState.ERROR -> {
                    clearScreen()
                    handleError(resource.throwable?.message)
                }
            }
        }
    }

    private fun handleError(message: String?) {
        setMessage(
            message ?: getString(R.string.countries_error),
            getString(R.string.try_again)
        ) {
            showLoading()
            viewModel.load()
        }
    }

    private fun showLoading() {
        loadingProgressBar.visible()
    }

    private fun hideLoading() {
        loadingProgressBar.gone()
    }

    private fun setMessage(message: String?, actionMessage: String?, action: () -> Unit) {
        message?.let {
            messageTextView.visible()
            messageTextView.text = message
        } ?: messageTextView.gone()

        actionMessage?.let {
            messageActionButton.setOnClickListener { action() }
            messageActionButton.visible()
            messageActionButton.text = actionMessage
        } ?: messageActionButton.gone()
    }

    private fun hideMessage() {
        messageTextView.gone()
        messageActionButton.gone()
    }

    private fun handleSuccess(data: List<Country>?) {
        data?.let {
            showList()
            countriesAdapter?.setItems(
                sortCountries(
                    data,
                    getCountryNameComparator(SortType.ASC)
                )
            )
        } ?: handleError(getString(R.string.countries_error))
    }

    private fun showList() {
        recyclerViewCountries.visible()
    }

    private fun hideList() {
        recyclerViewCountries.gone()
    }

    private fun clearScreen() {
        hideLoading()
        hideMessage()
        hideList()
    }

    private fun sortCountries(list: List<Country>, comparator: Comparator<Country>): List<Country> {
        Collections.sort(list, comparator)
        return list.sortedWith(nullsLast(comparator))
    }
}
