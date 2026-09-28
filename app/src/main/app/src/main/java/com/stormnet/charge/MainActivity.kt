package com.stormnet.charge

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.stormnet.charge.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvWelcome.text = "مرحباً بك في StormNet ⚡\n\nالتطبيق يعمل بنجاح على جوالك"
    }
}
