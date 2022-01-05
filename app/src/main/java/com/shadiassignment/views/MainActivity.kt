package com.shadiassignment.views

import android.os.Bundle
import com.shadiassignment.base.BaseActivity
import com.shadiassignment.databinding.ActivityMainBinding
import com.shadiassignment.extensions.addFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addDefaultFragment()
    }

    private fun addDefaultFragment() {
        addFragment(ShadiMatchFragment.newInstance())
    }

    override fun onBackPressed() {
        finish()
    }

}