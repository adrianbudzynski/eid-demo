package eu.electronicid.sdk.test.ui.custom.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.Target
import eu.electronicid.integration_sample.R
import eu.electronicid.integration_sample.databinding.CustomDialogContriesBinding
import eu.electronicid.sdk.base.util.extension.dismissKeyBoard
import eu.electronicid.sdk.domain.model.videoid.event.notification.Item
import java.util.*

class MyCustomCountriesDialogFragment : DialogFragment() {

    private var _binding: CustomDialogContriesBinding? = null
    private val binding get() = _binding!!

    lateinit var countries: List<Item>

    var listener: (item: Item) -> Unit = {}


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CustomDialogContriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = CountriesAdapter(requireActivity(), countries)

        binding.listCountries.adapter = adapter

        binding.filterText.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                adapter.filter.filter(query)
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                adapter.filter.filter(query)
                return true
            }
        })

    }

    companion object {
        @JvmStatic
        fun newInstance() = MyCustomCountriesDialogFragment()
    }

    inner class CountriesAdapter(
        context: Context,
        countries: List<Item>
    ) : ArrayAdapter<Item>(context, 0, countries), Filterable {

        private var countriesListFiltered: List<Item>

        init {
            countriesListFiltered = countries
        }

        private val mInflater: LayoutInflater = LayoutInflater.from(context)

        override fun getFilter(): Filter = object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults =
                FilterResults().apply {
                    val text = charSequence.toString()
                    countriesListFiltered = if (text.isEmpty()) countries else countries.filter {
                        it.name.toLowerCase(
                            Locale.ROOT
                        ).contains(text.toLowerCase(Locale.ROOT))
                    }
                    values = countriesListFiltered
                }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                countriesListFiltered = filterResults.values as List<Item>
                notifyDataSetChanged()
            }
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = convertView ?: mInflater.inflate(R.layout.custom_dialog_item, parent, false)
            val item = getItem(position)

            Glide.with(context).load(item.icon).override(Target.SIZE_ORIGINAL).transition(
                DrawableTransitionOptions.withCrossFade()
            ).into(view.findViewById(R.id.icon))

            view.findViewById<TextView>(R.id.name).run {
                text = item.name
            }

            view.setOnClickListener {
                activity!!.dismissKeyBoard(binding.filterText)
                listener(item)
                dismiss()
            }

            return view
        }

        override fun getCount(): Int = countriesListFiltered.size

        override fun getItem(position: Int): Item =
            countriesListFiltered[if (countriesListFiltered.size == position) position - 1 else position]
    }
}