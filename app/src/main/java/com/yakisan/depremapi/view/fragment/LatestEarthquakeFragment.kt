package com.yakisan.depremapi.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.yakisan.depremapi.R
import com.yakisan.depremapi.databinding.EarthquakeItemBinding
import com.yakisan.depremapi.databinding.FragmentLatestEarthquakeBinding
import com.yakisan.depremapi.util.saatiGetir
import com.yakisan.depremapi.util.tarihiGetir
import com.yakisan.depremapi.util.utilDownloadImage
import com.yakisan.depremapi.util.utilPlaceholderOlustur
import com.yakisan.depremapi.viewmodel.LatestEarthquakeViewModel
import kotlinx.coroutines.NonDisposableHandle.parent

class LatestEarthquakeFragment : Fragment() {
    lateinit var viewModel: LatestEarthquakeViewModel
    lateinit var binding: FragmentLatestEarthquakeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_latest_earthquake, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //ViewModel Binding
        viewModel = ViewModelProviders.of(this).get(LatestEarthquakeViewModel::class.java)
        //Get data from internet
        viewModel.veriyiGuncelle()

        //Veri gözlemleme
        observeLiveData()
    }


    //Veriyi gözlemleme
    private fun observeLiveData() {
        //Listeyi gözlemleme
        viewModel.sonDeprem.observe(viewLifecycleOwner, Observer { deprem ->
            deprem?.let {
                println("Liste: ${it.get(0)}") //Liste tamamını alır.
                viewModel.verileriYazdir(binding, deprem, requireContext())
            }
        })

        //Hata mesaji gözlemleme
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { hataMesaji ->
            hataMesaji?.let {
                if (hataMesaji) {
                    binding.tvHataMesajiDetail.visibility = View.VISIBLE
                } else {
                    binding.tvHataMesajiDetail.visibility = View.GONE
                }
            }
        })

        //Progress gözlemleme
        viewModel.progress.observe(viewLifecycleOwner, Observer { progress ->
            progress?.let {
                if (progress) {
                    binding.ivMapImageDetail.visibility = View.GONE
                    binding.tvHataMesajiDetail.visibility = View.GONE
                    binding.progressBarDetail.visibility = View.VISIBLE
                } else {
                    binding.progressBarDetail.visibility = View.GONE
                    binding.ivMapImageDetail.visibility = View.VISIBLE
                }
            }
        })
    }


}