package com.martret_monot.epicture.Controller.Fragment

import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.util.Log
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.martret_monot.epicture.R
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

import android.app.AlertDialog
import android.net.Uri
import android.widget.EditText
import com.martret_monot.epicture.Client.ImgurClient
import com.martret_monot.epicture.Models.UploadResponse
import com.martret_monot.epicture.Preference
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.android.synthetic.main.fragment_favorite.view.*
import retrofit2.Call
import retrofit2.Callback
import java.util.*

class UploadFragment: Fragment() {

    private var imageview: ImageView? = null
    private var openGallery: Button? = null
    private var uploadPhoto: Button? = null
    private var bitmapCopy: Bitmap? = null
    private val GALLERY = 1
    private val CAMERA = 2

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_favorite, container, false)

        imageview = view?.findViewById(R.id.iv)
        openGallery = view?.findViewById(R.id.openGalleryButton)
        uploadPhoto = view?.findViewById(R.id.uploadPhoto)

        view.openGalleryButton.setOnClickListener { view ->
            showPictureDialog()
        }

        view.uploadPhoto.setOnClickListener { view ->
            uploadImage()
        }

        return view
    }

    fun showPictureDialog() {

        val pictureDialog = AlertDialog.Builder(this.requireContext())
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems = arrayOf("Select photo from gallery", "Capture photo from camera")
        pictureDialog.setItems(pictureDialogItems
        ) { dialog, which ->
            when (which) {
                0 -> choosePhotoFromGallary()
                1 -> takePhotoFromCamera()
            }
        }
        pictureDialog.show()
    }

    fun choosePhotoFromGallary() {
        val galleryIntent = Intent(Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        startActivityForResult(galleryIntent, GALLERY)
    }

    private fun takePhotoFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        startActivityForResult(intent, CAMERA)
    }


    override fun onActivityResult(requestCode:Int, resultCode:Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GALLERY) {

            if (data != null) {
                val contentURI = data?.data
                try {
                    bitmapCopy = MediaStore.Images.Media.getBitmap(activity?.contentResolver, contentURI)
                    imageview?.setImageBitmap(bitmapCopy)
                }
                catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this.requireContext(), "Failed!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        else if (requestCode == CAMERA) {

            bitmapCopy = data?.extras?.get("data") as Bitmap
            imageview?.setImageBitmap(bitmapCopy)
            Toast.makeText(this.requireContext(), "Image Saved!", Toast.LENGTH_SHORT).show()
        }
    }

    fun uploadImage() {

        if (bitmapCopy != null) {
            val byteA: ByteArrayOutputStream = ByteArrayOutputStream()
            bitmapCopy?.compress(Bitmap.CompressFormat.PNG, 100, byteA)
            val byteArray = byteA.toByteArray()
            val encoded = Base64.encodeToString(byteArray, Base64.DEFAULT)
            val client = ImgurClient.create()

            val title = titleUpload.text.toString()
            val description = descriptionUpload.text.toString()

            val call = client.uploadImage(
                encoded, title, "", description,
                "Bearer " + Preference(this.requireContext()).getAccessToken(), "Epicture"
            )

            call.enqueue(object : Callback<UploadResponse> {
                override fun onResponse(
                    call: Call<UploadResponse>, response: retrofit2.Response<UploadResponse>
                ) {
                    val body = response.body()
                    Toast.makeText(requireContext(), "Image successfully uploaded !", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                    println("REQUEST FAILED")
                    println(t.message)
                }
            })
        } else {
            Toast.makeText(requireContext(), "Select a photo first", Toast.LENGTH_SHORT).show()
        }
    }

}
