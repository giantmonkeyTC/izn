package com.weno.izn.network.services

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import io.reactivex.rxjava3.core.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.*

interface IdentityService {
//mdzz
    @Multipart
    @POST("upload")
    fun uploadPhoto(
        @Path("channelId") channelId: String,
        @Part file: MultipartBody.Part,
        @Header(
            "Authorization"
        ) token: String
    ): Observable<JsonObject>
}