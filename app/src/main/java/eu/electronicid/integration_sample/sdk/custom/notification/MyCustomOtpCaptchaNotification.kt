package eu.electronicid.sdk.test.ui.custom.notification

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import eu.electronicid.integration_sample.databinding.FragmentMyCustomOtpCaptchaNotificationBinding
import eu.electronicid.sdk.base.ui.notification.customizable.CustomNotificationOtpCaptchaFragment
import eu.electronicid.sdk.base.util.extension.dismissKeyBoard
import eu.electronicid.sdk.base.util.extension.showKeyBoard
import eu.electronicid.sdk.domain.model.videoid.event.notification.InputType

class MyCustomOtpCaptchaNotification : CustomNotificationOtpCaptchaFragment() {
    private var _binding: FragmentMyCustomOtpCaptchaNotificationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyCustomOtpCaptchaNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        binding.description.text = notificationData.description

        binding.footer.text = notificationData.phoneUpdate
        binding.footerAction.text = notificationData.textButton2

        binding.input.inputType = when (notificationData.inputType) {
            InputType.numeric -> android.text.InputType.TYPE_CLASS_NUMBER
            InputType.alphanumeric -> android.text.InputType.TYPE_CLASS_TEXT
            InputType.alpha -> android.text.InputType.TYPE_CLASS_TEXT
        }

        binding.input.setOnClickListener {
            requireActivity().showKeyBoard(it)
        }

        binding.cont.text = notificationData.textButton1
        binding.cont.setOnClickListener {
            sendAckActionButton1(binding.input.text.toString())
        }

        binding.footerAction.setOnClickListener {
            sendAckActionButton2()
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
        fun newInstance() = MyCustomOtpCaptchaNotification()
    }
}