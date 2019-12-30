# GlideRecycleDemo

对于 Glide.into(SimpleTarget/CustomTarget)，从glide的官方文档可知，使用这类target加载后，必须要实现onLoadCleared接口，接收该资源被回收的通知。

当资源回收后，则不应该继续使用该资源了（如重绘、或setImageDrawable等），因为该资源一旦被cleared后，bitmap有可能被recycled，或被用于渲染其他的图片内容。
而我们的某些业务场景下，会把得到的drawable传出去其他地方使用。由于直接加载得到的drawable或bitmap传出去了，
使用的地方也不会再知道何时该资源会被回收，所以也没法及时取消对资源的使用。

所以针对此问题，对这类Target的加载进行了封装。利用一个[ClearableResource]对象，把资源的回收状态携带起来，
然后在真正使用资源的地方，再要求使用者要提供资源清理的回调，以保证资源不会在回收后被继续误用。
