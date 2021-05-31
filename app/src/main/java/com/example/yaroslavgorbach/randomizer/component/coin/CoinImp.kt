package com.example.yaroslavgorbach.randomizer.component.coin

import android.animation.*
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import com.example.yaroslavgorbach.randomizer.R
import com.example.yaroslavgorbach.randomizer.data.database.Repo
import com.example.yaroslavgorbach.randomizer.util.disableViewDuringAnimation
import com.example.yaroslavgorbach.randomizer.feature.SoundManager
import com.example.yaroslavgorbach.randomizer.screen.coin.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class CoinImp( private val soundManager: SoundManager, private val repo: Repo): Coin{
    private enum class CoinSide { FRONT, BACK }

    private var mCoinSide: CoinSide = CoinSide.FRONT
    private val coinSound = MutableLiveData(repo.getCoinSoundIsAllow())


    override fun getSoundIsAllow() = coinSound
    override fun disallowSound(){
        repo.setCoinSoundIsAllow(false)
        coinSound.value = false
    }
    override fun allowSound(){
        repo.setCoinSoundIsAllow(true)
        coinSound.value = true
    }

    override fun animate(coin: ImageView, fon: ImageView) {
        when ((1..4).random()) {
            1 -> rotateAnimSlowlyTwo(coin)
            2 -> rotateAnimNormalTwo(coin)
            3 -> rotateAnimSlowlyOne(coin)
            4 -> rotateAnimNormalOne(coin)
        }

        AnimatorSet().apply {
            playTogether(scaleFon(fon), scaleCoin(coin))
            GlobalScope.launch {
                delay(70)
                soundManager.flipCoinSoundPlay()
                coin.elevation = 0f
            }

            GlobalScope.launch {
                delay(1500)
                soundManager.fallCoinSoundPlay()
                coin.elevation = 15f
            }
            shake(coin).start()
            start()
        }
    }

    private fun scaleFon(fon: ImageView): AnimatorSet {
        val scaleX = ObjectAnimator.ofFloat(fon, View.SCALE_X, 1.5f).apply {
            repeatCount = 1
            repeatMode = ObjectAnimator.REVERSE
        }

        val scaleY = ObjectAnimator.ofFloat(fon, View.SCALE_Y, 1.5f).apply {
            repeatCount = 1
            repeatMode = ObjectAnimator.REVERSE
        }

       return AnimatorSet().apply {
           playTogether(scaleX, scaleY)
           interpolator = FonScaleInterpolator()
           duration = 900
        }

    }

    private fun shake(coin: ImageView):AnimatorSet {
        val scaleX = ObjectAnimator.ofFloat(coin, View.SCALE_X, 1f, 1.08f, 1f, 1.05f, 1f, 1.03f, 1f)
        val scaleY = ObjectAnimator.ofFloat(coin, View.SCALE_Y, 1f, 1.08f, 1f, 1.05f, 1f, 1.03f, 1f)

        return AnimatorSet().apply {
            if (Random.nextBoolean()){
                play(ObjectAnimator.ofFloat(coin, View.ROTATION_X,0f, -15f, 0f))
                    .with(ObjectAnimator.ofFloat(coin, View.ROTATION_Y,0f, -5f, 0f))
                    .with(ObjectAnimator.ofFloat(coin, View.ROTATION,0f, -10f, 10f, 0f))
                    .with(scaleX)
                    .with(scaleY)
            }else{
                play(ObjectAnimator.ofFloat(coin, View.ROTATION_X,0f, -5f, 0f))
                    .with(ObjectAnimator.ofFloat(coin, View.ROTATION_Y,0f, -5f, 0f))
                    .with(ObjectAnimator.ofFloat(coin, View.ROTATION,0f, 10f, -10f, 0f))
                    .with(scaleX)
                    .with(scaleY)
            }
            duration = 500
            startDelay = 1450
        }
    }

    private fun rotateAnimNormalOne(coin: ImageView) {
        setCoinSide()
        ValueAnimator.ofFloat(-1440f, 0f).apply {
            addUpdateListener { animation ->
                coin.rotationX = animation.animatedValue as Float
                if (mCoinSide == CoinSide.FRONT) {
                    when ((animation.animatedValue as Float).toInt()) {
                        in -1440..-1360 -> setFrontImage(coin)
                        in -1360..-1180 -> setBackImage(coin)
                        in -1180..-980 -> { setFrontImage(coin) }
                        in -980..-810 -> setBackImage(coin)
                        in -810..0 -> setFrontImage(coin)
                    }

                } else {
                    when ((animation.animatedValue as Float).toInt()) {
                        in -1440..-1360 -> setBackImage(coin)
                        in -1360..-1180 -> setFrontImage(coin)
                        in -1180..-980 -> { setBackImage(coin) }
                        in -980..-810 -> setFrontImage(coin)
                        in -810..0 -> setBackImage(coin)
                    }
                }
            }
            duration = 1400
            interpolator = InterpolatorRotateNormalOne()
            startDelay = 100
            disableViewDuringAnimation(coin)
            start()
        }

    }

    private fun rotateAnimNormalTwo(coin: ImageView) {
        setCoinSide()
        ValueAnimator.ofFloat(-1980f, 0f).apply {
            addUpdateListener { animation ->
                coin.rotationX = animation.animatedValue as Float

                if (mCoinSide == CoinSide.FRONT) {
                    setCoinSide()
                    when ((animation.animatedValue as Float).toInt()) {
                        in -1980..-1880 -> setFrontImage(coin)
                        in -1880..-1690 -> setBackImage(coin)
                        in -1690..-1530 -> setFrontImage(coin)
                        in -1530..-1340 -> setBackImage(coin)
                        in -1340..-1170 -> setFrontImage(coin)
                        in -1170..-270 -> setBackImage(coin)
                        in -270..-0 -> setFrontImage(coin)
                    }

                } else {
                    when ((animation.animatedValue as Float).toInt()) {
                        in -1980..-1880 -> setBackImage(coin)
                        in -1880..-1690 -> setFrontImage(coin)
                        in -1690..-1530 -> setBackImage(coin)
                        in -1530..-1340 -> setFrontImage(coin)
                        in -1340..-1170 -> setBackImage(coin)
                        in -1170..-270 -> setFrontImage(coin)
                        in -270..-0 -> setBackImage(coin)
                    }
                }
            }
            duration = 1400
            interpolator = InterpolatorRotateNormalTwo()
            disableViewDuringAnimation(coin)
            startDelay = 100
            start()
        }
    }

    private fun rotateAnimSlowlyOne(coin: ImageView) {
        ValueAnimator.ofFloat(-740f, 0f).apply {
            addUpdateListener { animation ->
                coin.rotationX = animation.animatedValue as Float

                if (mCoinSide == CoinSide.FRONT) {
                    mCoinSide = CoinSide.FRONT
                    when ((animation.animatedValue as Float).toInt()) {
                        in -740..-630 -> setFrontImage(coin)
                        in -630..-88 -> setBackImage(coin)
                        in -88..0 -> setFrontImage(coin)
                    }

                } else {
                    when ((animation.animatedValue as Float).toInt()) {
                        in -740..-630 -> setBackImage(coin)
                        in -630..-88 -> setFrontImage(coin)
                        in -88..0 -> setBackImage(coin)
                    }
                    mCoinSide = CoinSide.BACK
                }
            }
            duration = 1400
            interpolator = InterpolatorRotateSlowlyOne()
            disableViewDuringAnimation(coin)
            startDelay = 100
            start()
        }
    }

    private fun rotateAnimSlowlyTwo(coin: ImageView) {
        ValueAnimator.ofFloat(-1100f, 0f).apply {
            addUpdateListener { animation ->
                coin.rotationX = animation.animatedValue as Float

                if (mCoinSide == CoinSide.FRONT) {
                    when ((animation.animatedValue as Float).toInt()) {
                        in -1100..-990 -> setFrontImage(coin)
                        in -990..-810 -> setBackImage(coin)
                        in -810..-90 -> setFrontImage(coin)
                        in -90..0 -> setBackImage(coin)
                    }

                } else {
                    when ((animation.animatedValue as Float).toInt()) {
                        in -1100..-990 -> setBackImage(coin)
                        in -990..-810 -> setFrontImage(coin)
                        in -810..-90 -> setBackImage(coin)
                        in -90..0 -> setFrontImage(coin)
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
            disableViewDuringAnimation(coin)
            startDelay = 100
            start()
        }
    }

    private fun setBackImage(coin: ImageView) =
        coin.setImageResource(R.drawable.ic_coin_back)

    private fun setFrontImage(coin: ImageView) =
        coin.setImageResource(R.drawable.ic_coin_front)

    private fun scaleCoin(coin: ImageView):AnimatorSet {
        val scaleX = ObjectAnimator.ofFloat(coin, View.SCALE_X, 1.4f).apply {
            repeatCount = 1
            repeatMode = ObjectAnimator.REVERSE
        }

        val scaleY = ObjectAnimator.ofFloat(coin, View.SCALE_Y, 1.4f).apply {
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
        mCoinSide = if((0..1).random() == 0) CoinSide.FRONT else CoinSide.BACK
    }

}