package com.softwarefactorymm.notesofbooks.blognetwork

import com.softwarefactorymm.notesofbooks.blogmodel.Posts
import retrofit2.Call
import retrofit2.http.*

interface WebService {
    @GET("2691411289485537056/posts")
    fun getAllPost(@Query("key") k:String):Call<Posts>


}