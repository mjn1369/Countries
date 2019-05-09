package apps.mjn.countries.ui.screen.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
        buildAndObserveViewModel()
        initViews()
        getCountries()
    }

    private fun buildAndObserveViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GetCountriesViewModel::class.java)
        viewModel.getData().observe(this, Observer(::listenToViewModelData))
    }

    private fun initViews() {
        countriesAdapter = CountriesAdapter(
            ArrayList()
        ) {
            onCountryClick(it)
        }
        recyclerViewCountries.addItemDecoration(VerticalSpaceItemDecoration(resources.getDimensionPixelSize(R.dimen.space_0_5x)))
        recyclerViewCountries.adapter = countriesAdapter

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // not implemented
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // not implemented
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    success(viewModel.search(s.toString().toLowerCase()))
                }
            }

        })
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
                ResourceState.LOADING -> { loading() }
                ResourceState.SUCCESS -> { success(resource.data) }
                ResourceState.ERROR -> { error(resource.throwable?.message) }
            }
        }
    }

    private fun loading() {
        clearScreen()
        loadingProgressBar.visible()
    }

    private fun hideLoading() {
        loadingProgressBar.gone()
    }

    private fun error(message: String?) {
        clearScreen()
        setMessage(
            message ?: getString(R.string.countries_error), getString(R.string.try_again)) {
            loading()
            viewModel.load()
        }
    }

    private fun setMessage(message: String?, actionMessage: String? = null, action: () -> Unit) {
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

    private fun success(data: List<Country>?) {
        clearScreen()
        data?.let {
            if(data.isEmpty()){
                setMessage(getString(R.string.countries_empty), null) {}
            } else {
                showList()
                countriesAdapter?.setItems(
                    sortCountries(
                        data,
                        getCountryNameComparator(SortType.ASC)
                    )
                )
            }
        } ?: error(getString(R.string.countries_error))
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
