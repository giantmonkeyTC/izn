package com.weno.izn.network

import com.weno.izn.BuildConfig
import com.weno.izn.network.services.IdentityService
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory


class NetworkClient {
    private val client: OkHttpClient by lazy {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level =
            if (BuildConfig.BUILD_TYPE.equals(
                    "debug",
                    true
                )
            ) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        OkHttpClient.Builder().addInterceptor(interceptor).callTimeout(5, TimeUnit.MINUTES).build()
    }

    private val retrofit = Retrofit
        .Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .baseUrl(NetworkConfig.baseUrl)
        .client(client)
        .build()

    val identityService: IdentityService = retrofit.create(IdentityService::class.java)

}