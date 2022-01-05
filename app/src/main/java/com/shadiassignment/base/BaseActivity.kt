package com.shadiassignment.base

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.shadiassignment.R

abstract class BaseActivity:AppCompatActivity() {
    private lateinit var toolbar: Toolbar

    fun setToolbar()
    {
        toolbar=findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
    }
}