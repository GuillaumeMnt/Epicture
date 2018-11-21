package com.martret_monot.epicture.Controller.Fragment

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.martret_monot.epicture.Models.Content
import com.martret_monot.epicture.Models.ImageLib
import com.martret_monot.epicture.R
import kotlinx.android.synthetic.main.image_row.view.*
import com.bumptech.glide.request.RequestOptions
import com.martret_monot.epicture.Controller.DetailPhoto
import kotlinx.android.synthetic.main.gallery_row.view.*

class MainAdapter(val imageList: List<Content>?, val context: Context): RecyclerView.Adapter<CustomViewHolder>() {

    val mContext: Context = context
    val CONTENT = "content"

    override fun getItemCount(): Int {
        if (imageList?.size == null)
            return 0
        return imageList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.image_row, parent, false)

        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val content: Content? = imageList?.get(position)

        if (content?.images != null) {

            val image: List<ImageLib>? = content?.images
            val optionsImage = RequestOptions().fitCenter()

                if (image?.get(0)?.type == "image/gif") {
                    Glide.with(mContext)
                        .asGif()
                        .load(image?.get(0)?.link)
                        .apply(optionsImage)
                        .into(holder?.view.imageView)

               } else {
                    Glide.with(mContext)
                        .load(image?.get(0)?.link)
                        .apply(optionsImage)
                        .into(holder.view.imageView)
                }
        }
        holder?.view?.textView.text = content?.title
        holder.view.setOnClickListener { didSelectTableView(content, position) }
    }

    fun didSelectTableView(content: Content?, position: Int) {

        val randomIntent = Intent(context, DetailPhoto::class.java)
        randomIntent.putExtra(CONTENT, content)
        startActivity(context, randomIntent, null)

    }

    /// MARK : helper

}

class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view) {


}