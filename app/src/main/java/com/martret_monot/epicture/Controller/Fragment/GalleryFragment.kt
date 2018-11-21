package com.martret_monot.epicture.Controller.Fragment

import android.content.Intent
import android.drm.DrmStore.DrmObjectType.CONTENT
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.martret_monot.epicture.Client.ImgurClient
import com.martret_monot.epicture.Controller.DetailPhoto
import com.martret_monot.epicture.Models.Content
import com.martret_monot.epicture.Models.ImgurResponse
import com.martret_monot.epicture.Preference
import com.martret_monot.epicture.R
import retrofit2.Call
import retrofit2.Response

class GalleryFragment: Fragment() {

    var rvGallery: RecyclerView? = null
    var imageList: List<Content>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_gallery, container, false)

        rvGallery = view.findViewById(R.id.recryclerView_gallery) as RecyclerView
        rvGallery?.layoutManager = LinearLayoutManager(this.requireContext())


        fetchGalleryuser()
        return view
    }

    fun fetchGalleryuser() {
        val client = ImgurClient.create()
        val call = client.getUploadedImage(Preference(this.requireContext()).getAccountUsername(),
            "Bearer " + Preference(this.requireContext()).getAccessToken(),
            "Epicture"
        )

        call.enqueue(object : retrofit2.Callback<ImgurResponse> {

            override fun onResponse(call: Call<ImgurResponse>, response: Response<ImgurResponse>) {
                val body = response.body()

                imageList = body?.data
                rvGallery?.adapter = GalleryAdapter(imageList, requireContext())
                rvGallery?.adapter?.notifyDataSetChanged()
                print(body)
            }

            override fun onFailure(call: Call<ImgurResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Failed request to fetch gallery user", Toast.LENGTH_SHORT).show()
            }
        })

    }
}
