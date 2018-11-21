package com.martret_monot.epicture.Controller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.martret_monot.epicture.Controller.Fragment.PhotoAdapter
import com.martret_monot.epicture.Models.Content
import com.martret_monot.epicture.Models.ImageLib
import com.martret_monot.epicture.R

class DetailPhoto : AppCompatActivity() {

    var rvMain: RecyclerView? = null
    var imageList: List<ImageLib>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_photo)

        supportActionBar?.show()
        supportActionBar?.setDisplayUseLogoEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        rvMain = findViewById(R.id.recyclerView_detail)
        rvMain?.layoutManager = LinearLayoutManager(baseContext)

        val content: Content = intent.extras.get("content") as Content
        rvMain?.adapter = PhotoAdapter(content, context = baseContext)

        print(content)
    }
}
