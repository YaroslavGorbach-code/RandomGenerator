package com.example.yaroslavgorbach.randomizer

import android.animation.*
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.Fragment


class CoinFragment : Fragment() {
    private lateinit var mCoinImage: ImageView
    private lateinit var mDeckFon: ImageView
    private var coinState = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_coin, container, false)
        mCoinImage = view.findViewById(R.id.coin)
        mDeckFon = view.findViewById(R.id.wood)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mCoinImage.setOnClickListener{
            scaleFon()
            rotateAnim()
            shake()
        }
    }

    private fun scaleFon() {
        val scaleX = ObjectAnimator.ofFloat(mDeckFon, View.SCALE_X, 1.5f).apply {
            repeatCount = 1
            repeatMode = ObjectAnimator.REVERSE
        }

        val scaleY = ObjectAnimator.ofFloat(mDeckFon, View.SCALE_Y, 1.5f).apply {
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

    private fun rotateAnim(){

       // val rotateX = ObjectAnimator.ofFloat(mCoinImage, View.ROTATION_X, -1440f, 0f)
        ValueAnimator.ofFloat(-1440f, 0f).apply {
            addUpdateListener { animation ->
                mCoinImage.rotationX = animation.animatedValue as Float
                Log.v("coin", (animation.animatedValue as Float).toInt().toString())

                if (coinState == 0){
                    when((animation.animatedValue as Float).toInt()){
                        in -720..-625 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        in -625..-449 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        in -438..-270 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        in -264..-187 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        in -445..450 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        in 450..629 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                    }

                }else{
                    when((animation.animatedValue as Float).toInt()){
                        in -720..-625 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        in -625..-449 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        in -438..-270 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        in -264..-187 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                        in -445..450 -> mCoinImage.setImageResource(R.drawable.ic_coin_back)
                        in 450..629 -> mCoinImage.setImageResource(R.drawable.ic_coin_front)
                    }
                }

            }
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    coinState = if (coinState == 0) 1 else 0
                }
            })
            duration = 1400
            interpolator = RotateCoinInterpolator()
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
}