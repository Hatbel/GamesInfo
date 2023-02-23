package com.hatbel.gamesinfo.presenter.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.hatbel.gamesinfo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), HandleLoader {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun handle(isShow: Boolean) {
        binding.loader.isVisible = isShow

    }
}

interface HandleLoader{
    fun handle(isShow: Boolean)
}