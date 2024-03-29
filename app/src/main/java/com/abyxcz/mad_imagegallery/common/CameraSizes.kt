package com.abyxcz.mad_imagegallery.common

/*
 * Copyright 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.graphics.Point
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.params.StreamConfigurationMap
import android.util.Size
import android.view.Display
import kotlin.math.max
import kotlin.math.min

/** Helper class used to pre-compute shortest and longest sides of a [Size] */
class SmartSize(width: Int, height: Int) {
    var size = Size(width, height)
    var long = max(size.width, size.height)
    var short = min(size.width, size.height)
    override fun toString() = "SmartSize(${long}x${short})"
}

/** Standard FHD size for pictures and video */
val SIZE_1080P: SmartSize = SmartSize(1920, 1080)

/** Standard HD size for pictures and video */
val SIZE_720P: SmartSize = SmartSize(1280, 720)

/** Returns a [SmartSize] object for the given [Display] */
fun getDisplaySmartSize(display: Display): SmartSize {
    val outPoint = Point()
    display.getRealSize(outPoint)
    return SmartSize(outPoint.x, outPoint.y)
}

/**
 * Returns the largest available PREVIEW size. For more information, see:
 * https://d.android.com/reference/android/hardware/camera2/CameraDevice and
 * https://developer.android.com/reference/android/hardware/camera2/params/StreamConfigurationMap
 */
fun <T> getPreviewOutputSize(
    display: Display,
    characteristics: CameraCharacteristics,
    targetClass: Class<T>,
    format: Int? = null,
): Size? {
    // Find which is smaller: screen or 1080p
    val screenSize = getDisplaySmartSize(display)
    val fhdScreen = screenSize.long >= SIZE_1080P.long || screenSize.short >= SIZE_1080P.short
    val maxSize = if (fhdScreen) SIZE_1080P else screenSize

    // If image format is provided, use it to determine supported sizes; else use target class
    val config = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)

    if (format == null) assert(StreamConfigurationMap.isOutputSupportedFor(targetClass))
    else config?.isOutputSupportedFor(format)?.let { assert(it) }

    val allSizes = if (format == null) config?.getOutputSizes(targetClass)
    else config?.getOutputSizes(format)

    // Get available sizes and sort them by area from largest to smallest.
    val validSizes = allSizes
        ?.sortedWith(compareBy { it.height * it.width })
        ?.map { SmartSize(it.width, it.height) }
        ?.reversed()

    // Then, get the largest output size that is smaller or equal than our max size.
    return validSizes?.first { it.long <= maxSize.long && it.short <= maxSize.short }?.size
}