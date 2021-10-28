package eu.electronicid.sdk.test.ui.custom.notification

import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import eu.electronicid.integration_sample.databinding.FragmentMyCustomWarningNotificationBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.Target
import eu.electronicid.sdk.base.ui.notification.customizable.CustomNotificationWarningFragment
import eu.electronicid.sdk.domain.model.videoid.event.notification.classification.NotificationWarningType

class MyCustomWarningNotification : CustomNotificationWarningFragment() {
    private var _binding: FragmentMyCustomWarningNotificationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyCustomWarningNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.text.text = when (notificationData.type) {
            NotificationWarningType.NONE -> "Notificacion warning"
        }

        binding.title.text =  notificationData.title

        binding.description.visibility = View.VISIBLE
        binding.description.text = notificationData.description

        notificationData.image?.run {
            binding.image.visibility = View.VISIBLE
            Glide.with(requireActivity()).load(this)
                .override(Target.SIZE_ORIGINAL)
                .transition(DrawableTransitionOptions.withCrossFade()).into(binding.image)
        }

        notificationData.textButton?.run {
            binding.actionButton.visibility = View.VISIBLE
            binding.actionButton.text = this
            binding.actionButton.setOnClickListener {
                sendAckActionButton()
            }
        }

        notificationData.textFooter?.run {
            binding.footer.visibility = View.VISIBLE
            binding.footer.text = this
            notificationData.actionFooter?.run {
                sendAckActionFooter()
            }
        }

        notificationData.closeButton?.run {
            val content = SpannableString(this)
            content.setSpan(UnderlineSpan(), 0, this.length, 0)
            binding.closeAction.text = content
            binding.closeAction.visibility = View.VISIBLE
            binding.closeAction.setOnClickListener {
                sendAckActionClose()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = MyCustomWarningNotification()
    }
}