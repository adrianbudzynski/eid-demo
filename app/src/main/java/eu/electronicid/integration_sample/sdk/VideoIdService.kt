package eu.electronicid.integration_sample.sdk

import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface VideoIdService {
    @POST("https://etrust-sandbox.electronicid.eu/v2/videoid.request")
    fun requestVideoId(
        @Body body: VideoIdRequest,
        @Header("Authorization") authorization: String
    ): Single<VideoIdResponse>
}