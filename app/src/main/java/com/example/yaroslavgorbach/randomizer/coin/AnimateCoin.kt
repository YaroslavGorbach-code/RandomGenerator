package com.example.yaroslavgorbach.randomizer.coin

import android.animation.*
import android.util.Log
import android.view.View
import android.view.animation.*
import android.widget.ImageView
import com.example.yaroslavgorbach.randomizer.R

class AnimateCoin(coinImage: ImageView, fonImage: ImageView) {
    private enum class CoinSide {
        FRONT, BACK
    }
    private val mCoinImage: ImageView = coinImage
    private val mFonImage: ImageView = fonImage
    private var mCoinSide: CoinSide = CoinSide.FRONT
    private var mCoinState: Int = (0..1).random()

    fun animate(){
        mCoinState = (0..1).random()
        when((1..4).random()){
            4-> rotateAnimNormalTwo()
            3-> rotateAnimFast()
            2-> rotateAnimNormalOne()
            1-> rotateAnimSlowly()
        }
        scaleFon()
        scaleCoin()
        shake()
    }

    private fun scaleFon() {
        val scaleX = ObjectAnimator.ofFloat(mFonImage, View.SCALE_X, 1.5f).apply {
            repeatCount = 1
            repeatMode = ObjectAnimator.REVERSE
        }

        val scaleY = ObjectAnimator.ofFloat(mFonImage, View.SCALE_Y, 1.5f).apply {
            repeatCount = 1
            repeatMode = ObjectAnimator.REVERSE
        }

        AnimatorSet().apply {
            playTogether(scaleX, scaleY)
            interpolator = FonScaleInterpolator()
            duration = 900
            start()
        }

    }

    private fun shake() {
        val scaleX = ObjectAnimator.ofFloat(mCoinImage, "scaleX", 1f, 1.1f, 1f, 1.1f, 1f, 1.1f, 1f)
        val scaleY = ObjectAnimator.ofFloat(mCoinImage, "scaleY", 1f, 1.1f, 1f, 1.1f, 1f, 1.1f, 1f)
        val rotation =ObjectAnimator.ofFloat(mCoinImage, "rotation", 0f, -3f, -3f, 3f, -3f, 3f, -3f, 3f, -3f, 0f)

        AnimatorSet().apply {
            playTogether(scaleX, scaleY, rotation)
            startDelay = 1400
            duration = 300
            start()
        }
    }

    private fun rotateAnimNormalOne() {
        ValueAnimator.ofFloat(-1980f, 0f).apply {
            addUpdateListener { animation ->
                mCoinImage.rotationX = animation.animatedValue as Float
                Log.v("coin", (animation.animatedValue as Float).toInt().toString())

                if (mCoinSide == CoinSide.FRONT) {
                    when ((animation.animatedValue as Float).toInt()) {
                        in -1980..-1870 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        in -1870..-1720 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        in -1720..-1530 -> {
                            setCoinSide()
                            mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        }
                        in -1530..-1340 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        in -1340..-1170 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        in -1170..-395 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        in -395..-277 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        in -277..-90 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        in -90..0 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                    }

                } else {
                    when ((animation.animatedValue as Float).toInt()) {
                        in -1980..-1870 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        in -1870..-1720 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        in -1720..-1530 -> {
                            setCoinSide()
                            mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        }
                        in -1530..-1340 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        in -1340..-1170 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        in -1170..-395 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        in -395..-277 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        in -277..-90 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        in -90..0 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                    }
                }
            }
            duration = 1400
            interpolator = InterpolatorRotateNormalOne()
            disableViewDuringAnimation(mCoinImage)
            startDelay = 100
            start()
        }

    }

    private fun rotateAnimNormalTwo() {
        ValueAnimator.ofFloat(-1980f, 0f).apply {
            addUpdateListener { animation ->
                mCoinImage.rotationX = animation.animatedValue as Float
                Log.v("coin", (animation.animatedValue as Float).toInt().toString())

                if (mCoinSide == CoinSide.FRONT) {
                    when ((animation.animatedValue as Float).toInt()) {
                        in -1980..-1900 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        in -1900..-1720 -> {
                            setCoinSide()
                            mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        }
                        in -1720..-1530 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        in -1530..-1340 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        in -1340..-80 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        in -80..0 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                    }

                } else {
                    when ((animation.animatedValue as Float).toInt()) {
                        in -1980..-1900 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        in -1900..-1720 -> {
                            setCoinSide()
                            mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        }
                        in -1720..-1530 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        in -1530..-1340 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        in -1340..-80 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        in -80..0 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                    }
                }
            }
            duration = 1400
            interpolator = InterpolatorRotateNormalTwo()
            disableViewDuringAnimation(mCoinImage)
            startDelay = 100
            start()
        }

    }


