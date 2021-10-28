package eu.electronicid.sdk.test.ui.custom.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import eu.electronicid.sdk.base.R
import eu.electronicid.sdk.domain.model.videoid.event.notification.Item

class MyCustomArrayAdapterSpinner(
    context: Context,
    private val resource: Int,
    private val dropDown: Int,
    countries: List<Item>
) : ArrayAdapter<Item>(context, resource, countries) {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View =
        convertView ?: mInflater.inflate(dropDown, parent, false)

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: mInflater.inflate(resource, parent, false)
        val document = getItem(position)!!

        Glide.with(context).load(document.icon)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view.findViewById(R.id.icon))

        view.findViewById<TextView>(R.id.name).run {
            text = document.name.split("-")[0]
        }

        return view
    }
}