package com.agb.core_ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.agb.core.common.Router

abstract class LemonActivity : AppCompatActivity() {
    abstract val router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lemon_activity)
    }

    protected fun changeFragment(
        fragment: LemonFragment,
        animation: Animation? = null
    ) {
        supportFragmentManager.beginTransaction().apply {
            animation?.let { setCustomAnimations(it.enter, it.exit, it.popEnter, it.popExit) }
            replace(R.id.container, fragment)
        }.commit()
    }

    override fun onBackPressed() {
        router.back()
    }
}
