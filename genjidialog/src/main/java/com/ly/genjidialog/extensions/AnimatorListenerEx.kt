package com.ly.genjidialog.extensions

import android.animation.Animator

fun Animator.addAnimatorListenerEx(listener: AnimatorListenerEx.() -> Unit): Animator {
    val animatorListenerEx = AnimatorListenerEx()
    this.addListener(animatorListenerEx)
    animatorListenerEx.listener()
    return this
}

class AnimatorListenerEx : Animator.AnimatorListener {

    private var animatorStart: ((animator: Animator?) -> Unit)? = null
    private var animatorEnd: ((animator: Animator?) -> Unit)? = null
    private var animatorRepeat: ((animator: Animator?) -> Unit)? = null
    private var animatorCancel: ((animator: Animator?) -> Unit)? = null

    fun onAnimatorStart(listener: (animator: Animator?) -> Unit) {
        animatorStart = listener
}

    fun onAnimatorEnd(listener: (animator: Animator?) -> Unit) {
        animatorEnd = listener
    }

    fun onAnimatorRepeat(listener: (animator: Animator?) -> Unit) {
        animatorRepeat = listener
    }

    fun onAnimatorCancel(listener: (animator: Animator?) -> Unit) {
        animatorCancel = listener
    }

    override fun onAnimationRepeat(p0: Animator?) {
        animatorRepeat?.invoke(p0)
    }

    override fun onAnimationEnd(p0: Animator?) {
        animatorEnd?.invoke(p0)
    }

    override fun onAnimationCancel(p0: Animator?) {
        animatorCancel?.invoke(p0)
    }

    override fun onAnimationStart(p0: Animator?) {
        animatorStart?.invoke(p0)
    }
}