package com.freelapp.encontrotecnicoflow.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.freelapp.encontrotecnicoflow.R
import com.freelapp.encontrotecnicoflow.ktx.await
import com.freelapp.encontrotecnicoflow.viewmodel.MainActivityViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycleScope.launch {
            val returnValue = viewModel.generateRandomIntAwait()
            Log.d(TAG, "onCreate: $returnValue")
        }
    }
    
    companion object {
        private const val TAG = "MainActivity"
    }
}