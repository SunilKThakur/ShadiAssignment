package com.shadiassignment.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.shadiassignment.R

/**
 * This method will add fragment into stack
 * */
fun AppCompatActivity.addFragment(fragment: Fragment) {
    supportFragmentManager.beginTransaction().apply {
        add(R.id.fragmentContainer, fragment, fragment.javaClass.canonicalName)
        addToBackStack(fragment.javaClass.canonicalName)
            .commit()
    }
}