package com.yakisan.depremapi.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yakisan.depremapi.databinding.FragmentEarthquakeListBinding
import com.yakisan.depremapi.databinding.FragmentLatestEarthquakeBinding
import com.yakisan.depremapi.model.DepremModel
import com.yakisan.depremapi.service.APIClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class EarthquakeListViewModel : ViewModel() {
    val depremler = MutableLiveData<ArrayList<DepremModel>>()
    val errorMessage = MutableLiveData<Boolean>()
    val progress = MutableLiveData<Boolean>()
    /**
     * Disposable (RxJava)
     * Kullan at anlamına gelir, büyük isteklerde kullanılır.
     */
    private val API = APIClient()
    private val disposable = CompositeDisposable()

    fun veriyiGuncelle(){
        tumDepremleriGetir()
    }


    fun tumDepremleriGetir(){
        progress.value = true
        disposable.add(
            API.getAllEartquakes() //Veri çekme işlemi
                .subscribeOn(Schedulers.newThread()) //getLastEartquake() işlemi için oluşturduğumuz yeni thread
                .observeOn(AndroidSchedulers.mainThread()) //Gözlemleme işmeini kullanıcının kullandığı ana thread üzerinde yapıyoruz.
                .subscribeWith(object : DisposableSingleObserver<ArrayList<DepremModel>>() {
                    override fun onSuccess(t: ArrayList<DepremModel>) {
                        //Başarılı olursa yapılacaklar:
                        depremleriGoster(t)
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

    //Depremleri goster
    private fun depremleriGoster(liste: ArrayList<DepremModel>) {
        depremler.value = liste
        progress.value = false
        errorMessage.value = false
    }

    //Listeyi Guncelle (Swipe)
    fun listeGuncelleSwipe(binding: FragmentEarthquakeListBinding, viewModel : EarthquakeListViewModel){
        //Swipe Refresh Layout
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.progressBar.visibility = View.GONE
            binding.tvHataMesaji.visibility = View.GONE
            binding.depremlerRecyclerView.visibility = View.GONE
            viewModel.veriyiGuncelle()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }
}