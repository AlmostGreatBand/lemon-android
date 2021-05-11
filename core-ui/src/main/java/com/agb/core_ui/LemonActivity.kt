package com.agb.core_ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class LemonActivity : AppCompatActivity() {
    val app: LemonApp get() = application as LemonApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lemon_activity)
    }

    fun changeFragment(
        fragment: LemonFragment,
    ) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container, fragment)
        }.commit()
    }
}
