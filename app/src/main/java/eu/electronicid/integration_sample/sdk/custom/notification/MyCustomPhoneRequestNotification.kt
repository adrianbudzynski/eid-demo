package eu.electronicid.sdk.test.ui.custom.notification

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import eu.electronicid.integration_sample.databinding.FragmentMyCustomPhoneRequestNotificationBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.Target
import eu.electronicid.sdk.base.ui.notification.customizable.CustomNotificationPhoneRequestFragment
import eu.electronicid.sdk.base.util.extension.dismissKeyBoard
import eu.electronicid.sdk.base.util.extension.showKeyBoard
import eu.electronicid.sdk.domain.model.videoid.event.notification.InputType
import eu.electronicid.sdk.domain.model.videoid.event.notification.Item
import eu.electronicid.sdk.domain.model.videoid.event.notification.Prefix
import eu.electronicid.sdk.test.ui.custom.fragment.MyCustomCountriesDialogFragment
import java.util.*

class MyCustomPhoneRequestNotification : CustomNotificationPhoneRequestFragment() {
    private var _binding: FragmentMyCustomPhoneRequestNotificationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyCustomPhoneRequestNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    private lateinit var prefix: String

    private lateinit var prefixes: List<Prefix>

    private var telephoneLength = -1

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        prefixes = notificationData.prefix.sortedBy { it.countryName }
        val defaultPrefix =
            prefixes.find { it.countryName == Locale.getDefault().country } ?: prefixes[0]


        binding.title.text = notificationData.title
        binding.description.text = notificationData.description

        Glide.with(requireActivity()).load(defaultPrefix.flagIcon).override(Target.SIZE_ORIGINAL)
            .transition(
                DrawableTransitionOptions.withCrossFade()
            )
            .into(binding.countryFlag)

        prefix = defaultPrefix.code
        telephoneLength = defaultPrefix.length
        binding.country.text = String.format("%s %s", defaultPrefix.countryName, defaultPrefix.code)

        binding.countryView.setOnClickListener {
            MyCustomCountriesDialogFragment.newInstance().apply {
                this.countries = prefixes.map {
                    Item(
                        String.format("%s %s", it.countryName, it.code),
                        it.code,
                        it.flagIcon
                    )
                }

                this.listener = { item ->
                    binding.country.text = item.name
                    prefix = item.key

                    prefixes.find { it.code == item.key }?.run {
                        binding.numberPhone.hint = placeHolder
                        binding.numberPhone.filters = arrayOf<InputFilter>(
                            InputFilter.LengthFilter(
                                length
                            )
                        )
                        telephoneLength = length
                    }

                    Glide.with(requireActivity()).load(item.icon).override(Target.SIZE_ORIGINAL)
                        .transition(
                            DrawableTransitionOptions.withCrossFade()
                        )
                        .into(binding.countryFlag)
                }
            }.show(childFragmentManager, "TAG")
        }

        binding.numberPhone.inputType = when (notificationData.inputType) {
            InputType.numeric -> android.text.InputType.TYPE_CLASS_NUMBER
            InputType.alphanumeric -> android.text.InputType.TYPE_CLASS_TEXT
            InputType.alpha -> android.text.InputType.TYPE_CLASS_TEXT
        }

        binding.numberPhone.hint = defaultPrefix.placeHolder
        binding.numberPhone.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(defaultPrefix.length))
        binding.numberPhone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val fill = s.trim().length == telephoneLength
                binding.cont.isEnabled = fill
                if (fill) {
                    activity?.dismissKeyBoard(binding.numberPhone)
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        binding.cont.setOnClickListener {
            feedback("$prefix${binding.numberPhone.text.trim()}")
        }

        requireActivity().showKeyBoard(binding.numberPhone)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = MyCustomPhoneRequestNotification()
    }
}