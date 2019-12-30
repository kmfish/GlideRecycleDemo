package com.kmfish.android.gliderecycledemo.glide

import android.graphics.drawable.Drawable

/**
 * created by lijun3 on 2019/12/13
 *
 * 对加载Drawable资源的Target的包装
 */
abstract class ClearableDrawableTarget(width: Int, height: Int) :
    ClearableTarget<ClearableDrawable, Drawable>(ClearableDrawable(), width, height)

class ClearableDrawable : ClearableResource<Drawable>()