package apps.mjn.countries.ui.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import apps.mjn.countries.R
import apps.mjn.countries.ui.base.BaseActivity
import apps.mjn.countries.ui.viewmodel.GetCountriesViewModel

class MainActivity : BaseActivity() {

    lateinit var viewModel: GetCountriesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buildViewModel()
    }

    private fun buildViewModel(){
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GetCountriesViewModel::class.java)
    }
}
