package com.example.yaroslavgorbach.randomizer.screen.coin

import android.animation.*
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import com.example.yaroslavgorbach.randomizer.R
import com.example.yaroslavgorbach.randomizer.disableViewDuringAnimation
import com.example.yaroslavgorbach.randomizer.feature.SoundManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class CoinAnimator(coinImage: ImageView, fonImage: ImageView, soundManager: SoundManager) {
     private enum class CoinSide {
        FRONT, BACK
    }

    private val mCoinImage: ImageView = coinImage
    private val mFonImage: ImageView = fonImage
    private var mCoinSide: CoinSide = CoinSide.FRONT
    private var mCoinState: Int = (0..1).random()
    private val mSoundManager = soundManager


    fun animate() {
        mCoinState = (0..1).random()
        when ((1..4).random()) {
            1 -> rotateAnimSlowlyTwo()
            2 -> rotateAnimNormalTwo()
            3 -> rotateAnimSlowlyOne()
            4 -> rotateAnimNormalOne()
        }

        AnimatorSet().apply {
            playTogether(scaleFon(), scaleCoin())
            GlobalScope.launch {
                delay(70)
                mSoundManager.flipCoinSoundPlay()
                mCoinImage.elevation = 0f
            }

            GlobalScope.launch {
                delay(1500)
                mSoundManager.fallCoinSoundPlay()
                mCoinImage.elevation = 15f
            }
            shake().start()
            start()
        }
    }

    private fun scaleFon(): AnimatorSet {
        val scaleX = ObjectAnimator.ofFloat(mFonImage, View.SCALE_X, 1.5f).apply {
            repeatCount = 1
            repeatMode = ObjectAnimator.REVERSE
        }

        val scaleY = ObjectAnimator.ofFloat(mFonImage, View.SCALE_Y, 1.5f).apply {
            repeatCount = 1
            repeatMode = ObjectAnimator.REVERSE
        }

       return AnimatorSet().apply {
           playTogether(scaleX, scaleY)
           interpolator = FonScaleInterpolator()
           duration = 900
        }

    }

    private fun shake():AnimatorSet {
        val scaleX = ObjectAnimator.ofFloat(mCoinImage, View.SCALE_X, 1f, 1.08f, 1f, 1.05f, 1f, 1.03f, 1f)
        val scaleY = ObjectAnimator.ofFloat(mCoinImage, View.SCALE_Y, 1f, 1.08f, 1f, 1.05f, 1f, 1.03f, 1f)

        return AnimatorSet().apply {
            if (Random.nextBoolean()){
                play(ObjectAnimator.ofFloat(mCoinImage, View.ROTATION_X,0f, -15f, 0f))
                    .with(ObjectAnimator.ofFloat(mCoinImage, View.ROTATION_Y,0f, -5f, 0f))
                    .with(ObjectAnimator.ofFloat(mCoinImage, View.ROTATION,0f, -10f, 10f, 0f))
                    .with(scaleX)
                    .with(scaleY)
            }else{
                play(ObjectAnimator.ofFloat(mCoinImage, View.ROTATION_X,0f, -5f, 0f))
                    .with(ObjectAnimator.ofFloat(mCoinImage, View.ROTATION_Y,0f, -5f, 0f))
                    .with(ObjectAnimator.ofFloat(mCoinImage, View.ROTATION,0f, 10f, -10f, 0f))
                    .with(scaleX)
                    .with(scaleY)
            }
            duration = 500
            startDelay = 1450
        }
    }

    private fun rotateAnimNormalOne() {
        ValueAnimator.ofFloat(-1440f, 0f).apply {
            addUpdateListener { animation ->
                mCoinImage.rotationX = animation.animatedValue as Float

                if (mCoinSide == CoinSide.FRONT) {
                    when ((animation.animatedValue as Float).toInt()) {
                        in -1440..-1360 -> setFrontImage()
                        in -1360..-1180 -> setBackImage()
                        in -1180..-980 -> {
                            setCoinSide()
                            setFrontImage()
                        }
                        in -980..-810 -> setBackImage()
                        in -810..0 -> setFrontImage()
                    }

                } else {
                    when ((animation.animatedValue as Float).toInt()) {
                        in -1440..-1360 -> setBackImage()
                        in -1360..-1180 -> setFrontImage()
                        in -1180..-980 -> {
                            setCoinSide()
                            setBackImage()
                        }
                        in -980..-810 -> setFrontImage()
                        in -810..0 -> setBackImage()
                    }
                }
            }
            duration = 1400
            interpolator = InterpolatorRotateNormalOne()
            startDelay = 100
            disableViewDuringAnimation(mCoinImage)
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


    private fun setBackImage() {
        mCoinImage.setImageResource(R.drawable.ic_coin_back)
    }

    private fun setFrontImage() {
        mCoinImage.setImageResource(R.drawable.ic_coin_front)
    }


    private fun scaleCoin():AnimatorSet {
        val scaleX = ObjectAnimator.ofFloat(mCoinImage, View.SCALE_X, 1.4f).apply {
            repeatCount = 1
            repeatMode = ObjectAnimator.REVERSE
        }

        val scaleY = ObjectAnimator.ofFloat(mCoinImage, View.SCALE_Y, 1.4f).apply {
            repeatCount = 1
            repeatMode = ObjectAnimator.REVERSE
        }

       return AnimatorSet().apply {
            playTogether(scaleX, scaleY)
            duration = 700
            interpolator = LinearInterpolator()
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