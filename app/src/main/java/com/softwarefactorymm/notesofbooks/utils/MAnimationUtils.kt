package com.softwarefactorymm.notesofbooks.utils

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.softwarefactorymm.notesofbooks.R

class MAnimationUtils {
    companion object{
        fun clickFadeIn(context:Context,view:View){
            val fadeIn:Animation = AnimationUtils.loadAnimation(context, R.anim.button_fade_in)
            view.startAnimation(fadeIn)
        }
    }


}