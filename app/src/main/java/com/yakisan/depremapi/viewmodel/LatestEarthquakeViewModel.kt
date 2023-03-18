package com.yakisan.depremapi.viewmodel

import android.content.Context
import android.util.Log
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yakisan.depremapi.databinding.FragmentLatestEarthquakeBinding
import com.yakisan.depremapi.model.DepremModel
import com.yakisan.depremapi.service.APIClient
import com.yakisan.depremapi.util.saatiGetir
import com.yakisan.depremapi.util.tarihiGetir
import com.yakisan.depremapi.util.utilDownloadImage
import com.yakisan.depremapi.util.utilPlaceholderOlustur
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class LatestEarthquakeViewModel : ViewModel() {
    val sonDeprem = MutableLiveData<ArrayList<DepremModel>>()
    val errorMessage = MutableLiveData<Boolean>()
    val progress = MutableLiveData<Boolean>()
    /**
     * Disposable (RxJava)
     * Kullan at anlamına gelir, büyük isteklerde kullanılır.
     */
    private val API = APIClient()
    private val disposable = CompositeDisposable()

    fun veriyiGuncelle(){
        sonDepremiGetir()
    }

    //Son depremi getirir.
    fun sonDepremiGetir(){
        progress.value = true
        disposable.add(
            API.getLastEartquake() //Veri çekme işlemi
                .subscribeOn(Schedulers.newThread()) //getLastEartquake() işlemi için oluşturduğumuz yeni thread
                .observeOn(AndroidSchedulers.mainThread()) //Gözlemleme işmeini kullanıcının kullandığı ana thread üzerinde yapıyoruz.
                .subscribeWith(object : DisposableSingleObserver<ArrayList<DepremModel>>() {
                    override fun onSuccess(t: ArrayList<DepremModel>) {
                        //Başarılı olursa yapılacaklar:
                        sonDepremiGoster(t)
                        Log.e("Response", t[0].MapImage)
                    }

                    override fun onError(e: Throwable) {
                        //Başarısız olursa yapılacaklar:
                        progress.value = false
                        errorMessage.value = true
                        e.printStackTrace()
                    }
                })
        )
    }

    //Depremi goster
    private fun sonDepremiGoster(liste: ArrayList<DepremModel>) {
        sonDeprem.value = liste
        progress.value = false
        errorMessage.value = false
    }

    //Verileri yazdirma
    fun verileriYazdir(binding: FragmentLatestEarthquakeBinding ,deprem: ArrayList<DepremModel>, context: Context){
        binding.tvDetailBuyukluk.text = deprem[0].Magnitude.toString()
        binding.tvDetailSaat.text = saatiGetir(deprem[0].Time)
        binding.tvDetailTarih.text = tarihiGetir(deprem[0].Time)
        binding.tvDetailKonum.text = deprem[0].Region
        binding.tvDetailDerinlik.text = deprem[0].Depth.toString()

        //Image Bind
        binding.ivMapImageDetail.utilDownloadImage(
            deprem[0].MapImage, utilPlaceholderOlustur(context)
        )
    }



}