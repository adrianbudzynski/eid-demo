package eu.electronicid.integration_sample.sdk

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import eu.electronicid.integration_sample.databinding.ActivityMainBinding
import eu.electronicid.sdk.base.model.Environment
import eu.electronicid.sdk.base.ui.base.VideoIdServiceActivity
import eu.electronicid.sdk.ui.smileid.SmileIDActivity
import eu.electronicid.sdk.ui.videoid.VideoIDActivity
import eu.electronicid.sdk.ui.videoscan.VideoScanActivity
import java.net.URL

const val REQUEST_CODE = 1

class MainActivity : AppCompatActivity() {

    private val endpoint = URL("https://etrust-dev.electronicid.eu/v2/")

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.buttonVideoidSubstantial.setOnClickListener {
            startActivityForResult(Intent(this, VideoIDActivity::class.java).apply {
                putExtra(VideoIDActivity.ENVIRONMENT, Environment(endpoint, "PXBntiIvcfl1R0VrNk6woI9CBgOiC_Ghg8-tpyGwBsgaWL9eTyA26vh-OlFPsXfvjgiLcxJU_l3Fxr3v-lfNRhAdAiIYRlwcCrv3uNrSjqM="))
                putExtra(VideoScanActivity.LANGUAGE, "en")
                putExtra(VideoIDActivity.ID_DOCUMENT, 62)
            }, REQUEST_CODE)
        }

        binding.buttonVideoidMedium.setOnClickListener {
            startActivityForResult(Intent(this, VideoScanActivity::class.java).apply {
                putExtra(VideoScanActivity.ENVIRONMENT, Environment(endpoint, "PXBntiIvcfl1R0VrNk6woI9CBgOiC_Ghg8-tpyGwBsgaWL9eTyA26vh-OlFPsXfvjgiLcxJU_l3Fxr3v-lfNRhAdAiIYRlwcCrv3uNrSjqM="))
                putExtra(VideoScanActivity.LANGUAGE, "en")
                putExtra(VideoScanActivity.ID_DOCUMENT, 62)
            }, REQUEST_CODE)
        }

        binding.buttonSmileid.setOnClickListener {
            startActivityForResult(Intent(this, SmileIDActivity::class.java).apply {
                putExtra(SmileIDActivity.ENVIRONMENT, Environment(endpoint, "PXBntiIvcfl1R0VrNk6woI9CBgOiC_Ghg8-tpyGwBsgaWL9eTyA26vh-OlFPsXfvjgiLcxJU_l3Fxr3v-lfNRhAdAiIYRlwcCrv3uNrSjqM="))
                putExtra(VideoScanActivity.LANGUAGE, "en")
            }, REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                data?.run {
                    val videoId = getStringExtra(VideoIdServiceActivity.RESULT_OK)
                    Toast.makeText(this@MainActivity, "Video OK: $videoId", Toast.LENGTH_LONG).show()
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                data?.run {
                    val errorId = getStringExtra(VideoIdServiceActivity.RESULT_ERROR_CODE)
                    val errorMsg = getStringExtra(VideoIdServiceActivity.RESULT_ERROR_MESSAGE)

                    Toast.makeText(this@MainActivity, "Video ERROR id: $errorId, msg: $errorMsg", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}