package com.martret_monot.epicture.Models

import java.io.Serializable

class ImageLib(id: String?, title: String?, type: String?, animated: Boolean,
               favorite: Boolean, link: String?, gifv: String?,
               ups: Int, downs: Int, points: Int?, score: Int?): Serializable {

    internal var id: String? = null
    internal var title: String? = null
    internal var type: String? = null
    internal var animated: Boolean = false
    internal var favorite: Boolean = false
    internal var link: String? = null
    internal var gifv: String? = null
    internal var ups: Int? = null
    internal var downs: Int? = null
    internal var points: Int? = null
    internal var score: Int? = null
}