package com.lianda.githubfinder.utils.extentions

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.lianda.githubfinder.R

fun View.visible(){
    this.visibility  = View.VISIBLE
}

fun View.gone() {
    this.visibility  = View.GONE
}

fun View.onSingleClickListener(action: () -> Unit) {
    this.setOnClickListener {
        action.invoke()
        this.isEnabled = false
        postDelayed({
            this.isEnabled = true
        }, 1000)
    }
}

fun ImageView.loadImage(url: String, progressBar: ProgressBar, placeHolder:Int? =null) {
    progressBar.visible()
    Glide.with(context)
        .load(url)
        .error(placeHolder ?: R.drawable.ic_sentiment_dissatisfied_accent_24dp)
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                progressBar.gone()
                setImageResource(placeHolder ?: R.drawable.ic_sentiment_dissatisfied_accent_24dp)
                return true
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                progressBar.gone()
                setImageDrawable(resource)
                return true
            }

        })
        .into(this)
}