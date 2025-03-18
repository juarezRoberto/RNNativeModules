package com.upax.zemytalents.di

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.upax.zcservicecoordinator.expose.ZCSCApiClientBuilderV2
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal object ZEMTApiServiceProvider {

    @VisibleForTesting
    val okHttpClientTest by lazy { OkHttpClient.Builder().build() }

    fun <Api> buildTestApiService(
        apiInterface: Class<Api>,
    ): Api {
        return Retrofit.Builder()
            .baseUrl("http://localhost:8080/")
            .client(okHttpClientTest)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(apiInterface)
    }

    fun <Api> provideApiServiceV2(
        context: Context,
        apiInterface: Class<Api>,
        baseUrl: String,
        timeout: Long = 30L,
        allowSSl: Boolean = false
    ): Api {
        return ZCSCApiClientBuilderV2(context, apiInterface)
            .setBaseUrl(baseUrl)
            .setConnectTimeout(timeout)
            .setReadTimeout(timeout)
            .setWriteTimeout(timeout)
            .setAllowSSL(allowSSl)
            .setCheckNetworkConnection(false)
            .build()
    }
}