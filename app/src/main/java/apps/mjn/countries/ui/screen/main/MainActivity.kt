package apps.mjn.countries.ui.screen.main

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import apps.mjn.countries.R
import apps.mjn.countries.ui.base.BaseActivity
import apps.mjn.countries.ui.model.Resource
import apps.mjn.countries.ui.model.ResourceState
import apps.mjn.countries.ui.screen.details.DetailsWindowBottomSheet
import apps.mjn.countries.ui.screen.main.adapter.CountriesAdapter
import apps.mjn.countries.ui.screen.main.adapter.VerticalSpaceItemDecoration
import apps.mjn.countries.ui.viewmodel.GetCountriesViewModel
import apps.mjn.countries.utils.SortType
import apps.mjn.countries.utils.getCountryNameComparator
import apps.mjn.domain.entity.Country
import com.jakewharton.rxbinding3.widget.afterTextChangeEvents
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import apps.mjn.countries.extension.gone
import apps.mjn.countries.extension.visible
import java.util.*
import java.util.concurrent.TimeUnit

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
        countriesAdapter = CountriesAdapter(::onCountryClick)
        with(recyclerViewCountries) {
            setHasFixedSize(false)
            adapter = countriesAdapter
            addItemDecoration(VerticalSpaceItemDecoration(resources.getDimensionPixelSize(R.dimen.space_0_5x)))
            itemAnimator?.changeDuration = 0L
            itemAnimator?.moveDuration = resources.getInteger(R.integer.animation_duration_list).toLong()
        }

        etSearch.afterTextChangeEvents()
            .skipInitialValue()
            .debounce(500L, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if(it.editable.isNullOrBlank()){
                    ivClearSearch.gone()
                } else {
                    ivClearSearch.visible()
                }
                viewModel.search(it.editable.toString())
            }

        ivClearSearch.setOnClickListener {
            etSearch.text = null
        }
    }

    private fun onCountryClick(country: Country) {
        showDetails(country)
    }

    private fun showDetails(country: Country) {
        DetailsWindowBottomSheet.getInstance(country).show(supportFragmentManager, "")
    }

    private fun getCountries() {
        disableSearch()
        viewModel.load()
    }

    private fun listenToViewModelData(resource: Resource<List<Country>>?) {
        resource?.let {
            when (resource.resourceState) {
                ResourceState.LOADING -> {
                    loading()
                }
                ResourceState.SUCCESS -> {
                    success(resource.data)
                }
                ResourceState.ERROR -> {
                    error(resource.throwable?.message)
                }
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
        disableSearch()
        setMessage(
            message ?: getString(R.string.countries_error), getString(R.string.try_again)
        ) {
            loading()
            viewModel.load()
        }
    }

    private fun disableSearch(){
        etSearch.isEnabled = false
        etSearch.isClickable = false
        etSearch.clearFocus()
    }

    private fun enableSearch(){
        etSearch.isEnabled = true
        etSearch.isClickable = true
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
        enableSearch()
        data?.let {
            if (data.isEmpty()) {
                setMessage(getString(R.string.countries_empty), null) {}
            } else {
                showList()
                countriesAdapter.items = sortCountries(data, getCountryNameComparator(SortType.ASC))
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
