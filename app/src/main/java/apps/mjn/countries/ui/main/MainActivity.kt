package apps.mjn.countries.ui.main

import android.os.Bundle
import apps.mjn.countries.R
import apps.mjn.countries.ui.base.BaseActivity
import apps.mjn.countries.ui.viewmodel.GetCountriesViewModel
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModel: GetCountriesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
