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
            view.translationX = view.width * -position;

            if(position <= -1.0F || position >= 1.0F) {
                view.alpha = 0.0F;
                view.translationX = view.width*5f;
            } else if( position == 0.0F ) {
                view.alpha = 1.0F;
            } else {
                view.alpha = 1.0F - abs(position);
            }
        }
    }
}
