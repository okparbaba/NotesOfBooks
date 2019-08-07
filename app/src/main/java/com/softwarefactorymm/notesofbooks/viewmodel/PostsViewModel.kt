package com.softwarefactorymm.notesofbooks.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.softwarefactorymm.notesofbooks.blogmodel.Item
import com.softwarefactorymm.notesofbooks.blogmodel.Posts
import com.softwarefactorymm.notesofbooks.blognetwork.ServerRequest.Companion.retrofitCli
import com.softwarefactorymm.notesofbooks.blognetwork.WebService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostsViewModel : ViewModel() {
    private var postList: MutableLiveData<List<Item>>? = null

    val postsViewModel: MutableLiveData<List<Item>>
        get() {
            if (postList == null) {
                postList = MutableLiveData()
                loadPosts()
            }
            return postList!!
        }

    private fun loadPosts() {
        retrofitCli<WebService>()
            .getAllPost("AIzaSyCeJxIoWvkyxv1N__8EiFN8rrEIoG0aj-k")
            .enqueue(object : Callback<Posts> {
                override fun onFailure(call: Call<Posts>, t: Throwable) {}
                override fun onResponse(call: Call<Posts>, response: Response<Posts>) {
                    if (response.isSuccessful) {
                        postsViewModel.value = response.body()?.items
                    }
                }

            })
    }


}
