package com.kmfish.android.gliderecycledemo.glide

import android.graphics.drawable.Drawable
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition

/**
 * 对于 Glide.into(SimpleTarget/CustomTarget)，从glide的官方文档可知，
 * 使用这类target加载后，必须要实现onLoadCleared接口，接收该资源被回收的通知。
 * 当资源回收后，则不应该继续使用该资源了（如重绘、或setImageDrawable等），
 * 因为该资源一旦被cleared后，bitmap有可能被recycled，或被用于渲染其他的图片内容。
 *
 * 而我们的某些业务场景下，会把得到的drawable传出去其他地方使用。由于直接加载得到的drawable或bitmap传出去了，
 * 使用的地方也不会再知道何时该资源会被回收，所以也没法及时取消对资源的使用。
 *
 * 所以针对此问题，对这类Target的加载进行了封装。利用一个[ClearableResource]对象，把资源的回收状态携带起来，
 * 然后在真正使用资源的地方，再要求使用者要提供资源清理的回调，以保证资源不会在回收后被继续误用。
 *
 * 使用方法如下：
 * Glide.with(context).load(url)
 * .into(object : ClearableBitmapTarget(500, 500) {
 *       override fun onResourceReady(resource: ClearableBitmap, transition: Transition<in Bitmap>?
) {
        resource.into({ drawable ->
            // use drawable
        }, { placeholder ->
            // unuse drawable, use placeholder maybe
        })
}
})
 *
 * [bug id ANDROIDYY-42173]
 *
 * Glide的文档：https://muyangmin.github.io/glide-docs-cn/doc/resourcereuse.html
 *
 * @param [R] 经过封装后的 ClearableResource 的子类型
 * @param [T] Glide 加载时指定的资源类型
 */
abstract class ClearableTarget<R : ClearableResource<T>, T>(resource: R, width: Int, height: Int) :
    SimpleTarget<T>(width, height) {

    private val clearableResource: R = resource

//    constructor(resource: R) : this(resource, Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)

    abstract fun onResourceReady(resource: R, transition: Transition<in T>?)

    override fun onResourceReady(resource: T, transition: Transition<in T>?) {
        clearableResource.resource = resource
        onResourceReady(clearableResource, transition)
    }

    override fun onLoadCleared(placeholder: Drawable?) {
        clearableResource.clear()
    }
}