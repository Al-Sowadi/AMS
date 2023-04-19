package com.abdullah.ams

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.viewpager.widget.ViewPager
import com.example.ams.MyFragmentPagerAdapterTeacher
import com.google.android.material.tabs.TabLayout

class teacher_notiv : AppCompatActivity() {
    lateinit var tabLayoutT: TabLayout
    lateinit var viewPagerT: ViewPager
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_notiv)

        var actionBar = getSupportActionBar()

        if (actionBar != null) {

            // Customize the back button
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24);

            // showing the back button in action bar
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        tabLayoutT = findViewById(R.id.tabLayoutT)
        viewPagerT = findViewById(R.id.viewPagerT)
        tabLayoutT.addTab(tabLayoutT.newTab().setText("Individual"))
        tabLayoutT.addTab(tabLayoutT.newTab().setText("To Teacher"))
        tabLayoutT.addTab(tabLayoutT.newTab().setText("All"))

        tabLayoutT.tabGravity = TabLayout.GRAVITY_FILL
        val adapter = MyFragmentPagerAdapterTeacher(this, supportFragmentManager,
            tabLayoutT.tabCount)
        viewPagerT.adapter = adapter
        viewPagerT.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayoutT))
        tabLayoutT.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPagerT.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }
    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
//                Intent(this,teacher::class.java).also { startActivity(it) }
                finish()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

}