package com.yakisan.depremapi.util

import android.app.Activity
import android.app.PendingIntent.getActivity
import android.content.Context
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.yakisan.depremapi.R

//Load new fragment
fun loadFragment(fragment: Fragment, replace : Int, manager : FragmentManager){
    val transaction = manager.beginTransaction()
    transaction.replace(replace,fragment)
    transaction.commit()
}

//ImageView sınıfına eklenti yapma
fun ImageView.utilDownloadImage(url: String?, placeholder: CircularProgressDrawable) {
    val options = RequestOptions().placeholder(placeholder).error(R.mipmap.ic_launcher)

    Glide.with(context)
        .setDefaultRequestOptions(options) //Ozellestirilmis placeholder tanimi
        .load(url)
        .into(this)
}

//Yüklenme animasyonu eklentisi
fun utilPlaceholderOlustur(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f //Kalinlik
        centerRadius = 40f //Yaricap
        start()
    }
}