package gq.kirmanak.mealie.data.impl

import gq.kirmanak.mealie.data.auth.impl.AuthOkHttpInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import javax.inject.Inject

class OkHttpBuilder @Inject constructor() {
    fun buildOkHttp(token: String?): OkHttpClient {
        Timber.v("buildOkHttp() called with: token = $token")
        val builder = OkHttpClient.Builder()
            .addNetworkInterceptor(buildLoggingInterceptor())
        if (token != null) builder.addNetworkInterceptor(AuthOkHttpInterceptor(token))
        return builder.build()
    }

    private fun buildLoggingInterceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor { message -> Timber.tag("OkHttp").v(message) }
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }
}