package eu.electronicid.integration_sample.sdk.custom

import android.util.Log
import androidx.fragment.app.Fragment
import eu.electronicid.sdk.base.ui.notification.customizable.*
import eu.electronicid.sdk.domain.model.Phase
import eu.electronicid.sdk.test.ui.custom.notification.*
import eu.electronicid.sdk.ui.videoid.VideoIDActivity

class CustomVideoIDActivity : VideoIDActivity() {
    override fun onPhaseCompleted(phase: Phase) {
        Log.d("aaa", "$phase completed!!")
    }

    override fun onPhaseStarted(phase: Phase) {
        Log.d("aaa", "$phase started!!")
    }

    override fun getCustomMultimediaNotification(): CustomNotificationMultimediaFragment = MyCustomMultimediaNotification.newInstance()
    override fun getCustomErrorNotification(): CustomNotificationErrorFragment = MyCustomErrorNotification.newInstance()
    override fun getCustomWarningNotification(): CustomNotificationWarningFragment = MyCustomWarningNotification.newInstance()
    override fun getCustomTextCaptchaNotification(): CustomNotificationTextCaptchaFragment = MyCustomTextCaptchaNotification.newInstance()
    override fun getCustomOtpCaptchaNotification(): CustomNotificationOtpCaptchaFragment = MyCustomOtpCaptchaNotification.newInstance()
    override fun getCustomPhoneRequestNotification(): CustomNotificationPhoneRequestFragment = MyCustomPhoneRequestNotification.newInstance()
    override fun getCustomListNotification(): CustomNotificationListFragment = MyCustomListNotification.newInstance()
    override fun getCustomModalNotification(): CustomNotificationModalFragment = MyCustomModalNotification.newInstance()
    override fun getCustomDocumentSelectionNotification(): CustomNotificationDocumentSelectionFragment = MyCustomDocumentSelectionNotification.newInstance()
}