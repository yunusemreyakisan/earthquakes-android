package com.yakisan.depremapi.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.yakisan.depremapi.model.DepremModel
import com.yakisan.depremapi.service.APIClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class EarthquakeViewModel : ViewModel() {
    /**
     * Disposable (RxJava)
     * Kullan at anlamına gelir, büyük isteklerde kullanılır.
     */
    private val API = APIClient()
    private val disposable = CompositeDisposable()


    //Son depremi getirir.
    fun sonDepremiGetir(){
        disposable.add(
            API.getLastEartquake() //Veri çekme işlemi
                .subscribeOn(Schedulers.newThread()) //getLastEartquake() işlemi için oluşturduğumuz yeni thread
                .observeOn(AndroidSchedulers.mainThread()) //Gözlemleme işmeini kullanıcının kullandığı ana thread üzerinde yapıyoruz.
                .subscribeWith(object : DisposableSingleObserver<ArrayList<DepremModel>>() {
                    override fun onSuccess(t: ArrayList<DepremModel>) {
                        //Başarılı olursa yapılacaklar:
                        Log.e("Response", t[0].MapImage)
                    }

                    override fun onError(e: Throwable) {
                        //Başarısız olursa yapılacaklar:
                        e.printStackTrace()
                    }
                })
        )
    }
}