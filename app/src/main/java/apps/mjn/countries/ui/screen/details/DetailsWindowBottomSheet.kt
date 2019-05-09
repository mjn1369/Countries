package apps.mjn.countries.ui.screen.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import apps.mjn.countries.ARG_COUNTRY
import apps.mjn.countries.R
import apps.mjn.countries.ui.model.ParcelableCountry
import apps.mjn.countries.utils.getDrawableId
import apps.mjn.countries.utils.toParcelable
import apps.mjn.domain.entity.Country
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.details_window.*
import java.lang.Exception


class DetailsWindowBottomSheet : BottomSheetDialogFragment() {

    private lateinit var country: ParcelableCountry

    companion object {
        fun getInstance(country: Country): DetailsWindowBottomSheet {
            val fragment = DetailsWindowBottomSheet()
            val arguments = Bundle()
            arguments.putParcelable(ARG_COUNTRY, country.toParcelable())
            fragment.arguments = arguments
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.details_window, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog.window.attributes.windowAnimations = R.style.BottomSheetAnimation
        country = arguments?.getParcelable(ARG_COUNTRY) as ParcelableCountry
        if (country != null) {
            setupViews()
        } else {
            context?.let {
                Toast.makeText(context, context?.getString(R.string.country_notfound), Toast.LENGTH_LONG).show()
                dismiss()
            }
        }
    }

    private fun setupViews() {
        // set name
        tvCountryInfoName.text = country.name

        // set flag
        country.alpha2Code?.let {
            ivCountryInfoFlag.setImageResource(
                context!!.getDrawableId(
                    context!!.resources.getString(
                        R.string.flag_prefix,
                        it.toLowerCase()
                    )
                )
            )
        }

        // set region
        tvCountryInfoRegionValue.text = country.region

        // set population
        tvCountryInfoPopulationValue.text = "%,d".format(country.population)

        // set area
        tvCountryInfoAreaValue.text =
            country.area?.let {
                "%,d".format(country.area!!.toInt())
            } ?: context!!.getString(R.string.unknown)

        // set capital
        if (country.capital.isNullOrEmpty()) {
            tvCountryInfoCapitalValue.text = context!!.getString(R.string.unknown)
        } else {
            tvCountryInfoCapitalValue.text = country.capital
        }

        // set calling codes
        if (country.callingCodes.isNullOrEmpty()) {
            tvCountryInfoCallingCodesValue.text = context!!.getString(R.string.unknown)
        } else {
            var callingCodes = country.callingCodes.filter { it.isNotBlank() }.joinToString {
                "+$it"
            }
            if (callingCodes.isNotEmpty()) {
                tvCountryInfoCallingCodesValue.text = callingCodes
            } else {
                tvCountryInfoCallingCodesValue.text = context!!.getString(R.string.unknown)
            }
        }

        // set show map button
        containerCountryInfoShowOnMap.setOnClickListener {
            val zoomLevel = 6
            val uri = "geo:${country.latlng[0]},${country.latlng[1]}?z=$zoomLevel"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            try {
                context?.startActivity(intent)
            }
            catch (exception: Exception){
                Toast.makeText(context, "Please install a map application", Toast.LENGTH_LONG).show()
            }
        }
    }
}