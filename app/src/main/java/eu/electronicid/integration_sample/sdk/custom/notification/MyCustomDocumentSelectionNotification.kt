package eu.electronicid.sdk.test.ui.custom.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import eu.electronicid.integration_sample.R
import eu.electronicid.integration_sample.databinding.FragmentMyCustomDocumentSelectionNotificationBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.Target
import eu.electronicid.sdk.base.ui.notification.customizable.CustomNotificationDocumentSelectionFragment
import eu.electronicid.sdk.base.util.extension.dismissKeyBoard
import eu.electronicid.sdk.domain.model.videoid.event.notification.Item
import eu.electronicid.sdk.test.ui.custom.adapter.MyCustomArrayAdapterSpinner
import eu.electronicid.sdk.test.ui.custom.fragment.MyCustomCountriesDialogFragment
import java.util.*

class MyCustomDocumentSelectionNotification : CustomNotificationDocumentSelectionFragment() {
    private var _binding: FragmentMyCustomDocumentSelectionNotificationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyCustomDocumentSelectionNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }


    private lateinit var documents: List<Item>

    var idType: Int = -1

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val countries = notificationData.countries.sortedBy { it.name }
        val defaultCountry =
            countries.find { it.key == Locale.getDefault().isO3Country } ?: countries[0]
        val index = countries.indexOf(defaultCountry)

        binding.welcomeText.text = notificationData.title

        binding.selectText.text = notificationData.description

        binding.selectCountry.text = notificationData.countrySelector
        binding.selectDocument.text = notificationData.documentSelector

        Glide.with(requireActivity()).load(countries[index].icon).override(Target.SIZE_ORIGINAL)
            .transition(
                DrawableTransitionOptions.withCrossFade()
            )
            .into(binding.countryFlag)

        documents =
            notificationData.documents.filter { it.key == countries[index].key }
                .sortedBy {
                    val splits = it.name.split("-")
                    splits[splits.size - 1].toInt()
                }

        binding.spinnerDocuments.adapter = MyCustomArrayAdapterSpinner(
            requireContext(),
            R.layout.my_custom_item_spinner,
            R.layout.my_custom_view_drop_spinner,
            documents
        )

        binding.country.text = defaultCountry.name

        binding.countryView.setOnClickListener {
            MyCustomCountriesDialogFragment.newInstance().apply {
                this.countries = countries
                this.listener = { item ->
                    documents =
                        notificationData.documents
                            .filter {
                                it.key == item.key
                            }
                            .sortedBy {
                                val splits = it.name.split("-")
                                splits[splits.size - 1].toInt()
                            }

                    binding.country.text = item.name

                    Glide.with(requireActivity()).load(item.icon).override(Target.SIZE_ORIGINAL)
                        .transition(
                            DrawableTransitionOptions.withCrossFade()
                        )
                        .into(binding.countryFlag)

                    binding.spinnerDocuments.adapter = MyCustomArrayAdapterSpinner(
                        requireContext(),
                        R.layout.my_custom_item_spinner,
                        R.layout.my_custom_view_drop_spinner,
                        documents
                    )
                }
            }.show(childFragmentManager, "TAG")
        }


        Glide.with(requireActivity()).load(documents[0].icon).override(Target.SIZE_ORIGINAL)
            .transition(
                DrawableTransitionOptions.withCrossFade()
            )
            .into(binding.documentIcon)

        binding.spinnerDocuments.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    val splits = documents[position].name.split("-")

                    binding.document.text = splits[0].trimEnd()
                    idType = splits[splits.size - 1].toInt()

                    Glide.with(activity!!).load(documents[position].icon)
                        .override(Target.SIZE_ORIGINAL).transition(
                            DrawableTransitionOptions.withCrossFade()
                        )
                        .into(binding.documentIcon)

                    fullScreen()
                }
            }

        binding.cont.setOnClickListener {
            sendAckActionButton(idType)
        }

        requireActivity().dismissKeyBoard(binding.root)
    }

    private fun fullScreen() {
        activity?.window?.decorView?.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = MyCustomDocumentSelectionNotification()
    }
}