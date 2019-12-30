package com.kmfish.android.gliderecycledemo.glide

import android.graphics.drawable.Drawable

/**
 * created by lijun3 on 2019/12/13
 *
 * 表示一种可被回收的资源，是真正的Glide资源[resource]的代理对象（可能为drawable或bitmap等），附加了回收状态信息
 */
open class ClearableResource<T>(
    var resource: T? = null,
    placeholder: Drawable? = null
) {

    private val placeholderDrawable = PlaceholderDrawable(placeholder)

    private var isCleared = false
    private var onClearedCallback: ((placeholder: PlaceholderDrawable) -> Any)? = null

    /**
     * 使用该资源，需要分别提供使用回调，以及清理回调
     *
     * @param onLoadFunc 资源使用回调，可以拿到资源
     * @param onClearedFunc 资源清理回调，在该回调中应该释放对资源的所有引用，不再继续使用该资源
     */
    fun into(
        onLoadFunc: (resource: T?) -> Any,
        onClearedFunc: (placeholder: PlaceholderDrawable) -> Any
    ) {
        onClearedCallback = onClearedFunc
        if (isCleared) {
            doCleared()
        } else {
            onLoadFunc(resource)
        }
    }

    /**
     * 清理资源
     */
    fun clear() {
        isCleared = true
        doCleared()
    }

    private fun doCleared() {
        onClearedCallback?.invoke(placeholderDrawable)
    }
}

/**
 * 定义该类，为了让资源清理回调闭包的提示更清晰些
 */
class PlaceholderDrawable(private val drawable: Drawable?) {
    fun get(): Drawable? = drawable
}