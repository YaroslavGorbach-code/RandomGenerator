package com.example.yaroslavgorbach.randomizer.coin

import android.animation.*
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
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
        scaleFon()
        rotateAnim_1440()
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

    private fun rotateAnim_1440(){
        ValueAnimator.ofFloat(-1440f, 0f).apply {
            addUpdateListener { animation ->
                mCoinImage.rotationX = animation.animatedValue as Float
                Log.v("coin", (animation.animatedValue as Float).toInt().toString())

                if (mCoinSide == CoinSide.FRONT){
                    when((animation.animatedValue as Float).toInt()){
                        in -720..-625 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        in -625..-449 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        in -438..-270 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        in -264..-187 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        in -445..450 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        in 450..629 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        in 629..680 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        in 680..720 -> setCoinSide()
                    }

                }else{
                    when((animation.animatedValue as Float).toInt()){
                        in -720..-625 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        in -625..-449 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        in -438..-270 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        in -264..-187 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        in -445..450 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        in  450..629 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        in 629..680 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        in 680..720 -> setCoinSide()
                    }
                }
            }
            duration = 1400
            interpolator = RotateCoinInterpolator()
            disableViewDuringAnimation(mCoinImage)
            startDelay = 100
            start()
        }

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
            mCoinImage.setImageResource(R.drawable.ic_coin_back)
            CoinSide.BACK
        } else {
            mCoinImage.setImageResource(R.drawable.ic_coin_front)
            CoinSide.FRONT
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