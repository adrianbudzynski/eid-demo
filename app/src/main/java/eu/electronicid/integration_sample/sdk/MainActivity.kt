package eu.electronicid.integration_sample.sdk

import android.R
import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.squareup.moshi.Moshi
import eu.electronicid.integration_sample.databinding.ActivityMainBinding
import eu.electronicid.integration_sample.sdk.custom.CustomVideoIDActivity
import eu.electronicid.sdk.base.certid.CertIDActivity
import eu.electronicid.sdk.base.model.Environment
import eu.electronicid.sdk.base.ui.base.VideoIdServiceActivity
import eu.electronicid.sdk.discriminator.CheckRequirements
import eu.electronicid.sdk.ui.smileid.SmileIDActivity
import eu.electronicid.sdk.ui.videoid.VideoIDActivity
import eu.electronicid.sdk.ui.videoscan.VideoScanActivity
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.URL


const val REQUEST_CODE = 1

class MainActivity : AppCompatActivity() {

    private val endpoint = URL("https://etrust-sandbox.electronicid.eu/v2/")
    private val custom = false

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val service: VideoIdService

    init {

        val moshi = Moshi
            .Builder()
            .build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi).withNullSerialization())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .baseUrl("https://etrust-sandbox.electronicid.eu/v2/")
            .build()

        service = retrofit.create(VideoIdService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val adapter: ArrayAdapter<*> =
            ArrayAdapter(
                this,
                R.layout.simple_spinner_dropdown_item,
                DocumentTypes.list
            )

        binding.documentType.adapter = adapter

        binding.buttonVideoidSubstantial.setOnClickListener {
            check {
                initVideoId {
                    startVideoId(it)
                }
            }
        }

        binding.buttonVideoidMedium.setOnClickListener {
            check {
                initVideoId {
                    startVideoScan(it)
                }
            }
        }

        binding.buttonSmileid.setOnClickListener {
            check {
                initVideoId {
                    startSmileId(it)
                }
            }
        }

        binding.buttonCertid.setOnClickListener {
            initVideoId {
                startCertId(it)
            }
        }
    }

    private fun startCertId(authorization: String) {
        startActivityForResult(Intent(this, CertIDActivity::class.java).apply {
            putExtra(
                CertIDActivity.ENVIRONMENT,
                Environment(endpoint, "$authorization")
            )
            putExtra(CertIDActivity.LANGUAGE, "en")
        }, REQUEST_CODE)
    }

    private fun startSmileId(authorization: String) {
        startActivityForResult(Intent(this, SmileIDActivity::class.java).apply {
            putExtra(
                SmileIDActivity.ENVIRONMENT,
                Environment(
                    endpoint,
                    "$authorization"
                )
            )
            putExtra(VideoScanActivity.LANGUAGE, "en")
        }, REQUEST_CODE)
    }

    private fun startVideoScan(authorization: String) {
        startActivityForResult(Intent(this, VideoScanActivity::class.java).apply {
            putExtra(
                VideoScanActivity.ENVIRONMENT,
                Environment(
                    endpoint,
                    "$authorization"
                )
            )
            putExtra(VideoScanActivity.LANGUAGE, "en")
            putExtra(
                VideoScanActivity.ID_DOCUMENT,
                (binding.documentType.selectedItem as DocumentType).id
            )
        }, REQUEST_CODE)
    }

    private fun startVideoId(authorization: String) {
        startActivityForResult(
            Intent(
                this,
                (if (custom) CustomVideoIDActivity::class.java else VideoIDActivity::class.java)
            ).apply {
                putExtra(
                    VideoIDActivity.ENVIRONMENT,
                    Environment(
                        endpoint,
                        "$authorization"
                    )
                )
                putExtra(VideoScanActivity.LANGUAGE, "en")
                putExtra(
                    VideoIDActivity.ID_DOCUMENT,
                    (binding.documentType.selectedItem as DocumentType).id
                )
            }, REQUEST_CODE
        )
    }

    @Suppress("DEPRECATION")
    private fun check(launchService: () -> Unit) {
        val progress: ProgressDialog = ProgressDialog.show(this, "Checking Requirements", "")
//        CheckRequirements.getInstance(this).checkVideoScan(...)
//        CheckRequirements.getInstance(this).checkSmileID(...)
//        CheckRequirements.getInstance(this).checkCertID(...)

        CheckRequirements.getInstance(this).checkVideoID(endpoint, {
            progress.setMessage("$it/10")
        }) { result ->
            progress.dismiss()
            if (result.passed) {
                launchService()
            } else {
                AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setMessage("Requirements for VideoID not passed, please, try another onboarding solution")
                    .setPositiveButton("Ok", null)
                    .show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                data?.run {
                    val videoId = getStringExtra(VideoIdServiceActivity.RESULT_OK)
                    Toast.makeText(this@MainActivity, "Video OK: $videoId", Toast.LENGTH_LONG)
                        .show()
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                data?.run {
                    val errorId = getStringExtra(VideoIdServiceActivity.RESULT_ERROR_CODE)
                    val errorMsg = getStringExtra(VideoIdServiceActivity.RESULT_ERROR_MESSAGE)

                    Toast.makeText(
                        this@MainActivity,
                        "Video ERROR id: $errorId, msg: $errorMsg",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun initVideoId(action: (String) -> Unit) {
        service.requestVideoId(VideoIdRequest(), "Bearer ")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                action(it.authorization)
            }, {})
    }
}

