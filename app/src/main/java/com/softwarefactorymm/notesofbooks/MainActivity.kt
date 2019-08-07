package com.softwarefactorymm.notesofbooks

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.softwarefactorymm.notesofbooks.ui.main.SectionsPagerAdapter
import com.softwarefactorymm.notesofbooks.utils.FontUtil.Companion.isValid
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        val currentLocale = Locale.getDefault().language
        isValid = if (currentLocale.toString() == "my") {
            setLocale("my")
            true
        } else {
            setLocale("zy")
            false
        }

    }
    private fun setLocale(lang: String) {
        val locale = Locale(lang)
        val config = baseContext.resources.configuration
        Locale.setDefault(locale)
        config.setLocale(locale)
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
    }
}