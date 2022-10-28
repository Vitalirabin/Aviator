package com.example.aviator

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pushBackStack(MenuFragment())
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 0) {
            finish()
        } else supportFragmentManager.popBackStack()
    }

    fun pushBackStack(fragment: Fragment) {
        supportFragmentManager.beginTransaction().addToBackStack(fragment.tag)
            .add(R.id.container, fragment, fragment.tag)
            .commit()
    }
}