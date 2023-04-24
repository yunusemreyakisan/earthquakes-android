package com.yakisan.depremapi.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
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
    val options = RequestOptions().placeholder(placeholder).error(R.color.md_theme_dark_error)

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


@BindingAdapter("android:downloadImage")
fun gorselIndir(view: ImageView, url: String?){
    view.utilDownloadImage(url, utilPlaceholderOlustur(view.context))
}

//Tarih ve Saat ayrımı
fun tarihiGetir(zaman : String) : String{
    val kesilmis_zaman = zaman.split(" ")
    val tarih = kesilmis_zaman[0]
    return tarih
}

fun saatiGetir(zaman : String) : String{
    val kesilmis_zaman = zaman.split(" ")
    val saat = kesilmis_zaman[1]
    return saat
}