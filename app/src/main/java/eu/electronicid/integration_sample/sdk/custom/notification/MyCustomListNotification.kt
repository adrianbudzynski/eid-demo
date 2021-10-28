package eu.electronicid.sdk.test.ui.custom.notification

import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.Target
import eu.electronicid.integration_sample.databinding.FragmentMyCustomListNotificationBinding
import eu.electronicid.sdk.base.ui.notification.customizable.CustomNotificationListFragment

class MyCustomListNotification : CustomNotificationListFragment() {
    private var _binding: FragmentMyCustomListNotificationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyCustomListNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        binding.title.text = notificationData.title

        notificationData.description?.run {
            binding.description.visibility = View.VISIBLE
            binding.description.text = this
        }

        notificationData.info.forEachIndexed { index, textIcon ->
            when (index) {
                0 -> {
                    binding.info1.visibility = View.VISIBLE
                    if (textIcon.icon.isNotEmpty()) {
                        Glide.with(requireActivity()).load(textIcon.icon)
                            .override(Target.SIZE_ORIGINAL)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(binding.image1)
                    }
                    binding.text1.text = textIcon.text
                }
                1 -> {
                    binding.info2.visibility = View.VISIBLE
                    if (textIcon.icon.isNotEmpty()) {
                        Glide.with(requireActivity()).load(textIcon.icon)
                            .override(Target.SIZE_ORIGINAL)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(binding.image2)
                    }
                    binding.text2.text = textIcon.text
                }
                2 -> {
                    binding.info3.visibility = View.VISIBLE
                    if (textIcon.icon.isNotEmpty()) {
                        Glide.with(requireActivity()).load(textIcon.icon)
                            .override(Target.SIZE_ORIGINAL)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(binding.image3)
                    }
                    binding.text3.text = textIcon.text
                }
            }
        }

        binding.cont.text = notificationData.textButton

        binding.cont.setOnClickListener {
            sendAckActionButton()
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
        fun newInstance() = MyCustomListNotification()
    }
}