package eu.electronicid.sdk.test.ui.custom.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.Target
import eu.electronicid.integration_sample.databinding.FragmentMyCustomNotificationModalBinding
import eu.electronicid.sdk.base.ui.notification.customizable.CustomNotificationModalFragment

class MyCustomModalNotification : CustomNotificationModalFragment() {
    private var _binding: FragmentMyCustomNotificationModalBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyCustomNotificationModalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        notificationData.image?.run {
            binding.image.visibility = View.VISIBLE
            Glide.with(requireActivity()).load(this)
                .override(Target.SIZE_ORIGINAL)
                .transition(DrawableTransitionOptions.withCrossFade()).into(binding.image)
        }

        notificationData.title?.run {
            binding.title.visibility = View.VISIBLE
            binding.title.text = this
        }


        notificationData.description?.run {
            binding.description.visibility = View.VISIBLE
            binding.description.text = this
        }

        binding.actionButton.text = notificationData.textButton

        binding.actionButton.setOnClickListener {
            sendAckActionButton()
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = MyCustomModalNotification()
    }
}