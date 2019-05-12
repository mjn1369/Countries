package apps.mjn.countries.ui.screen.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import apps.mjn.countries.R
import apps.mjn.domain.entity.Country
import kotlin.properties.Delegates

class CountriesAdapter(
    private val onItemClick: (Country) -> (Unit)
) : RecyclerView.Adapter<CountryViewHolder>(), AutoUpdatableAdapter {

    var items: List<Country> by Delegates.observable(emptyList()) { _, old, new ->
        autoNotify(old, new) { o, n -> o.name == n.name }
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