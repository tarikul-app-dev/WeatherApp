package com.tarikul.weatherapp.main.view

import android.os.Bundle
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tarikul.weatherapp.api.WeatherModel
import com.tarikul.weatherapp.databinding.ActivityMainBinding

import com.tarikul.weatherapp.main.viewmodel.MainViewModel
import com.tarikul.weatherapp.main.view.adapter.WeatherAdapter
import com.tarikul.weatherapp.utils.CommonMethods

import com.tarikul.weatherapp.utils.ProgressDialog

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var progressDialog: ProgressDialog
    lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: WeatherAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        adapter = WeatherAdapter(this)
        progressDialog = ProgressDialog(this)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerview.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(
            binding.recyclerview.context,
            layoutManager.orientation
        )

        binding.recyclerview.addItemDecoration(dividerItemDecoration)
        binding.recyclerview.adapter = adapter


        mainViewModel.cityList()


        liveDataListener()
    }


    private fun liveDataListener() {

        mainViewModel.cityList.observe(this) {
            adapter.addData(it)
        }

        mainViewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        mainViewModel.loading.observe(this, Observer {
            if (it) {
                progressDialog.show()
            } else {
                progressDialog.dismiss()
            }
        })

    }


}