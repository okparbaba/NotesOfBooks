package com.softwarefactorymm.notesofbooks.adapter

import android.app.Activity
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.softwarefactorymm.notesofbooks.R
import com.softwarefactorymm.notesofbooks.blogmodel.Item
import com.softwarefactorymm.notesofbooks.ui.main.FragmentsHolderActivity
import com.softwarefactorymm.notesofbooks.ui.main.PostDetailsFragment
import com.softwarefactorymm.notesofbooks.utils.FontUtil.Companion.vo
import com.softwarefactorymm.notesofbooks.utils.MAnimationUtils.Companion.clickFadeIn
import com.softwarefactorymm.notesofbooks.viewmodel.PostHolder
import kotlinx.android.synthetic.main.note_recycler_item.view.*

import java.util.*
import kotlin.collections.ArrayList


class PostListAdapter(var items: ArrayList<Item>, var imageList:ArrayList<String>, val context: Activity) :
    RecyclerView.Adapter<PostListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.note_recycler_item, parent, false))
    }


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = items[position]
        val image = imageList[position]
        holder.setData(post, position,image)
    }

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return imageList.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Holds the TextView that will add each animal to
        var currentPost: Item? = null
        var currentPosition: Int = 0
        var currentImage:String? = null

        init {
            itemView.homeItemImg.setOnClickListener {
                PostHolder.title = currentPost?.title
                PostHolder.body = currentPost?.content?.trim()
                PostHolder.imageurl = currentImage
                PostHolder.date = currentPost?.published

                val intent = Intent(context, FragmentsHolderActivity::class.java)
                val options:ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(context,itemView.homeItemImg as View,"home")
                intent.putExtra(
                    FragmentsHolderActivity.EXTRA_DISPLAY_FRAGMENT,
                    PostDetailsFragment::class.java.simpleName
                )
                context.startActivity(intent,options.toBundle())
            }
            itemView.img_star.setOnClickListener {
                clickFadeIn(context,itemView.img_star)
                itemView.img_star.setImageResource(android.R.drawable.star_big_on)
            }
        }

        @RequiresApi(Build.VERSION_CODES.N)
        fun setData(post: Item?, pos: Int,image:String?) {
            itemView.homeItemTvTitle.text = vo(post!!.title)
            val o = Html.fromHtml(post.content).toString().trim()
            val p = o.replace("￼","")
            itemView.homeItemBody.text = vo(p)
            val now = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format( Date())
            val published = post.published
            val puDate = published.substring(0,10)
            if (puDate == now){
                itemView.homeItemDate.text = "Today"
            }else{
                itemView.homeItemDate.text = vo(puDate)
            }
            val circularProgressDrawable = CircularProgressDrawable(context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f

            circularProgressDrawable.start()
            Glide.with(context).load(image).placeholder(circularProgressDrawable).into(itemView.homeItemImg)
            this.currentPost = post
            this.currentPosition = pos
            this.currentImage = image
        }
    }

    fun searchFilter(newList: ArrayList<Item>) {
        items = ArrayList()
        items.addAll(newList)
        notifyDataSetChanged()
    }
}