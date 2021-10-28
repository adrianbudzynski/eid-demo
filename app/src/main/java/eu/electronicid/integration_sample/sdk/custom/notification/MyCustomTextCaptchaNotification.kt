package eu.electronicid.sdk.test.ui.custom.notification

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.Target
import eu.electronicid.integration_sample.databinding.FragmentMyCustomTextCaptchaNotificationBinding
import eu.electronicid.sdk.base.ui.notification.customizable.CustomNotificationTextCaptchaFragment
import eu.electronicid.sdk.base.util.extension.dismissKeyBoard
import eu.electronicid.sdk.base.util.extension.showKeyBoard
import eu.electronicid.sdk.domain.model.videoid.event.notification.InputType

class MyCustomTextCaptchaNotification : CustomNotificationTextCaptchaFragment() {
    private var _binding: FragmentMyCustomTextCaptchaNotificationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyCustomTextCaptchaNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        Glide.with(requireActivity()).load(notificationData.image).override(Target.SIZE_ORIGINAL)
            .transition(DrawableTransitionOptions.withCrossFade()).into(binding.image)
        binding.description.text = notificationData.description
        binding.cont.text = notificationData.textButton

        binding.input.inputType = when (notificationData.inputType) {
            InputType.numeric -> android.text.InputType.TYPE_CLASS_NUMBER
            InputType.alphanumeric -> android.text.InputType.TYPE_CLASS_TEXT
            InputType.alpha -> android.text.InputType.TYPE_CLASS_TEXT
        }

        binding.input.setOnClickListener {
            requireActivity().showKeyBoard(it)
        }


        binding.cont.setOnClickListener {
            sendAckActionButton(binding.input.text.toString())
        }

        binding.input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                val fill = s.toString().length == notificationData.lengthOtp
                binding.cont.isEnabled = fill
                if (fill) {
                    activity?.dismissKeyBoard(binding.input)
                }
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
            }
        })

        requireActivity().showKeyBoard(binding.input)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = MyCustomTextCaptchaNotification()
    }
}