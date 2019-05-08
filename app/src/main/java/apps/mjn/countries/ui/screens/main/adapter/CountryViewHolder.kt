package apps.mjn.countries.ui.screens.main.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import apps.mjn.domain.entity.Country
import kotlinx.android.synthetic.main.item_country.view.*

class CountryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(item: Country, onClick: (Country) -> (Unit)) = with(itemView) {
        tvCountryItemName.text = item.name
        tvCountryItemName.isSelected = true
        tvCountryItemAlpha2Code.text = "(${item.alpha2Code})"
        tvCountryItemRegion.text = item.region
        setOnClickListener {
            onClick(item)
        }
    }
}