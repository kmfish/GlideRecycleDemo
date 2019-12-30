package com.kmfish.android.gliderecycledemo.glide

import android.graphics.Bitmap

/**
 * created by lijun3 on 2019/12/13
 *
 * 对加载Bitmap资源的Target的包装
 */
abstract class ClearableBitmapTarget(width: Int, height: Int) :
    ClearableTarget<ClearableBitmap, Bitmap>(ClearableBitmap(), width, height)

class ClearableBitmap : ClearableResource<Bitmap>()