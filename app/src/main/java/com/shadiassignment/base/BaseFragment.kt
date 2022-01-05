package com.shadiassignment.base

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

abstract class BaseFragment:Fragment() {
    abstract fun setUpToolbar()

    protected fun updateToolbar(title:String?)
    {
        (activity as AppCompatActivity)?.apply {
            this.title=title
            supportActionBar?.apply {
                this.title=title
            }
        }
    }
}