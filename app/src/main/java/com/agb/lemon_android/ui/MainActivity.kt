package com.agb.lemon_android.ui

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.agb.core.common.Router
import com.agb.core.common.Stage
import com.agb.core_ui.Animation
import com.agb.core_ui.LemonActivity
import com.agb.feature_login.ui.LoginFragment

class MainActivity : LemonActivity() {
    override val router = object : Router {
        private val Stage.fragment get() = when (this) {
            Stage.Home -> LoginFragment()
            Stage.Login -> LoginFragment()
        }

        override fun routeTo(stage: Stage) {
            viewModel.setStage(stage)

            val animation = if (viewModel.previousStages.isEmpty()) {
                Animation.Start
            } else {
                Animation.Forward
            }

            changeFragment(stage.fragment, animation)
        }

        override fun backTo(stage: Stage) = with(viewModel.previousStages) {
            while (removeLastOrNull() != null) {
                if (last() == stage) {
                    changeFragment(stage.fragment, Animation.Return)
                    return@with
                }
            }

            error("No such previous stage: $stage")
        }

        override fun restore(stage: Stage) {
            changeFragment(stage.fragment, Animation.Start)
        }
    }

    val viewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        router routeTo viewModel.stage
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        router restore viewModel.stage
    }
}
