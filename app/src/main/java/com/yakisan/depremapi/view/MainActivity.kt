package com.yakisan.depremapi.view

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yakisan.depremapi.R
import com.yakisan.depremapi.databinding.ActivityMainBinding
import com.yakisan.depremapi.util.loadFragment
import com.yakisan.depremapi.view.fragment.LatestEarthquakeFragment
import com.yakisan.depremapi.view.fragment.EarthquakeListFragment


class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener{
    lateinit var binding : ActivityMainBinding
    //Bottom Navigation Bar
    var latestEarthquakeFragment: LatestEarthquakeFragment = LatestEarthquakeFragment()
    var earthquakeListFragment : EarthquakeListFragment = EarthquakeListFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //set navbar listener
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(this);
        loadFragment(latestEarthquakeFragment, R.id.flFragment, supportFragmentManager)
    }

    //Selected func.
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
          R.id.latest_earthquake -> {
                loadFragment(latestEarthquakeFragment, R.id.flFragment, supportFragmentManager)
                return true
            }
           R.id.list -> {
               loadFragment(earthquakeListFragment, R.id.flFragment, supportFragmentManager)
                return true
            }
        }
        return false
    }



}