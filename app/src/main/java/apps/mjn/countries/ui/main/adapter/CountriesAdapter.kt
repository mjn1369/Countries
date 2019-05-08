package apps.mjn.countries.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import apps.mjn.countries.R
import apps.mjn.domain.entity.Country

class CountriesAdapter(
    private var postItems: ArrayList<Country>,
    private val onItemClick: (Country) -> (Unit)
) : RecyclerView.Adapter<CountryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_country, parent, false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(postItems[position], onItemClick)
    }

    fun setItems(data: List<Country>){
        postItems.clear()
        postItems.addAll(data)
        notifyDataSetChanged()
    }

    override fun getItemCount() = postItems.size
}