package com.agb.lemon_android.ui

import androidx.lifecycle.ViewModel
import com.agb.core.common.Stage

class MainViewModel : ViewModel() {
    private var _stage: Stage? = null
    val stage: Stage get() = _stage ?: Stage.Login

    val previousStages = mutableListOf<Stage>()

    fun setStage(new: Stage) {
        val prev = _stage
        _stage = new

        if (prev != null) {
            previousStages += prev
        }
    }
}
