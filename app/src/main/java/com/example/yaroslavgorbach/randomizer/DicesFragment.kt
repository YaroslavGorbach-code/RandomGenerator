package com.example.yaroslavgorbach.randomizer

import android.animation.*
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.example.yaroslavgorbach.randomizer.coin.FonScaleInterpolator

class DicesFragment : Fragment() {
    private lateinit var mImageView: ImageView
    private lateinit var mDeckFon: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_dices, container, false)
        mImageView = view.findViewById(R.id.dice)
        mDeckFon = view.findViewById(R.id.wood)
        return view


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mImageView.setOnClickListener {
            translater()
            rotateAnim()
            scalerFon()
            shake()
            when((1..6).random()){
                1 ->  Handler().postDelayed({mImageView.setImageResource(R.drawable.dice_1)}, 900)
                2 ->  Handler().postDelayed({mImageView.setImageResource(R.drawable.dice_2)}, 900)
                3 ->  Handler().postDelayed({mImageView.setImageResource(R.drawable.dice_3)}, 900)
                4 ->  Handler().postDelayed({mImageView.setImageResource(R.drawable.dice_4)}, 900)
                5 ->  Handler().postDelayed({mImageView.setImageResource(R.drawable.dice_5)}, 900)
                6 ->  Handler().postDelayed({mImageView.setImageResource(R.drawable.dice_6)}, 900)
            }

        }

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    private fun scalerFon() {
        val fonScaleSet = AnimatorSet()
        val scaleX = ObjectAnimator.ofFloat(mDeckFon, View.SCALE_X, 1.5f)
        scaleX.repeatCount = 1
        scaleX.repeatMode = ObjectAnimator.REVERSE

        val scaleY = ObjectAnimator.ofFloat(mDeckFon, View.SCALE_Y, 1.5f)
        scaleY.repeatCount = 1
        scaleY.repeatMode = ObjectAnimator.REVERSE

        fonScaleSet.playTogether(scaleX, scaleY)
//        fonScaleSet.interpolator = AnticipateOvershootInterpolator(0.0f, 0.0f)
        fonScaleSet.interpolator = FonScaleInterpolator()
        fonScaleSet.duration = 1200
        fonScaleSet.start()

    }



    private fun rotateAnim(){
        val setR = AnimatorSet()
        val setS = AnimatorSet()

        val rotateX = ObjectAnimator.ofFloat(mImageView, View.ROTATION, -2060f, 0f)

        val scaleX = ObjectAnimator.ofFloat(mImageView, View.SCALE_X, 1.6f)
        scaleX.repeatCount = 1
        scaleX.repeatMode = ObjectAnimator.REVERSE
        val scaleY = ObjectAnimator.ofFloat(mImageView, View.SCALE_Y, 1.6f)
        scaleY.repeatCount = 1
        scaleY.repeatMode = ObjectAnimator.REVERSE

        setS.playTogether(scaleX, scaleY)
        setS.duration = 1000
        setS.interpolator = AnticipateOvershootInterpolator(0.0f, 0.0f)
        setS.start()


        setR.play(rotateX)
        setR.duration = 2000
        setR.interpolator  = AccelerateDecelerateInterpolator()
        setR.startDelay = 200
        setR.start()

        //  animator.disableViewDuringAnimation(mImageView)

    }

    private fun translater() {
        val animator = ObjectAnimator.ofFloat(mImageView, View.TRANSLATION_Y,
            -600f)
        animator.repeatCount = 1
        animator.duration = 1000
        animator.interpolator = AnticipateOvershootInterpolator(0.0f, 0.0f)
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.disableViewDuringAnimation(mImageView)
        animator.start()
    }

    private fun shake() {
        val shakeset = AnimatorSet()
        val shakeY = ObjectAnimator.ofFloat(mImageView, View.TRANSLATION_Y,
            0f, 35f, -35f, 35f, 0f)

        val shakeX = ObjectAnimator.ofFloat(mImageView, View.TRANSLATION_X,
            0f, 5f, -5f, 5f, 0f)

        shakeset.play(shakeY).with(shakeX)

        shakeset.startDelay = 1900
        shakeset.duration = 400
        shakeset.start()
    }


    private fun scaler() {
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1.3f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.3f)
        val animator = ObjectAnimator.ofPropertyValuesHolder(
            mImageView, scaleX, scaleY)
        animator.repeatCount = 1
        animator.duration = 500
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.disableViewDuringAnimation(mImageView)
        animator.start()
    }




    private fun fader() {
        val animator = ObjectAnimator.ofFloat(mImageView, View.ALPHA, 0f)
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.disableViewDuringAnimation(mImageView)
        animator.start()
    }

    private fun colorizer() {
        var animator = ObjectAnimator.ofArgb(mImageView.parent,
            "backgroundColor", Color.WHITE, Color.RED)
        animator.setDuration(500)
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.disableViewDuringAnimation(mImageView)
        animator.start()
    }


    private fun ObjectAnimator.disableViewDuringAnimation(view: View) {
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

