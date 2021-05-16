package com.agb.core.common

sealed class Stage {
    object Login : Stage()
    object Home : Stage()
}

interface Router {
    infix fun routeTo(stage: Stage)
    infix fun backTo(stage: Stage)
    infix fun restore(stage: Stage)
}
