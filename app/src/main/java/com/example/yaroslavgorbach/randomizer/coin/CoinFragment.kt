package com.example.yaroslavgorbach.randomizer.coin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.yaroslavgorbach.randomizer.R


class CoinFragment : Fragment() {
    private lateinit var mToolbar: Toolbar
    private lateinit var mCoinImage: ImageView
    private lateinit var mDeckFon: ImageView
    private lateinit var mCoinAnimatorAnimation: CoinAnimator


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_coin, container, false)
        mToolbar = view.findViewById(R.id.materialToolbar)
        mCoinImage = view.findViewById(R.id.coin)
        mDeckFon = view.findViewById(R.id.wood)
        mCoinAnimatorAnimation = CoinAnimator(mCoinImage, mDeckFon)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mCoinImage.setOnClickListener {
            mCoinAnimatorAnimation.animate()
        }

        mToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}