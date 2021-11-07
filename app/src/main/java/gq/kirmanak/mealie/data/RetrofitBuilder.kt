package gq.kirmanak.mealie.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Inject

@ExperimentalSerializationApi
class RetrofitBuilder @Inject constructor(private val okHttpBuilder: OkHttpBuilder) {
    fun buildRetrofit(baseUrl: String): Retrofit {
        Timber.v("buildRetrofit() called with: baseUrl = $baseUrl")
        val url = if (baseUrl.startsWith("http")) baseUrl else "https://$baseUrl"
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpBuilder.buildOkHttp())
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
    }
}