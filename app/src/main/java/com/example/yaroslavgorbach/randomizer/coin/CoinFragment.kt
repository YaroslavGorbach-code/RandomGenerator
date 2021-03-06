package com.example.yaroslavgorbach.randomizer.coin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.Fragment
import com.example.yaroslavgorbach.randomizer.R


class CoinFragment : Fragment() {
    private lateinit var mCoinImage: ImageView
    private lateinit var mDeckFon: ImageView
    private lateinit var mCoinAnimation: AnimateCoin


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_coin, container, false)
        mCoinImage = view.findViewById(R.id.coin)
        mDeckFon = view.findViewById(R.id.wood)
        mCoinAnimation = AnimateCoin(mCoinImage, mDeckFon)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mCoinImage.setOnClickListener{
        mCoinAnimation.animate()
        }

        view.findViewById<Button>(R.id.fast).setOnClickListener{
            mCoinAnimation.fast()
        }
        view.findViewById<Button>(R.id.slow).setOnClickListener{
            mCoinAnimation.slow()
        }
        view.findViewById<Button>(R.id.normal1).setOnClickListener{
            mCoinAnimation.normal1()
        }
        view.findViewById<Button>(R.id.normal2).setOnClickListener{
            mCoinAnimation.normal2()
        }
    }
}