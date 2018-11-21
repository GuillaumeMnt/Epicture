package com.martret_monot.epicture.Controller.Fragment

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.martret_monot.epicture.Models.ImageLib
import com.martret_monot.epicture.R
import com.bumptech.glide.request.RequestOptions
import com.martret_monot.epicture.Client.ImgurClient
import com.martret_monot.epicture.Controller.DetailPhoto
import com.martret_monot.epicture.Controller.HomeVC
import com.martret_monot.epicture.Models.Content
import com.martret_monot.epicture.Models.ImgurResponse
import com.martret_monot.epicture.Models.UploadResponse
import com.martret_monot.epicture.Preference
import com.martret_monot.epicture.R.id.sort
import kotlinx.android.synthetic.main.detail_photo.view.*
import kotlinx.android.synthetic.main.gallery_row.view.*
import retrofit2.Call
import retrofit2.Callback

class GalleryAdapter(val contentData: List<Content>?, val context: Context): RecyclerView.Adapter<CustomGalleryViewHolder>() {

    val mContext: Context = context
    val imageList: List<Content>? = contentData

    override fun getItemCount(): Int {
        if (imageList?.size == null)
            return 0
        return imageList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomGalleryViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.gallery_row, parent, false)

        return CustomGalleryViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomGalleryViewHolder, position: Int) {
        val content: Content? = imageList?.get(position)

        val favoriteButton = holder.view.findViewById(R.id.deleteButton) as ImageView
        favoriteButton.setOnClickListener { callDeleteAction(content, holder.view, position) }


        val optionsImage = RequestOptions().fitCenter()

            if (content?.type == "image/gif") {
                Glide.with(mContext)
                    .asGif()
                    .load(content?.link)
                    .apply(optionsImage)
                    .into(holder?.view.imageViewGallery)

           } else {
                Glide.with(mContext)
                    .load(content?.link)
                    .apply(optionsImage)
                    .into(holder.view.imageViewGallery)
            }
        holder.view.galleryTextView.text = content?.title
        holder.view.descriptionTextView.text = content?.description

    }

    fun callDeleteAction(image: Content?, view: View, position: Int) {

        val id = image?.id

        if (id == null) {
            Toast.makeText(mContext, "Problem while deleting an image", Toast.LENGTH_SHORT).show()
            return
        }

        val client = ImgurClient.create()
        val call = client.deleteImage(id,"Bearer " + Preference(mContext).getAccessToken(), "Epicture")

        call.enqueue(object: Callback<UploadResponse> {
            override fun onResponse(call: Call<UploadResponse>, response: retrofit2.Response<UploadResponse>) {
                val body = response.body()

                if (body?.data == "true")
                    Toast.makeText(mContext,"image successfully deleted", Toast.LENGTH_SHORT).show()
                notifyDataSetChanged()
                val randomIntent = Intent(mContext, HomeVC::class.java)
                ContextCompat.startActivity(mContext, randomIntent, null)
            }

            override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                println("REQUEST FAILED")
                println(t.message)
            }
        })
    }

}

class CustomGalleryViewHolder(val view: View): RecyclerView.ViewHolder(view) {


}