package com.softwarefactorymm.notesofbooks.blognetwork

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServerRequest {
    companion object {
        inline fun <reified T> retrofitCli(): T {
            val ret = Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/blogger/v3/blogs/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(T::class.java)
            return ret
        }
    }
}