package eu.electronicid.integration_sample.sdk

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VideoIdRequest(
    val process: String = "Unattended"
)