package com.kmfish.android.gliderecycledemo

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.snackbar.Snackbar
import com.kmfish.android.gliderecycledemo.glide.ClearableBitmap
import com.kmfish.android.gliderecycledemo.glide.ClearableBitmapTarget
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        test2()
    }

    private var bitmap: Bitmap? = null
//    private fun test() {
//        val target = Glide.with(this)
//            .asBitmap()
//            .load("https://pics6.baidu.com/feed/8718367adab44aedf2d6d8842904c104a08bfbd0.jpeg?token=65aad28f9a5456d50e9e0b19bb19f2c1&s=3B994587400DC9431003B4EE0300F019")
//            .apply(RequestOptions.placeholderOf(R.mipmap.yylove_love_channel_bg))
//            .into(object : CustomTarget<Bitmap>() {
//                override fun onResourceReady(
//                    resource: Bitmap,
//                    transition: Transition<in Bitmap>?
//                ) {
//                    Log.i(TAG, "glide onResourceReady")
//                    bitmap = resource
//                    base_layout.setBackgroundDrawable(BitmapDrawable(resource))
//                }
//
//                override fun onLoadCleared(placeholder: Drawable?) {
//                    Log.i(TAG, "glide onLoadCleared placeholder:$placeholder")
//                    placeholder?.let {
//                        base_layout.setBackgroundDrawable(it)
//                    } ?: base_layout.setBackground(null)
//                }
//            })
//        base_layout.postDelayed({
//            Log.i(TAG, "glide clear target")
//            Glide.with(this).clear(target)
//            base_layout.postDelayed({
//                Glide.get(this).clearMemory()
//                base_layout.postDelayed({
//                    Log.i(TAG, "bitmap recycled: ${bitmap?.isRecycled}")
//                    base_layout.invalidate()
//                }, 3000)
//            }, 3000)
//        }, 3000)
//    }

    private var bitmap2: ClearableBitmap? = null
    private fun test2() {
        val target = Glide.with(this)
            .asBitmap()
            .load("https://pics6.baidu.com/feed/8718367adab44aedf2d6d8842904c104a08bfbd0.jpeg?token=65aad28f9a5456d50e9e0b19bb19f2c1&s=3B994587400DC9431003B4EE0300F019")
            .apply(RequestOptions.placeholderOf(R.mipmap.yylove_love_channel_bg))
            .into(object : ClearableBitmapTarget(500, 500) {
                override fun onResourceReady(
                    resource: ClearableBitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    Log.i(TAG, "glide onResourceReady")
                    resource.into({
                        Log.d(TAG, "onResourceFunc called")
                        base_layout.setBackgroundDrawable(BitmapDrawable(it))
                    }, {
                        Log.d(TAG, "onClearFunc called")
                        base_layout.setBackgroundDrawable(it.get())
                    })
                }
            })
        base_layout.postDelayed({
            Log.i(TAG, "glide clear target")
            Glide.with(this).clear(target)
            base_layout.postDelayed({
                Glide.get(this).clearMemory()
                base_layout.postDelayed({
                    Log.i(TAG, "bitmap recycled: ${bitmap?.isRecycled}")
                    base_layout.invalidate()
                }, 3000)
            }, 3000)
        }, 3000)
    }

//    private fun test3() {
//        Glide.with(this)
//            .load(R.mipmap.yylove_love_channel_bg)
//            .into(object : SimpleTarget<Drawable>() {
//                override fun onLoadCleared(placeholder: Drawable?) {
//                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                }
//
////                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
////                    bitmap = resource
////                    base_layout.setBackgroundDrawable(BitmapDrawable(resource))
////                }
//
//                override fun onResourceReady(
//                    resource: Drawable,
//                    transition: Transition<in Drawable>?
//                ) {
//                    base_layout.setBackgroundDrawable(resource)
//                }
//            })
//    }
}
