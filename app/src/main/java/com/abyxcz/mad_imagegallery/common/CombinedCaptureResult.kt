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


import android.hardware.camera2.CaptureResult
import android.media.Image
import java.io.Closeable

/**
 * Helper data class used to hold capture metadata with their associated image
 */
data class CombinedCaptureResult(
    val image: Image,
    val metadata: CaptureResult,
    val orientation: Int,
    val format: Int,
) : Closeable {
    override fun close() = image.close()
}