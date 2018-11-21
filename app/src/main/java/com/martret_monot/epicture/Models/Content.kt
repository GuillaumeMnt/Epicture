package com.martret_monot.epicture.Models

import android.media.Image
import java.io.Serializable

public class Content(id: String, title: String?, description: String?, link: String?, type: String?, ups: Int, downs: Int,
                     points: Int, favorite: Boolean, is_album: Boolean, images: List<ImageLib>?): Serializable {

    internal var id: String? = null
    internal var title: String? = null
    internal var description: String? = null
    internal var link: String? = null
    internal var type: String? = null
    internal var ups: Int = 0
    internal var downs: Int = 0
    internal var points: Int = 0
    internal var favorite: Boolean = false
    internal var is_album: Boolean = false
    internal var images: List<ImageLib>? = null
}