package com.example.root

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.weather.start_page.ui.DefaultFragment
import com.example.workingweather.R
import com.example.workingweather.databinding.ActivityMainBinding

class RootActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val fragment = DefaultFragment()
        replace(fragment, R.id.emptyContainer)
    }

    private fun replace(fragment: Fragment, id: Int) {
        val fragmentManager = this.supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.addToBackStack(null)
            .replace(id, fragment)
            .commit()
    }


}