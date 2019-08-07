package com.softwarefactorymm.notesofbooks.ui.main


import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.softwarefactorymm.notesofbooks.R
import com.softwarefactorymm.notesofbooks.adapter.PostListAdapter
import com.softwarefactorymm.notesofbooks.blogmodel.Item
import com.softwarefactorymm.notesofbooks.viewmodel.PostsViewModel
import kotlinx.android.synthetic.main.fragment_notes.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.regex.Pattern

class FragmentNotes : Fragment() {
    private lateinit var newsListAdapter: PostListAdapter
    private var posts = ArrayList<Item>()
    private var images = ArrayList<String>()
    private lateinit var manager: LinearLayoutManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        OkInternet()
        rv_note?.alpha = 0f
        spin_kit.visibility = VISIBLE
        manager = LinearLayoutManager(activity)

        getBlogPosts()
    }
    fun OkInternet() {
        doAsync {
            val connectivityManager: ConnectivityManager =
                activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            if (!(networkInfo != null && networkInfo.isConnected)) {
                uiThread {
                    tvNoInternet?.visibility = VISIBLE
                }
            } else {
                uiThread {
                    tvNoInternet?.visibility = GONE
                }
            }
        }
    }
    private fun getBlogPosts() {
        newsListAdapter = PostListAdapter(posts, images, activity!!)
        rv_note?.adapter = newsListAdapter
        val model = ViewModelProviders.of(activity!!).get(PostsViewModel::class.java)
        model.postsViewModel.observe(this,
            Observer<List<Item>> { psts ->
                val pattern =
                    Pattern.compile("\\s*(?i)href\\s*=\\s*(\"([^\"]*\")|'[^']*'|([^'\">\\s]+))")
                for (i in psts) {
                    val o = i.content

                    val r = pattern.matcher(o)
                    while (r.find()) {
                        val result = r.group(1)
                        val re = result.replace("\"", "")
                        images.add(re)

                    }


                }
                posts.addAll(psts)

                spin_kit?.animate()?.alpha(0f)?.setDuration(500)?.start()
                rv_note?.animate()?.alpha(1f)?.setDuration(500)?.start()

                rv_note?.layoutManager = manager


            })
    }

    }
