package com.softwarefactorymm.notesofbooks.ui.main


import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide

import com.softwarefactorymm.notesofbooks.R
import com.softwarefactorymm.notesofbooks.utils.FontUtil
import com.softwarefactorymm.notesofbooks.viewmodel.PostHolder
import kotlinx.android.synthetic.main.fragment_post_details.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class PostDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_details, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val circularProgressDrawable = CircularProgressDrawable(activity!!)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f

        circularProgressDrawable.start()
        val o = Html.fromHtml(PostHolder.body).toString()
        val p = o.replace("￼","")
        tvDetailsTitle.text = FontUtil.vo(PostHolder.title)
        tvDetailsBody.text = FontUtil.vo(p)
        tvDetailDate.text = FontUtil.vo(PostHolder.date)
        Glide.with(activity).load(PostHolder.imageurl).placeholder(circularProgressDrawable).into(imgPostDetails)
    }


}
