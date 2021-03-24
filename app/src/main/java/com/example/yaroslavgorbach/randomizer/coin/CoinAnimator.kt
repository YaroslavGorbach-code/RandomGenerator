package com.example.yaroslavgorbach.randomizer.coin

import android.animation.*
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import com.example.yaroslavgorbach.randomizer.R
import com.example.yaroslavgorbach.randomizer.disableViewDuringAnimation

class CoinAnimator(coinImage: ImageView, fonImage: ImageView) {

     private enum class CoinSide {
        FRONT, BACK
    }

    private val mCoinImage: ImageView = coinImage
    private val mFonImage: ImageView = fonImage
    private var mCoinSide: CoinSide = CoinSide.FRONT
    private var mCoinState: Int = (0..1).random()

    fun animate() {
        mCoinState = (0..1).random()
        when ((4..4).random()) {
            5 -> rotateAnimSlowlyTwo()
            4 -> rotateAnimNormalTwo()
            3 -> rotateAnimFast()
            2 -> rotateAnimNormalOne()
            1 -> rotateAnimSlowlyOne()
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
        val scaleX = ObjectAnimator.ofFloat(mCoinImage, "scaleX", 1f, 1.15f, 1f, 1.15f, 1f, 1.15f, 1f)
        val scaleY = ObjectAnimator.ofFloat(mCoinImage, "scaleY", 1f, 1.15f, 1f, 1.15f, 1f, 1.15f, 1f)
        val rotation = ObjectAnimator.ofFloat(mCoinImage, "rotation",0f, 10f, -10f, 10f, -10f, 10f, -10f, 10f, -0f
        )
        val tX = ObjectAnimator.ofFloat(
            mCoinImage, View.TRANSLATION_X, 1f, -10f, -10f, 10f, 10f, -10f, -10f, 10f,
            10f, -10f, -10f, 10f, 10f, 1f
        )
        val tY = ObjectAnimator.ofFloat(mCoinImage, View.TRANSLATION_Y, 1f, -5f, -5f, 5f, 5f, 1f)

        AnimatorSet().apply {
            playTogether(tX, tY, scaleX, scaleY, rotation)
            startDelay = 1400
            duration = 500
            start()
        }
    }

    private fun rotateAnimNormalOne() {
        ValueAnimator.ofFloat(-1980f, 0f).apply {
            addUpdateListener { animation ->
                mCoinImage.rotationX = animation.animatedValue as Float

                if (mCoinSide == CoinSide.FRONT) {
                    when ((animation.animatedValue as Float).toInt()) {
                        in -1980..-1870 -> setFrontImage()
                        in -1870..-1720 -> setBackImage()
                        in -1720..-1530 -> {
                            setCoinSide()
                            setFrontImage()
                        }
                        in -1530..-1340 -> setBackImage()
                        in -1340..-1170 -> setFrontImage()
                        in -1170..-395 -> setBackImage()
                        in -395..-277 -> setFrontImage()
                        in -277..-90 -> setBackImage()
                        in -90..0 -> setFrontImage()
                    }

                } else {
                    when ((animation.animatedValue as Float).toInt()) {
                        in -1980..-1870 -> setBackImage()
                        in -1870..-1720 -> setFrontImage()
                        in -1720..-1530 -> {
                            setCoinSide()
                            setBackImage()
                        }
                        in -1530..-1340 -> setFrontImage()
                        in -1340..-1170 -> setBackImage()
                        in -1170..-395 -> setFrontImage()
                        in -395..-277 -> setBackImage()
                        in -277..-90 -> setFrontImage()
                        in -90..0 -> setBackImage()
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

                if (mCoinSide == CoinSide.FRONT) {
                    when ((animation.animatedValue as Float).toInt()) {
                        in -1980..-1880 -> setFrontImage()
                        in -1880..-1690 -> {
                            setCoinSide()
                            setBackImage()
                        }
                        in -1690..-1530 -> setFrontImage()
                        in -1530..-1340 -> setBackImage()
                        in -1340..-1170 -> setFrontImage()
                        in -1170..-270 -> setBackImage()
                        in -270..-0 -> setFrontImage()
                    }

                } else {
                    when ((animation.animatedValue as Float).toInt()) {
                        in -1980..-1880 -> setBackImage()
                        in -1880..-1690 -> {
                            setCoinSide()
                            setFrontImage()
                        }
                        in -1690..-1530 -> setBackImage()
                        in -1530..-1340 -> setFrontImage()
                        in -1340..-1170 -> setBackImage()
                        in -1170..-270 -> setFrontImage()
                        in -270..-0 -> setBackImage()
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

                if (mCoinSide == CoinSide.FRONT) {
                    when ((animation.animatedValue as Float).toInt()) {
                        in -740..-630 -> setFrontImage()
                        in -630..-88 -> setBackImage()
                        in -88..0 -> setFrontImage()
                    }

                } else {
                    when ((animation.animatedValue as Float).toInt()) {
                        in -740..-630 -> setBackImage()
                        in -630..-88 -> setFrontImage()
                        in -88..0 -> setBackImage()
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

                if (mCoinSide == CoinSide.FRONT) {
                    when ((animation.animatedValue as Float).toInt()) {
                        in -1100..-990 -> setFrontImage()
                        in -990..-810 -> setBackImage()
                        in -810..-90 -> setFrontImage()
                        in -90..0 -> setBackImage()
                    }

                } else {
                    when ((animation.animatedValue as Float).toInt()) {
                        in -1100..-990 -> setBackImage()
                        in -990..-810 -> setFrontImage()
                        in -810..-90 -> setBackImage()
                        in -90..0 -> setFrontImage()
                    }
                }
            }
            addListener(object : AnimatorListenerAdapter() {
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

                if (mCoinSide == CoinSide.FRONT) {
                    when ((animation.animatedValue as Float).toInt()) {
                        in -2160..-2080 -> setFrontImage()
                        in -2080..-1900 -> setBackImage()
                        in -1900..-1700 -> {
                            setCoinSide()
                            setFrontImage()
                        }
                        in -1700..-1530 -> setBackImage()
                        in -1530..-1350 -> setFrontImage()
                        in -1350..-800 -> setBackImage()
                        in -800..-630 -> setFrontImage()
                        in -630..-450 -> setBackImage()
                        in -450..-270 -> setFrontImage()
                        in -100..0 -> setFrontImage()
                    }

                } else {
                    when ((animation.animatedValue as Float).toInt()) {
                        in -2160..-2080 -> setBackImage()
                        in -2080..-1900 -> setFrontImage()
                        in -1900..-1700 -> {
                            setCoinSide()
                            setBackImage()
                        }
                        in -1700..-1530 -> setFrontImage()
                        in -1530..-1350 -> setBackImage()
                        in -1350..-800 -> setFrontImage()
                        in -800..-630 -> setBackImage()
                        in -630..-450 -> setFrontImage()
                        in -450..-270 -> setBackImage()
                        in -100..0 -> setBackImage()
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

    private fun setBackImage() {
        mCoinImage.setImageResource(R.drawable.ic_coin_back)
    }

    private fun setFrontImage() {
        mCoinImage.setImageResource(R.drawable.ic_coin_front)
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


}