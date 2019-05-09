package apps.mjn.countries.ui.screen.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import apps.mjn.countries.R
import apps.mjn.domain.entity.Country

class CountriesAdapter(
    private val onItemClick: (Country) -> (Unit)
) : RecyclerView.Adapter<CountryViewHolder>() {

    var items: List<Country> = arrayListOf()
        set(data) {
            field = data
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_country, parent, false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(items[position], onItemClick)
    }

    override fun getItemCount() = items.size
}