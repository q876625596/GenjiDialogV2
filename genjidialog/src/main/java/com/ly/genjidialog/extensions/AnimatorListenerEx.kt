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

    fun onAnimatorStart(listener: (animator: Animator?) -> Unit): AnimatorListenerEx {
        animatorStart = listener
        return this
    }

    fun onAnimatorEnd(listener: (animator: Animator?) -> Unit): AnimatorListenerEx {
        animatorEnd = listener
        return this
    }

    fun onAnimatorRepeat(listener: (animator: Animator?) -> Unit): AnimatorListenerEx {
        animatorRepeat = listener
        return this
    }

    fun onAnimatorCancel(listener: (animator: Animator?) -> Unit): AnimatorListenerEx {
        animatorCancel = listener
        return this
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