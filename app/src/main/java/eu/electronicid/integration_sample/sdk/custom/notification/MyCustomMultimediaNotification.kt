package eu.electronicid.sdk.test.ui.custom.notification

import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import eu.electronicid.integration_sample.databinding.FragmentMyCustomMultimediaNotificationBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.Target
import eu.electronicid.sdk.base.ui.notification.customizable.CustomNotificationMultimediaFragment
import eu.electronicid.sdk.domain.model.videoid.event.notification.classification.NotificationMediaType

class MyCustomMultimediaNotification : CustomNotificationMultimediaFragment() {
    private var _binding: FragmentMyCustomMultimediaNotificationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyCustomMultimediaNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.text.text = when (notificationData.type) {
            NotificationMediaType.PRESENTATION -> "Notification de presentación"
            NotificationMediaType.AUDIO_CAPTCHA_HELP -> "Notificacion de ayuda de audio captcha"
            NotificationMediaType.AUDIO_CAPTCHA -> "Notificacion de audio captcha"
            NotificationMediaType.DOCUMENT_FRONT_HELP -> "Notificacion de FRONT"
            NotificationMediaType.DOCUMENT_BACK_HELP -> "Notificacion de BACK"
            NotificationMediaType.FACE_HELP -> "Notificacion de FACE"
            NotificationMediaType.PROCESS_COMPLETED -> "Notification de proceso completado"
            NotificationMediaType.UNKNOWN -> "Notificacion desconocida"
        }

        notificationData.title?.run {
            binding.title.visibility = View.VISIBLE
            binding.title.text = this
        }

        notificationData.description?.run {
            binding.description.visibility = View.VISIBLE
            binding.description.text = this
        }

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
            binding.footer.text = this
            binding.footer.visibility = View.VISIBLE
            binding.footer.setOnClickListener {
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
        fun newInstance() = MyCustomMultimediaNotification()
    }
}