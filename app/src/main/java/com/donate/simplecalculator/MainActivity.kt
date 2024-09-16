package com.donate.simplecalculator

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, TitleFragment())
                .commit()
        }
    }

    fun showArticleContent(title: String) {
        val description = when (title) {
            "The BMW S1000RR" -> getString(R.string.bmw)
            "The KAWASAKI ZX-10RR" -> getString(R.string.kawasaki)
            "The YAMAHA YZF-R1" -> getString(R.string.yamaha)
            else -> "No description available."
        }

        val imageResId = when (title) {
            "The BMW S1000RR" -> R.drawable.s1000rr
            "The KAWASAKI ZX-10RR" -> R.drawable.zx10rr
            "The YAMAHA YZF-R1" -> R.drawable.yzfr1
            else -> R.drawable.ic_launcher_background
        }

        val contentFragment = ContentFragment.newInstance(title, description, imageResId)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_content, contentFragment)
                .commit()
        } else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, contentFragment)
                .addToBackStack(null)
                .commit()
        }
    }

}
