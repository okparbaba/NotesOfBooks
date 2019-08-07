package com.softwarefactorymm.notesofbooks

import android.app.Application
import com.facebook.ads.AudienceNetworkAds

class NotesOfBooksApplication :Application(){
    override fun onCreate() {
        super.onCreate()
        AudienceNetworkAds.initialize(this)
    }
}