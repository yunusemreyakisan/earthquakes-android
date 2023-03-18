package com.yakisan.depremapi.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.yakisan.depremapi.adapter.DepremAdapter
import com.yakisan.depremapi.databinding.FragmentEarthquakeListBinding
import com.yakisan.depremapi.viewmodel.EarthquakeListViewModel

class EarthquakeListFragment : Fragment() {
    lateinit var viewModel: EarthquakeListViewModel
    private val adapter = DepremAdapter(arrayListOf())
    lateinit var binding: FragmentEarthquakeListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEarthquakeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //ViewModel Binding
        viewModel = ViewModelProviders.of(this).get(EarthquakeListViewModel::class.java)
        //Get data from internet
        viewModel.veriyiGuncelle()

        //Recyclerview Initialize
        binding.depremlerRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.depremlerRecyclerView.adapter = adapter

        //Veri gözlemleme
        observeLiveData()
    }


    //Veriyi gözlemleme
    private fun observeLiveData() {
        //Listeyi gözlemleme
        viewModel.depremler.observe(viewLifecycleOwner, Observer { depremler ->
            depremler?.let {
                binding.depremlerRecyclerView.visibility = View.VISIBLE
                adapter.updateEarthquakes(depremler)
            }
        })

        //Hata mesaji gözlemleme
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { hataMesaji ->
            hataMesaji?.let {
                if (hataMesaji) {
                    binding.tvHataMesaji.visibility = View.VISIBLE
                } else {
                    binding.tvHataMesaji.visibility = View.GONE
                }
            }
        })

        //Progress gözlemleme
        viewModel.progress.observe(viewLifecycleOwner, Observer { progress ->
            progress?.let {
                if (progress) {
                    binding.depremlerRecyclerView.visibility = View.GONE
                    binding.tvHataMesaji.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            }
        })
    }


}