package com.agb.core.common

sealed class Stage {
    object Registration : Stage()
    object Login : Stage()
    object Home : Stage()
    object Profile : Stage()
}

interface Router {
    fun routeTo(stage: Stage, clearBackStack: Boolean = false)
    fun backTo(stage: Stage)
    fun restore(stage: Stage)
    fun back()
    fun clearBackStack()
}
