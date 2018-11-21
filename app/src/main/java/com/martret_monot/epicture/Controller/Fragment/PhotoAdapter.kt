package com.martret_monot.epicture.Controller.Fragment

import android.content.Context
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
import com.martret_monot.epicture.Models.Content
import com.martret_monot.epicture.Models.ImgurResponse
import com.martret_monot.epicture.Models.UploadResponse
import com.martret_monot.epicture.Preference
import com.martret_monot.epicture.R.id.sort
import kotlinx.android.synthetic.main.detail_photo.view.*
import retrofit2.Call
import retrofit2.Callback

class PhotoAdapter(val contentData: Content?, val context: Context): RecyclerView.Adapter<CustomDetailViewHolder>() {

    val mContext: Context = context
    val imageList: List<ImageLib>? = contentData?.images

    override fun getItemCount(): Int {
        if (imageList?.size == null)
            return 0
        return imageList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomDetailViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.detail_photo, parent, false)

        return CustomDetailViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomDetailViewHolder, position: Int) {
        val content: ImageLib? = imageList?.get(position)

        val favoriteButton = holder.view.findViewById(R.id.favoriteButton) as ImageView
        favoriteButton.setOnClickListener { callFavoriteAction(content, holder.view) }

        val optionsImage = RequestOptions().fitCenter()

            if (content?.type == "image/gif") {
                Glide.with(mContext)
                    .asGif()
                    .load(content?.link)
                    .apply(optionsImage)
                    .into(holder?.view.imageViewDetail)

           } else {
                Glide.with(mContext)
                    .load(content?.link)
                    .apply(optionsImage)
                    .into(holder.view.imageViewDetail)
            }
//        image_view_resource.setImageResource(R.drawable.flower)
        if (contentData?.favorite == true) {
            holder.view.favoriteButton.setImageResource(R.drawable.ic_favorite_black_24dp)
        } else {
            holder.view.favoriteButton.setImageResource(R.drawable.ic_favorite_border_black_24dp)
        }
        holder.view.ups.text = contentData?.ups.toString()
        holder.view.downs.text = contentData?.downs.toString()
        holder.view.textViewDetail.text = contentData?.title
        holder.view.setOnClickListener { didSelectTableView(content, position) }
    }

    fun didSelectTableView(content: ImageLib?, position: Int) {


    }

    fun callFavoriteAction(image: ImageLib?, view: View) {

        val id = image?.id

        if (id == null) {
            Toast.makeText(mContext, "Problem while favorite an image", Toast.LENGTH_SHORT).show()
            return
        }

        val client = ImgurClient.create()
        val call = client.favoriteImage(id,"Bearer " + Preference(mContext).getAccessToken(), "Epicture")

        call.enqueue(object: Callback<UploadResponse> {
            override fun onResponse(call: Call<UploadResponse>, response: retrofit2.Response<UploadResponse>) {
                val body = response.body()

                if (body?.data == "unfavorited")
                    view.favoriteButton.setImageResource(R.drawable.ic_favorite_border_black_24dp)
                else
                    view.favoriteButton.setImageResource(R.drawable.ic_favorite_black_24dp)

                Toast.makeText(mContext, body?.data, Toast.LENGTH_SHORT).show()
                println("REQUEST " + body)
            }

            override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                println("REQUEST FAILED")
                println(t.message)
            }
        })
    }

    /// MARK : helper

}

class CustomDetailViewHolder(val view: View): RecyclerView.ViewHolder(view) {


}