package eu.electronicid.integration_sample.sdk

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VideoIdResponse(
    val id: String,
    val authorization: String
)