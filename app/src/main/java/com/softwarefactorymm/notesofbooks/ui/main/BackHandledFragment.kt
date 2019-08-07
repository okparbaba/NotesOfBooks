
/*
 * Created by Yannaing Lynn(Android Developer) from Myanmar on 3/29/19 2:57 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 3/28/19 2:22 PM
 *
 */

package com.softwarefactorymm.notesofbooks.ui.main


import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

abstract class BackHandledFragment : Fragment() {
    protected lateinit var backHandlerInterface: BackHandlerInterface

    abstract fun onBackPressed(): Boolean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (activity !is BackHandlerInterface) {
            throw ClassCastException(
                "Hosting activity must implement BackHandlerInterface"
            )
        } else {
            backHandlerInterface = activity as BackHandlerInterface
        }
    }

    override fun onStart() {
        super.onStart()

        // Mark this fragment as the selected Fragment.
        backHandlerInterface.setmSelectedFragment(this)
    }

    fun setSupportActionBar(toolbar: Toolbar) {
        val compatActivity = activity as AppCompatActivity
        compatActivity.setSupportActionBar(toolbar)

        val actionBar = compatActivity.supportActionBar
        actionBar?.setDisplayShowTitleEnabled(false)

    }

    fun setStatusBarColor(color: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = activity!!.window
            // clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            // finally change the color

            window.statusBarColor = Color.parseColor(color)
        }
    }

    //    public void setTitle(String title) {
    //        Toolbar toolbar = getActivity().findViewById(R.id.tool_bar);
    //        if (toolbar != null) {
    //            TextView tvTitle = toolbar.findViewById(R.id.tv_toolbar_title);
    //            tvTitle.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/microsoft_jhenghei.ttf"));
    //            tvTitle.setText(title);
    //            tvTitle.setCompoundDrawables(null, null, null, null);
    //            tvTitle.setOnClickListener(null);
    //        }
    //    }

    //    public void setTitle(String title, final OnTitleTextClickListener listener) {
    //        Toolbar toolbar = getActivity().findViewById(R.id.tool_bar);
    //        if (toolbar != null) {
    //            TextView tvTitle = toolbar.findViewById(R.id.tv_toolbar_title);
    //            tvTitle.setText(title);
    //
    //            tvTitle.setCompoundDrawablesWithIntrinsicBounds(null, null,
    //                    ContextCompat.getDrawable(getActivity(), R.mipmap.ic_expand_arrow), null);
    //
    //            tvTitle.setOnClickListener(view -> listener.onTitleTextClicked());
    //        }
    //    }

    fun showActionBar() {
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.show()
    }

    fun hideActionBar() {
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.hide()
    }

    //    public void setDisplayHomeAsUpEnable(boolean status) {
    //        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
    //        if (actionBar != null) {
    //            actionBar.setDisplayHomeAsUpEnabled(status);
    //            actionBar.setHomeButtonEnabled(status);
    //            actionBar.setHomeAsUpIndicator(R.mipmap.ic_cross_white);
    //        }
    //    }

    interface BackHandlerInterface {
        fun setmSelectedFragment(backHandledFragment: BackHandledFragment)
    }

    interface OnTitleTextClickListener {
        fun onTitleTextClicked()
    }
}