    private fun rotateAnimSlowly() {
        ValueAnimator.ofFloat(-740f, 0f).apply {
            addUpdateListener { animation ->
                mCoinImage.rotationX = animation.animatedValue as Float
                Log.v("coin", (animation.animatedValue as Float).toInt().toString())

                if (mCoinSide == CoinSide.FRONT) {
                    when ((animation.animatedValue as Float).toInt()) {
                        in -740..-630 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        in -630..-88 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        in -88..0 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                    }

                } else {
                    when ((animation.animatedValue as Float).toInt()) {
                        in -740..-630 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        in -630..-88 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        in -88..0 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                    }
                }
            }
            duration = 1400
            interpolator = InterpolatorRotateSlowly()
            disableViewDuringAnimation(mCoinImage)
            startDelay = 100
            start()
        }
    }


    private fun rotateAnimFast() {
        ValueAnimator.ofFloat(-2880f, 0f).apply {
            addUpdateListener { animation ->
                mCoinImage.rotationX = animation.animatedValue as Float
                Log.v("coin", (animation.animatedValue as Float).toInt().toString())

                if (mCoinSide == CoinSide.FRONT) {
                    when ((animation.animatedValue as Float).toInt()) {
                        in -2880..-2780 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        in -2780..-2610 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        in -2610..-2250 -> {
                            setCoinSide()
                            mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        }
                        in -2250..-2077 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        in -2077..-1890 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        in -1890..-1700 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        in -1700..-440 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        in -440..-270 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        in -270..-100 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        in -100..0 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                    }

                } else {
                    when ((animation.animatedValue as Float).toInt()) {
                        in -2880..-2780 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        in -2780..-2610 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        in -2610..-2250 -> {
                            setCoinSide()
                            mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        }
                        in -2250..-2077 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        in -2077..-1890 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        in -1890..-1700 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        in -1700..-440 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        in -440..-270 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        in -270..-100 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        in -100..0 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                    }
                }
            }

            duration = 1400
            interpolator = InterpolatorRotateFast()
            disableViewDuringAnimation(mCoinImage)
            startDelay = 150
            start()
        }
    }


    private fun scaleCoin() {
        val scaleX = ObjectAnimator.ofFloat(mCoinImage, View.SCALE_X, 1.4f).apply {
            repeatCount = 1
            repeatMode = ObjectAnimator.REVERSE
        }

        val scaleY = ObjectAnimator.ofFloat(mCoinImage, View.SCALE_Y, 1.4f).apply {
            repeatCount = 1
            repeatMode = ObjectAnimator.REVERSE
        }

        AnimatorSet().apply {
            playTogether(scaleX, scaleY)
            duration = 700
            interpolator = LinearInterpolator()
            start()
        }
    }

    private fun setCoinSide() {
        mCoinSide = if (mCoinState == 1) {
            CoinSide.FRONT
        } else {
            CoinSide.BACK
        }
    }

    private fun ValueAnimator.disableViewDuringAnimation(view: View) {
        this.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                view.isClickable = false
            }

            override fun onAnimationEnd(animation: Animator?) {
                view.isClickable = true
            }
        })
    }

    fun fast() {
        mCoinState = (0..1).random()
        rotateAnimFast()
        scaleFon()
        scaleCoin()
        shake()

    }

    fun slow() {
        mCoinState = (0..1).random()
        rotateAnimSlowly()
        scaleFon()
        scaleCoin()
        shake()    }

    fun normal1() {
        mCoinState = (0..1).random()
        rotateAnimNormalOne()
        scaleFon()
        scaleCoin()
        shake()    }

    fun normal2() {
        mCoinState = (0..1).random()
        rotateAnimNormalTwo()
        scaleFon()
        scaleCoin()
        shake()    }
}