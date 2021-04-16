package com.example.yaroslavgorbach.randomizer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import com.example.yaroslavgorbach.randomizer.screen.ChangeThemeDialog
import com.example.yaroslavgorbach.randomizer.screen.Navigation
import com.example.yaroslavgorbach.randomizer.screen.MainFragment
import com.example.yaroslavgorbach.randomizer.screen.coin.CoinFragment
import com.example.yaroslavgorbach.randomizer.screen.dice.CreateDicesDialog
import com.example.yaroslavgorbach.randomizer.screen.dice.DiceFragment
import com.example.yaroslavgorbach.randomizer.screen.list.ChoseListDialog
import com.example.yaroslavgorbach.randomizer.screen.list.CreateEditListDialog
import com.example.yaroslavgorbach.randomizer.screen.list.ListFragment
import com.example.yaroslavgorbach.randomizer.screen.matches.CreateMatchesDialog
import com.example.yaroslavgorbach.randomizer.screen.matches.MatchesFragment
import com.example.yaroslavgorbach.randomizer.screen.number.CreateNumberDialog
import com.example.yaroslavgorbach.randomizer.screen.number.NumberFragment
import com.example.yaroslavgorbach.randomizer.util.ThemesUtils


class MainActivity : AppCompatActivity(), Navigation{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemesUtils.setCurrentTheme(this)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(R.id.main_container, MainFragment())
            }
        }
    }

    override fun openCoin() {
        supportFragmentManager.commit {
            replace(R.id.main_container, CoinFragment())
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            addToBackStack(null)
        }
    }

    override fun openList(title: String) {
        supportFragmentManager.commit {
            replace(R.id.main_container, ListFragment::class.java, ListFragment.argsOf(title))
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            addToBackStack(null)
        }
    }

    override fun openNumber(max: Long, min: Long, results: Long) {
        supportFragmentManager.commit {
            replace(R.id.main_container, NumberFragment::class.java, NumberFragment.argsOf(max, min, results))
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            addToBackStack(null)
        }
    }

    override fun openDice(number: Int) {
        supportFragmentManager.commit {
            replace(R.id.main_container, DiceFragment::class.java, DiceFragment.argsOf(number))
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            addToBackStack(null)
        }
    }

    override fun openMatches(number: Int, burned: Int) {
        supportFragmentManager.commit {
            replace(R.id.main_container, MatchesFragment::class.java, MatchesFragment.argsOf(number, burned))
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            addToBackStack(null)
        }
    }

    override fun showMatchesDialog() {
        CreateMatchesDialog().show(supportFragmentManager, null)
    }

    override fun showNumberDialog() {
        CreateNumberDialog().show(supportFragmentManager, null)
    }

    override fun showDicesDialog() {
        CreateDicesDialog().show(supportFragmentManager, null)
    }

    override fun showChangeThemeDialog() {
        ChangeThemeDialog().show(supportFragmentManager, null)
    }

    override fun showChoseListDialog() {
        ChoseListDialog().show(supportFragmentManager, null)
    }

    override fun showCreateEditListDialog(title: String?) {
        CreateEditListDialog().apply { arguments = CreateEditListDialog.argsOf(title) }
            .show(supportFragmentManager, null)
    }
}
