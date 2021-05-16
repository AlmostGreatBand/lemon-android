package com.agb.core_ui

import androidx.annotation.AnimRes

sealed class Animation(
    @AnimRes val enter: Int,
    @AnimRes val exit: Int,
    @AnimRes val popEnter: Int,
    @AnimRes val popExit: Int,
) {
    object Forward : Animation(
        R.anim.slide_from_right,
        R.anim.slide_to_left,
        R.anim.slide_from_left,
        R.anim.slide_to_right,
    )

    object Return : Animation(
        R.anim.slide_from_left,
        R.anim.slide_to_right,
        R.anim.slide_from_right,
        R.anim.slide_to_left,
    )

    object Start : Animation(
        R.anim.no_anim,
        R.anim.no_anim,
        R.anim.slide_from_left,
        R.anim.slide_to_right
    )

    class Custom(
        enter: Int,
        exit: Int,
        popEnter: Int,
        popExit: Int
    ) : Animation(enter, exit, popEnter, popExit)
}
