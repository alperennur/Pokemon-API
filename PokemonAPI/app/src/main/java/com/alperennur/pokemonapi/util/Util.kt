package com.alperennur.pokemonapi.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.alperennur.pokemonapi.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.net.HttpURLConnection
import java.net.URL

fun ImageView.downloadFromUrl(url : String?, context: Context){



    val options = RequestOptions()
        .placeholder(placeHolderProgressBar(context))
        .error(R.mipmap.ic_launcher_round)  // hata alırsa default olarak gösterilecek görsel


    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)


}


fun placeHolderProgressBar(context: Context): CircularProgressDrawable {

    return CircularProgressDrawable(context).apply {

        strokeWidth = 8f
        centerRadius = 40f
        start()

    }
}