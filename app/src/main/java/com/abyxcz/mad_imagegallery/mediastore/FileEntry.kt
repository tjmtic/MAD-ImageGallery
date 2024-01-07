package com.abyxcz.mad_imagegallery.mediastore

import android.net.Uri

data class FileEntry(
    val uri: Uri,
    val name: String,
    val size: Long,
    val mimeType: String,
    val dateAdded: Long,
)