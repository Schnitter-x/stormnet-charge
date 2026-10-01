package com.stormnet.charge

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.stormnet.charge.databinding.ActivityMainBinding
import com.stormnet.charge.ui.PlaceholderFragment
import com.stormnet.charge.ui.families.FamiliesFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // فرض الاتجاه من اليمين لليسار (عربي)
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNav.setOnItemSelectedListener { item ->
            val fragment: Fragment = when (item.itemId) {
                R.id.nav_dashboard -> PlaceholderFragment("لوحة التحكم")
                R.id.nav_sockets -> PlaceholderFragment("إدارة المقابس")
                R.id.nav_families -> FamiliesFragment()
                R.id.nav_transactions -> PlaceholderFragment("سجل المعاملات")
                R.id.nav_settings -> PlaceholderFragment("الإعدادات")
                else -> return@setOnItemSelectedListener false
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit()
            true
        }

        // افتراضياً: افتح شاشة العائلات (لأنها الوحيدة الجاهزة)
        if (savedInstanceState == null) {
            binding.bottomNav.selectedItemId = R.id.nav_families
        }
    }
}
