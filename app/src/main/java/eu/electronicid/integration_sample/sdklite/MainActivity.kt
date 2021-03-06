//package eu.electronicid.integration_sample.sdklite
//
//
//import android.app.Activity
//import android.app.ProgressDialog
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.widget.Toast
//import androidx.appcompat.app.AlertDialog
//import eu.electronicid.integration_sample.databinding.ActivityMainBinding
//import eu.electronicid.sdk.base.certid.CertIDActivity
//import eu.electronicid.sdk.base.model.Environment
//import eu.electronicid.sdk.base.ui.base.VideoIdServiceActivity
//import eu.electronicid.sdk.discriminator.CheckRequirements
//import eu.electronicid.sdklite.ui.smileid.SmileIDActivity
//import eu.electronicid.sdklite.ui.videoid.VideoIDActivity
//import eu.electronicid.sdklite.ui.videoscan.VideoScanActivity
//import java.net.URL
//
//const val REQUEST_CODE = 1
//
//class MainActivity : AppCompatActivity() {
//
//    private val endpoint = URL("https://etrust-dev.electronicid.eu/v2/")
//
//    private var _binding: ActivityMainBinding? = null
//    private val binding get() = _binding!!
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        _binding = ActivityMainBinding.inflate(layoutInflater)
//
//        setContentView(binding.root)
//
//        binding.buttonVideoidSubstantial.setOnClickListener {
//            check {
//                startActivityForResult(Intent(this, VideoIDActivity::class.java).apply {
//                    putExtra(
//                        VideoIDActivity.ENVIRONMENT,
//                        Environment(
//                            endpoint,
//                            "{auth}"
//                        )
//                    )
//                    putExtra(VideoScanActivity.LANGUAGE, "en")
//                    putExtra(VideoIDActivity.ID_DOCUMENT, 62)
//                }, REQUEST_CODE)
//            }
//        }
//
//        binding.buttonVideoidMedium.setOnClickListener {
//            check {
//                startActivityForResult(Intent(this, VideoScanActivity::class.java).apply {
//                    putExtra(
//                        VideoScanActivity.ENVIRONMENT,
//                        Environment(
//                            endpoint,
//                            "{auth}"
//                        )
//                    )
//                    putExtra(VideoScanActivity.LANGUAGE, "en")
//                    putExtra(VideoScanActivity.ID_DOCUMENT, 62)
//                }, REQUEST_CODE)
//            }
//        }
//
//        binding.buttonSmileid.setOnClickListener {
//            check {
//                startActivityForResult(Intent(this, SmileIDActivity::class.java).apply {
//                    putExtra(
//                        SmileIDActivity.ENVIRONMENT,
//                        Environment(
//                            endpoint,
//                            "{auth}"
//                        )
//                    )
//                    putExtra(VideoScanActivity.LANGUAGE, "en")
//                }, REQUEST_CODE)
//            }
//        }
//        binding.buttonCertid.setOnClickListener {
//            startActivityForResult(Intent(this, CertIDActivity::class.java).apply {
//                putExtra(
//                    CertIDActivity.ENVIRONMENT,
//                    Environment(
//                        endpoint,
//                        "{auth}"
//                    )
//                )
//                putExtra(CertIDActivity.LANGUAGE, "en")
//            }, REQUEST_CODE)
//        }
//    }
//
//    @Suppress("DEPRECATION")
//    private fun check(launchService: () -> Unit) {
//        val progress: ProgressDialog = ProgressDialog.show(this, "Checking Requirements", "")
////        CheckRequirements.getInstance(this).checkVideoScan(...)
////        CheckRequirements.getInstance(this).checkSmileID(...)
//        CheckRequirements.getInstance(this).checkVideoID(endpoint, {
//            progress.setMessage("$it/10")
//        }) { result ->
//            progress.dismiss()
//            if (result.passed) {
//                launchService()
//            } else {
//                AlertDialog.Builder(this)
//                    .setCancelable(false)
//                    .setMessage("Requirements for VideoID not passed, please, try another onboarding solution")
//                    .setPositiveButton("Ok", null)
//                    .show()
//
//            }
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == REQUEST_CODE) {
//            if (resultCode == Activity.RESULT_OK) {
//                data?.run {
//                    val videoId = getStringExtra(VideoIdServiceActivity.RESULT_OK)
//                    Toast.makeText(this@MainActivity, "Video OK: $videoId", Toast.LENGTH_LONG)
//                        .show()
//                }
//            } else if (resultCode == Activity.RESULT_CANCELED) {
//                data?.run {
//                    val errorId = getStringExtra(VideoIdServiceActivity.RESULT_ERROR_CODE)
//                    val errorMsg = getStringExtra(VideoIdServiceActivity.RESULT_ERROR_MESSAGE)
//
//                    Toast.makeText(
//                        this@MainActivity,
//                        "Video ERROR id: $errorId, msg: $errorMsg",
//                        Toast.LENGTH_LONG
//                    ).show()
//                }
//            }
//        }
//    }
//}