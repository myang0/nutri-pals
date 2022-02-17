package com.seggsmen.finalapp

import android.util.Log
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

private const val MIN_SCALE = 0.85f
private const val MIN_ALPHA = 0.5f

class PageViewFadeTransformer : ViewPager2.PageTransformer{
    override fun transformPage(view: View, position: Float) {
        view.apply {
            when {
                position < -1 -> { // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    alpha = 0f
                }
                position <= 0 -> { // [-1,0]
                    view.translationX = -position*view.width
                    view.alpha = 1- abs(position)
                }
                position <= 1 -> { // (0,1]
                    // Fade the page out.
                    alpha = 0f
                }
                else -> { // (1,+Infinity]
                    // This page is way off-screen to the right.
                    alpha = 0f
                }
            }
        }
    }
}
