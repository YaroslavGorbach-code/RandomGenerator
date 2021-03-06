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
        when((1..5).random()){
            5-> rotateAnimSlowlyTwo()
            4-> rotateAnimNormalTwo()
            3-> rotateAnimFast()
            2-> rotateAnimNormalOne()
            1-> rotateAnimSlowlyOne()
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
                        in -1980..-1880 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        in -1880..-1690 -> {
                            setCoinSide()
                            mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        }
                        in -1690..-1530 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        in -1530..-1340 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        in -1340..-1170 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        in -1170..-270 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        in -270..-0 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                    }

                } else {
                    when ((animation.animatedValue as Float).toInt()) {
                        in -1980..-1880 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        in -1880..-1690 -> {
                            setCoinSide()
                            mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        }
                        in -1690..-1530 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        in -1530..-1340 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        in -1340..-1170 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        in -1170..-270 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        in -270..-0 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
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


    private fun rotateAnimSlowlyOne() {
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
            interpolator = InterpolatorRotateSlowlyOne()
            disableViewDuringAnimation(mCoinImage)
            startDelay = 100
            start()
        }
    }

    private fun rotateAnimSlowlyTwo() {
        ValueAnimator.ofFloat(-1100f, 0f).apply {
            addUpdateListener { animation ->
                mCoinImage.rotationX = animation.animatedValue as Float
                Log.v("coin", (animation.animatedValue as Float).toInt().toString())

                if (mCoinSide == CoinSide.FRONT) {
                    when ((animation.animatedValue as Float).toInt()) {
                        in -1100..-990 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        in -990..-810 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        in -810..-90 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        in -90..0 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                    }

                } else {
                    when ((animation.animatedValue as Float).toInt()) {
                        in -1100..-990 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        in -990..-810 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        in -810..-90 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        in -90..0 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                    }
                }
            }
            addListener(object: AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    mCoinSide = if (mCoinSide == CoinSide.FRONT) CoinSide.BACK else CoinSide.FRONT
                }
            })
            duration = 1400
            interpolator = InterpolatorRotateSlowlyTwo()
            disableViewDuringAnimation(mCoinImage)
            startDelay = 100
            start()
        }
    }

    private fun rotateAnimFast() {
        ValueAnimator.ofFloat(-2160f, 0f).apply {
            addUpdateListener { animation ->
                mCoinImage.rotationX = animation.animatedValue as Float
                Log.v("coin", (animation.animatedValue as Float).toInt().toString())

                if (mCoinSide == CoinSide.FRONT) {
                    when ((animation.animatedValue as Float).toInt()) {
                        in -2160..-2080 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        in -2080..-1900 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        in -1900..-1700 -> {
                            setCoinSide()
                            mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        }
                        in -1700..-1530 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        in -1530..-1350 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        in -1350..-800 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        in -800..-630 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        in -630..-450 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        in -450..-270 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        in -100..0 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                    }

                } else {
                    when ((animation.animatedValue as Float).toInt()) {
                        in -2160..-2080 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        in -2080..-1900 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        in -1900..-1700 -> {
                            setCoinSide()
                            mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        }
                        in -1700..-1530 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        in -1530..-1350 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        in -1350..-800 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        in -800..-630 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        in -630..-450 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        in -450..-270 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        in -100..0 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
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
}